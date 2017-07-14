package winq.keult.foxplan.hu.winq.locationshare;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import winq.keult.foxplan.hu.winq.ProfileActivity;

import static winq.keult.foxplan.hu.winq.locationshare.LocationShare.sendMyLocation;


public class LocationShareService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static boolean mIsServiceRunning = false;
    public static Handler mLooperHandler;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private LoopThread mLoopThread;

    @Override
    public void onCreate() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Pozíció megosztás aktiválva", Toast.LENGTH_LONG).show();
        Log.v("LocationShareService", "Szolgáltatás elindult, startId:" + startId + ": " + intent);

        // GoogleAPIClient létrehozása
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // LocationRequest létrehozása
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(5 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();

        mIsServiceRunning = true;

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceRunning = false;
    }

    private void checkLocationSettings(final AppCompatActivity activity) {

        // Aktuális helymeghatározás eszközállapot összevetése az mLocationRequest-ben igényelt beállításokkal
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        // CallBack beállítása ha sikerült lekérdezni az eszközállapotot
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                //final LocationSettingsStates miez = locationSettingsResult.getLocationSettingsStates();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // Saját pozíció lekérdezhető

                        // Időzítő hurkok threadjének indításaa
                        mLoopThread = new LoopThread();
                        mLoopThread.start();

                        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        if (location != null)
                            mLastLocation = location;

                        // Pozíció lekérdezése
                        if (ActivityCompat.checkSelfPermission(
                                LocationShareService.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            // Nincs engedély, engedélyt kell kérni a felhasználótól
                            // TODO: 2016.12.13. Ki kell találni, hogy mit csináljunk
                            Log.e("LocationShareService:", "Nincs helyszolgáltatáshoz engedély, probléma...");
                            return;
                        }

                        Log.v("LocationShareService:", "Hely szolgáltatás ellenőrzése sikeres, pozíció");
                        LocationServices.FusedLocationApi.requestLocationUpdates(
                                mGoogleApiClient,
                                mLocationRequest,
                                LocationShareService.this);

                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Saját pozícióhoz felhasználói beavatkozás szükséges
                        Log.v("checkLocationSettings:", "Pozíció nem érhető el, Felhasználói beavatkozás szükséges");


                        try {
                            // Dialógusablak megjelenítése, eredmény a ProfileActivity onActivityResult()-ből kérdezhető le
                            if (activity != null) {
                                status.startResolutionForResult(activity, ProfileActivity.CHECK_LOCATION_SETTINGS);
                            }

                        } catch (IntentSender.SendIntentException e) {
                            // Nem érdekel a hiba
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        Log.v("checkLocationSettings:", "Pozíció nem érhető el felhasználói beavatkozással sem. :(");
                        break;
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v("LocationShareService", "GoogleAPI csatlakozva");
        checkLocationSettings(null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("LocationShareService:", "GoogleAPI hiba");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("LocationShareService:", "GoogleAPI felfüggesztve");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("LocationShareService:", "Pozíció frissítés érkezett");
        mLastLocation = location;
    }


    private class LoopThread extends Thread {
        private static final String INNER_TAG = "LoopThread";
        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mLastLocation != null) {
                    sendMyLocation(getBaseContext(), mLastLocation);
                }

                mLooperHandler.postDelayed(runnable, 10 * 1000); // Ismétlés másodpercekben
            }
        };

        public void run() {
            this.setName(INNER_TAG);
            Looper.prepare();
            mLooperHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {

                    Log.i("LoopThread", "handleMessage(Message msg)");

                    switch (msg.what) {
                        case 0:
                            // MainThread-ből érkező üzenet a GPS állapotának lekérdezéséhez
                            checkLocationSettings((AppCompatActivity) msg.obj);
                            /*if (msg.obj.equals("??")) {
                                getLooper().quit();
                                if (Winq.mUiHandler != null) {
                                    Message msgToProfileFragment = new Message();
                                    msgToProfileFragment.what = 0;
                                    msgToProfileFragment.obj = "LooperStopped";
                                    Winq.mUiHandler.sendMessage(msgToProfileFragment);
                                }
                            }*/
                            break;
                        default:
                            break;
                    }
                }
            };
            Log.i("LocationShareService", "Első üzenetküldés");
            mLooperHandler.post(runnable);
            Looper.loop();
        }
    }
}

