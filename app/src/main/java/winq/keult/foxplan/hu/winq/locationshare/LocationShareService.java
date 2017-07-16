package winq.keult.foxplan.hu.winq.locationshare;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static winq.keult.foxplan.hu.winq.locationshare.LocationShare.sendMyLocation;


public class LocationShareService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static boolean isServiceRunning = false;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;

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

        // CSatlakozás GoogleAPI-hoz
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();

        // LocationRequest létrehozása
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(30 * 1000)        // Igényelt frissítési ciklus
                .setFastestInterval(1 * 1000); // Leggyorsabb ciklus, amit az app képes kezelni

        isServiceRunning = true;

        return START_STICKY;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v("LocationShareService", "GoogleAPI csatlakozva");
        startLocationUpdates();
    }

    private void startLocationUpdates() {

        // Location service user engedély ellenőrzése
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            Log.e("LocationShareService:", "User nem adott még engedélyt Location Service-hez, service inaktív...");
            return;
        }

        // Engedély megvan, utolsó ismert helyzet lekérdezése
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            Log.v("LocationShareService", "Utolsó ismert helyzet: " + location.getLatitude() + "," + location.getLongitude());
            mLastLocation = location;
        }

        // Location update indítása
        Log.v("LocationShareService", "Location update request indítva");
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("LocationShareService:", "Pozíció frissítés érkezett");

        if (mLastLocation != null) {

            if (mLastLocation.distanceTo(location) >= 50.0) {
                // Ha 50 méternél többet mozdult el akkor érdemes frissíteni a pozíciót
                mLastLocation = location;
                sendMyLocation(getBaseContext(), mLastLocation);
            }
        } else {
            sendMyLocation(getBaseContext(), location);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceRunning = false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("LocationShareService:", "GoogleAPI hiba");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("LocationShareService:", "GoogleAPI felfüggesztve");
    }
}

