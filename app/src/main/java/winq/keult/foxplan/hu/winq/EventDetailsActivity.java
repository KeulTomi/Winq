package winq.keult.foxplan.hu.winq;

import android.content.Context;
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

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.EventJoinCallback;
import com.example.keult.networking.error.NetworkError;
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
        eventDescription.setMovementMethod(new ScrollingMovementMethod());

        //A headerre kiírjuk a valós dátumot
        Winq.setTheRealTime(headerDateYear, headerDateMonthAndDay);

        int eventNumber = getIntent().getIntExtra("eventNum", 0);

        switch (eventNumber) {

            case 0:
                setTheDatailsInfos(0);
                break;
            case 1:
                setTheDatailsInfos(1);
                break;
        }

    }

    private void setTheDatailsInfos(int eventNum) {

        eventTitle.setText(Winq.eventDatas.get(eventNum).getTitle());

        //Ha hosszabb a location mint 12 betű akkor utána már csak ...-ot irunk
        if (Winq.eventDatas.get(0).getLocation().length() > 19){
            String cuttedText = Winq.eventDatas.get(0).getLocation().substring(0, 19);
            eventPlace.setText(cuttedText + "...");
        }
        else {
            eventPlace.setText(Winq.eventDatas.get(eventNum).getLocation());
        }

        eventDate.setText(Winq.eventDatas.get(eventNum).getDate());
        eventDescription.setText(Winq.eventDatas.get(eventNum).getText());

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
        }
    }

    public void joinToEvent() {

        final Context context = this;


        Map<String, Object> map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", Winq.username);
            map.put("password", Winq.password);
            map.put("facebookid", "no");
            map.put("eventid", "1"); // Csak egyszer lehet egy event-hez csatlakozni, a számot változtatni kell


        NetworkManager.getInstance().joinToEvent(map, new EventJoinCallback() {
            @Override
            public void forwardResponse(EventJoinResponse eventJoinResponse) {

                if (eventJoinResponse.getSuccess() == 1) {
                    // Válasz rendben

                    //Toast.makeText(context, "Sikeresen csatlakoztál az eseményhez", Toast.LENGTH_LONG).show();
                } else {
                    // Válasz visszautasítva
                    Log.w("joinToEvent_Refused:",
                            "FirstErrorText= " + eventJoinResponse.getError().get(0));

                    //Toast.makeText(context, eventJoinResponse.getError().get(0), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("exploreUsers_Error:", networkError.getThrowable().getLocalizedMessage());
                //Toast.makeText(context, "Nincs internethozzáférés", Toast.LENGTH_LONG).show();
            }
        });
    }
}
