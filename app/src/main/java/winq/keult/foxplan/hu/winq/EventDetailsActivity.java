package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.example.keult.networking.callback.EventRateCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.EventData;
import com.example.keult.networking.model.EventJoinResponse;
import com.example.keult.networking.model.EventRateResponse;
import com.example.keult.networking.model.EventsJoinedData;

import java.util.HashMap;
import java.util.Map;

public class EventDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static int rate = 0;
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
    private static HashMap<String, EventsJoinedData> connectEventData;
    public Context mActivityContext;

        /*public EventDetailsActivity(Context ctx) {
            super();
            // TODO Auto-generated constructor stub
            mActivityContext = ctx;
        }*/

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

        //Feltöltjük a lehetséges hashmapeket
        eventData = (HashMap<String, EventData>) Winq.eventsEventData;
        connectEventData = Winq.connectEventData;


        //A headerre kiírjuk a valós dátumot
        Winq.setTheRealTime(headerDateYear, headerDateMonthAndDay);

        eventNumber = getIntent().getIntExtra("eventNum", 3);

        switch (eventNumber) {

            case 0:
                setHomepageInfos(0);
                break;
            case 1:
                setHomepageInfos(1);
                break;

            case 2:
                setConnectEventInfos();
                break;

            case 3:
                setEventInfos();
        }

    }

    private void setEventInfos() {

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

    private void setConnectEventInfos() {

        if (connectEventData.get("eventData").getImage() != "") {
            //Ha van képe az eventnek akkor betöltjük
            Glide.with(this)
                    .load(connectEventData.get("eventData").getImage())
                    .asBitmap()
                    .into((ImageView) findViewById(R.id.details_event_image));
        }

        //Kiíratjuk az adatokat
        eventTitle.setText(connectEventData.get("eventData").getTitle());

        //Ha hosszabb a location mint 12 betű akkor utána már csak ...-ot irunk
        if (connectEventData.get("eventData").getLocation().length() > 19) {
            String cuttedText = connectEventData.get("eventData").getLocation().substring(0, 19);
            eventPlace.setText(cuttedText + "...");
        } else {
            eventPlace.setText(connectEventData.get("eventData").getLocation());
        }

        eventDate.setText(connectEventData.get("eventData").getDate());
        eventDescription.setText(connectEventData.get("eventData").getText());
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
                joinToEvent();
                break;

            case R.id.details_event_opinion:
                ShareOpinionDialog cdd = new ShareOpinionDialog(this);
                cdd.show();
                cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        switch (rate) {

                            case 1:
                                rateEvent();
                                break;

                            case 2:
                                rateEvent();
                                break;

                            case 3:
                                rateEvent();
                                break;
                        }
                    }
                });
        }
    }

    public void joinToEvent() {

//        final Context context = getApplicationContext();

        Map<String, Object> map = new HashMap<>();

        switch (eventNumber) {

            case 0:
                map.put("apikey", "a");
                map.put("username", Winq.username);
                map.put("password", Winq.password);
                map.put("facebookid", "no");
                map.put("eventid", Winq.homepageEventDatas.get(eventNumber).getId());
                // Csak egyszer lehet egy event-hez csatlakozni, a számot változtatni kell
                break;

            case 1:
                map.put("apikey", "a");
                map.put("username", Winq.username);
                map.put("password", Winq.password);
                map.put("facebookid", "no");
                map.put("eventid", Winq.homepageEventDatas.get(eventNumber).getId());
                // Csak egyszer lehet egy event-hez csatlakozni, a számot változtatni kell
                break;

            case 2:
                map.put("apikey", "a");
                map.put("username", Winq.username);
                map.put("password", Winq.password);
                map.put("facebookid", "no");
                map.put("eventid", connectEventData.get("eventData").getId());
                // Csak egyszer lehet egy event-hez csatlakozni, a számot változtatni kell
                break;


            case 3:
                map.put("apikey", "a");
                map.put("username", Winq.username);
                map.put("password", Winq.password);
                map.put("facebookid", "no");
                map.put("eventid", eventData.get("eventData").getId());
                // Csak egyszer lehet egy event-hez csatlakozni, a számot változtatni kell
                break;

        }


        NetworkManager.getInstance().joinToEvent(map, new EventJoinCallback() {
            @Override
            public void forwardResponse(EventJoinResponse eventJoinResponse) {

                if (eventJoinResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("joinToEvent", "siker");
                    //dialogMaker("Succesful join");
                    Toast.makeText(getApplicationContext(), "Succesful join", Toast.LENGTH_LONG).show();
                } else {
                    // Válasz visszautasítva
                    Log.w("joinToEvent_Refused:",
                            "FirstErrorText= " + eventJoinResponse.getError().get(0));
                    Toast.makeText(getApplicationContext(), eventJoinResponse.getError().get(0), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("exploreUsers_Error:", networkError.getThrowable().getLocalizedMessage());
                Toast.makeText(getApplicationContext(), networkError.getThrowable().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void rateEvent() {
        Map<String, Object> map = new HashMap<>();

        switch (eventNumber) {
            case 0:
                map.put("apikey", "a");
                map.put("username", Winq.username);
                map.put("password", Winq.password);
                map.put("facebookid", "no");
                map.put("eventid", Winq.homepageEventDatas.get(eventNumber).getId());
                map.put("rate", String.valueOf(rate));
                // Csak egyszer lehet egy event-hez csatlakozni, a számot változtatni kell
                break;

            case 1:
                map.put("apikey", "a");
                map.put("username", Winq.username);
                map.put("password", Winq.password);
                map.put("facebookid", "no");
                map.put("eventid", Winq.homepageEventDatas.get(eventNumber).getId());
                map.put("rate", String.valueOf(rate));
                // Csak egyszer lehet egy event-hez csatlakozni, a számot változtatni kell
                break;

            case 2:
                map.put("apikey", "a");
                map.put("username", Winq.username);
                map.put("password", Winq.password);
                map.put("facebookid", "no");
                map.put("eventid", connectEventData.get("eventData").getId());
                map.put("rate", String.valueOf(rate));
                // Csak egyszer lehet egy event-hez csatlakozni, a számot változtatni kell
                break;

            case 3:
                map.put("apikey", "a");
                map.put("username", Winq.username);
                map.put("password", Winq.password);
                map.put("facebookid", "no");
                map.put("eventid", eventData.get("eventData").getId());
                map.put("rate", String.valueOf(rate));
                // Csak egyszer lehet egy event-hez csatlakozni, a számot változtatni kell
                break;

        }

        NetworkManager.getInstance().rateEvent(map, new EventRateCallback() {
            @Override
            public void forwardResponse(EventRateResponse eventRateResponse) {

                if (eventRateResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("joinToEvent", "siker");
                    //dialogMaker("Thank you:)");
                    Toast.makeText(getApplicationContext(), "Thank you", Toast.LENGTH_LONG).show();
                } else {
                    // Válasz visszautasítva
                    Log.w("joinToEvent_Refused:",
                            "FirstErrorText= " + eventRateResponse.getError().get(0));

                    Toast.makeText(getApplicationContext(), eventRateResponse.getError().get(0), Toast.LENGTH_LONG).show();
                    //dialogMaker(eventRateResponse.getError().get(0));
                }

            }

            @Override
            public void forwardError(NetworkError networkError) {

                //dialogMaker(networkError.getThrowable().getMessage());
                Toast.makeText(getApplicationContext(), "Thank you", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void dialogMaker(String dialogText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(dialogText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
