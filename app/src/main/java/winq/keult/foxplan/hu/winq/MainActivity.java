package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.EventListCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.EventData;
import com.example.keult.networking.model.EventListResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static ImageView mainUpcEventFirstImg;
    private static ImageView mainUpcEventSecondImg;
    private static TextView mainUpcEventFirstName;
    private static TextView mainUpcEventSecondName;
    private static TextView mainUpcEventFirstPlace;
    private static TextView mainUpcEventSecondPlace;
    private static Handler mUiHandler = new Handler();

    public void listEvents() {

        Map<String, Object> map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", Winq.username);
            map.put("password", Winq.password);
            map.put("facebookid", "no");
            map.put("page", "0");
            map.put("homepage", "1");

        NetworkManager.getInstance().listEvents(map, new EventListCallback() {
            @Override
            public void forwardResponse(EventListResponse eventListResponse) {

                if (eventListResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("listEvents_OK:",
                            "events_all= " + Integer.toString(eventListResponse.getData().getEventsCount()));
                    Winq.homepageEventDatas = eventListResponse.getData().getEventList();
                    setTheEventInfos(Winq.homepageEventDatas, 0);
                    setTheEventInfos(Winq.homepageEventDatas, 1);
                } else {
                    // Válasz visszautasítva
                    Log.w("listEvents_Refused:",
                            "FirstErrorText= " + eventListResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("listEvents_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    public void setTheEventInfos(List<EventData> eventData, int listNumber) {

        switch (listNumber){
            case 0:

                //Ha hosszabb a title mint 12 betű akkor utána már csak ...-ot irunk
                if (eventData.get(0).getTitle().length() > 15){
                    String cuttedText = eventData.get(0).getTitle().substring(0, 15);
                    mainUpcEventFirstName.setText(cuttedText + "...");
                }
                else {
                    mainUpcEventFirstName.setText(eventData.get(listNumber).getTitle());
                }

                //Ha hosszabb a location mint 12 betű akkor utána már csak ...-ot irunk
                if (eventData.get(0).getLocation().length() > 12){
                    String cuttedText = eventData.get(0).getLocation().substring(0, 12);
                    mainUpcEventFirstPlace.setText(cuttedText + "...");
                }
                else {
                    mainUpcEventFirstPlace.setText(eventData.get(listNumber).getLocation());
                }

                if (eventData.get(listNumber).getImage() != "") {
                    //Ha van képe a felhasználónak akkor betöltjük
                    Glide.with(this)
                            .load(eventData.get(listNumber).getImage())
                            .asBitmap()
                            .into((ImageView) findViewById(R.id.main_first_upcoming_event));
                }

                break;

            case 1:
                //Ha hosszabb a title mint 12 betű akkor utána már csak ...-ot irunk
                if (eventData.get(0).getTitle().length() > 15){
                    String cuttedText = eventData.get(1).getTitle().substring(0, 15);
                    mainUpcEventSecondName.setText(cuttedText + "...");
                }
                else {
                    mainUpcEventSecondName.setText(eventData.get(listNumber).getTitle());
                }

                //Ha hosszabb a location mint 12 betű akkor utána már csak ...-ot irunk
                if (eventData.get(0).getTitle().length() > 12){
                    String cuttedText = eventData.get(1).getLocation().substring(0, 12);
                    mainUpcEventSecondPlace.setText(cuttedText + "...");
                }
                else {
                    mainUpcEventSecondPlace.setText(eventData.get(listNumber).getTitle());
                }

                if (eventData.get(listNumber).getImage() != "") {
                    //Ha van képe a felhasználónak akkor betöltjük
                    Glide.with(this)
                            .load(eventData.get(listNumber).getImage())
                            .asBitmap()
                            .into((ImageView) findViewById(R.id.main_first_upcoming_event));
                }

                break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Betöltjük a homePage-en lévő eventeket
        listEvents();

        //Innicializálás
        RelativeLayout mainExploreButton = (RelativeLayout) findViewById(R.id.main_button_explore);
        RelativeLayout mainEventsButton = (RelativeLayout) findViewById(R.id.main_button_events);
        mainUpcEventFirstImg = (ImageView) findViewById(R.id.main_first_upcoming_event);
        mainUpcEventSecondImg = (ImageView) findViewById(R.id.main_second_upcoming_event);
        mainUpcEventFirstName = (TextView) findViewById(R.id.main_first_upc_event_name);
        mainUpcEventSecondName = (TextView) findViewById(R.id.main_second_upc_event_name);
        mainUpcEventFirstPlace = (TextView) findViewById(R.id.main_first_upc_event_city);
        mainUpcEventSecondPlace = (TextView) findViewById(R.id.main_second_upc_event_city);


        //OnCickListenerek
        mainExploreButton.setOnClickListener(this);
        mainEventsButton.setOnClickListener(this);
        mainUpcEventFirstImg.setOnClickListener(this);
        mainUpcEventSecondImg.setOnClickListener(this);
        findViewById(R.id.main_button_profile).setOnClickListener(this);
        findViewById(R.id.main_button_connect).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.main_button_explore:
                if (checkOutTheInternetConnection()) {
                    Intent openExplore = new Intent(this, ExploreActivity.class);
                    startActivity(openExplore);
                    overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_stay);
                } else return;
                break;

            case R.id.main_button_events:
                if (checkOutTheInternetConnection()) {
                    Intent openEvents = new Intent(this, EventsActivity.class);
                    startActivity(openEvents);
                    overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_stay);
                } else return;
                break;

            case R.id.main_button_connect:
                if (checkOutTheInternetConnection()) {
                    Intent openConnect = new Intent(this, ConnectActivity.class);
                    startActivity(openConnect);
                    overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_stay);
                } else return;
                break;

            case R.id.main_first_upcoming_event:
                if (checkOutTheInternetConnection()) {
                    Intent openDetails1 = new Intent(this, EventDetailsActivity.class);
                    openDetails1.putExtra("eventNum", 0);
                    startActivity(openDetails1);
                } else return;
                break;

            case R.id.main_second_upcoming_event:
                if (checkOutTheInternetConnection()) {
                    if (Winq.homepageEventDatas.size() > 1) {
                        Intent openDetails2 = new Intent(this, EventDetailsActivity.class);
                        openDetails2.putExtra("eventNum", 1);
                        startActivity(openDetails2);
                    }
                } else return;
                break;

            case R.id.main_button_profile:
                if (checkOutTheInternetConnection()) {
                    Intent intentProfile = new Intent(this, ProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(getString(R.string.intent_key_profile_data), Winq.getCurrentUserProfileData());
                    intentProfile.putExtras(bundle);
                    startActivity(intentProfile);
                    overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_stay);
                } else return;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        return;
    }

    private boolean checkOutTheInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        boolean hasWIFI = true;

        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi

            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile network
            }
        } else {
            // not connected to the internet
            hasWIFI = false;
            InternetProblemDialog dialog = new InternetProblemDialog(this);
            dialog.show();
        }

        return hasWIFI;
    }

}
