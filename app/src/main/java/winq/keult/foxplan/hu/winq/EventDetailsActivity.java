package winq.keult.foxplan.hu.winq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.EventJoinCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.EventData;
import com.example.keult.networking.model.EventJoinResponse;

import java.util.HashMap;
import java.util.Map;

public class EventDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static TextView headerDateMonthAndDay;
    private static TextView headerDateYear;
    private static ImageView eventImage;
    private static TextView eventTitle;
    private static TextView eventPlace;
    private static TextView eventDate;
    private static TextView eventDescription;
    private static TextView eventBuyButton;
    private static TextView eventJoinButton;
    private static LinearLayout eventShareOpinion;
    private static LinearLayout backToMainMenu;

    private static int eventNumber;
    private static HashMap<String, EventData> eventData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_event_details);

        //Innicializálás
        eventImage = (ImageView) findViewById(R.id.details_event_image);
        eventTitle = (TextView) findViewById(R.id.details_event_name);
        eventPlace = (TextView) findViewById(R.id.details_events_place);
        eventDate = (TextView) findViewById(R.id.details_events_date);
        eventDescription = (TextView) findViewById(R.id.details_event_description);
        eventBuyButton = (TextView) findViewById(R.id.details_event_buy_ticket);
        eventJoinButton = (TextView) findViewById(R.id.details_event_join);
        eventShareOpinion = (LinearLayout) findViewById(R.id.details_event_opinion);
        backToMainMenu = (LinearLayout) findViewById(R.id.details_back_points);
        headerDateYear = (TextView) findViewById(R.id.details_headertime_year);
        headerDateMonthAndDay = (TextView) findViewById(R.id.details_headertime_month_day);

        //Setterek
        backToMainMenu.setOnClickListener(this);
        eventJoinButton.setOnClickListener(this);
        eventShareOpinion.setOnClickListener(this);
        eventDescription.setMovementMethod(new ScrollingMovementMethod());

        //A headerre kiírjuk a valós dátumot
        Winq.setTheRealTime(headerDateYear, headerDateMonthAndDay);

        eventNumber = getIntent().getIntExtra("eventNum", 2);

        switch (eventNumber) {

            case 0:
                setHomepageInfos(0);
                break;
            case 1:
                setHomepageInfos(1);
                break;

            case 2:
                setEventInfos();
        }

    }

    private void setEventInfos() {

        eventData = (HashMap<String, EventData>) Winq.eventsEventData;

        if (eventData.get("eventData").getImage() != "") {
            //Ha van képe az eventnek akkor betöltjük
            Glide.with(this)
                    .load(eventData.get("eventData").getImage())
                    .asBitmap()
                    .into((ImageView) findViewById(R.id.details_event_image));
        }

        //Kiíratjuk az adatokat
        eventTitle.setText(eventData.get("eventData").getTitle());

        //Ha hosszabb a location mint 12 betű akkor utána már csak ...-ot irunk
        if (eventData.get("eventData").getLocation().length() > 19) {
            String cuttedText = eventData.get("eventData").getLocation().substring(0, 19);
            eventPlace.setText(cuttedText + "...");
        } else {
            eventPlace.setText(eventData.get("eventData").getLocation());
        }

        eventDate.setText(eventData.get("eventData").getDate());
        eventDescription.setText(eventData.get("eventData").getText());
    }

    private void setHomepageInfos(int eventNum) {

        if (Winq.homepageEventDatas.get(eventNum).getImage() != "") {
            //Ha van képe az eventnek akkor betöltjük
            Glide.with(this)
                    .load(Winq.homepageEventDatas.get(eventNum).getImage())
                    .asBitmap()
                    .into((ImageView) findViewById(R.id.details_event_image));
        }


        eventTitle.setText(Winq.homepageEventDatas.get(eventNum).getTitle());

        //Ha hosszabb a location mint 12 betű akkor utána már csak ...-ot irunk
        if (Winq.homepageEventDatas.get(eventNum).getLocation().length() > 19) {
            String cuttedText = Winq.homepageEventDatas.get(eventNum).getLocation().substring(0, 19);
            eventPlace.setText(cuttedText + "...");
        }
        else {
            eventPlace.setText(Winq.homepageEventDatas.get(eventNum).getLocation());
        }

        eventDate.setText(Winq.homepageEventDatas.get(eventNum).getDate());
        eventDescription.setText(Winq.homepageEventDatas.get(eventNum).getText());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.details_back_points:
                finish();
                break;

            case R.id.details_event_join:
                EventDetailsActivity activity = new EventDetailsActivity();
                activity.joinToEvent();
                break;

            case R.id.details_event_opinion:
                ShareOpinionDialog cdd = new ShareOpinionDialog(this);
                cdd.show();
        }
    }

    public void joinToEvent() {

//        final Context context = getApplicationContext();

        Map<String, Object> map = new HashMap<>();

        if (eventNumber == 2) {


            map.put("apikey", "a");
            map.put("username", Winq.username);
            map.put("password", Winq.password);
            map.put("facebookid", "no");
            map.put("eventid", eventData.get("eventData").getId());
            // Csak egyszer lehet egy event-hez csatlakozni, a számot változtatni kell
        } else {
            map.put("apikey", "a");
            map.put("username", Winq.username);
            map.put("password", Winq.password);
            map.put("facebookid", "no");
            map.put("eventid", Winq.homepageEventDatas.get(eventNumber).getId());
            // Csak egyszer lehet egy event-hez csatlakozni, a számot változtatni kell
        }


        NetworkManager.getInstance().joinToEvent(map, new EventJoinCallback() {
            @Override
            public void forwardResponse(EventJoinResponse eventJoinResponse) {

                if (eventJoinResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("joinToEvent", "siker");
                    //toastMaker("You joined succesfuly to this Event");
                } else {
                    // Válasz visszautasítva
                    Log.w("joinToEvent_Refused:",
                            "FirstErrorText= " + eventJoinResponse.getError().get(0));

                    //toastMaker(eventJoinResponse.getError().get(0));
                }

            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("exploreUsers_Error:", networkError.getThrowable().getLocalizedMessage());
                //toastMaker("No internet connection");
            }
        });
    }

    public void toastMaker(String toastText) {
        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();
    }
}
