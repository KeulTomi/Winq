package winq.keult.foxplan.hu.winq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.EventListCallback;
import com.example.keult.networking.callback.EventsJoinedCallback;
import com.example.keult.networking.callback.EventsSearchCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.EventData;
import com.example.keult.networking.model.EventListResponse;
import com.example.keult.networking.model.EventsJoinedResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    static TextView joinedTab;
    static TextView upcomingTab;
    static TextView searchTab;
    static ListView eventsList;
    private static ArrayList<EventData> currentEventList = new ArrayList<>();
    private static EventsJoinedAdapter joinedAdapter;
    private static EventsUpcomingAdapter upcomingAdapter;
    private static EventsSearchAdapter searchAdapter;
    private static RelativeLayout eventsHeader;
    private static LinearLayout backPoints;
    private static LinearLayout searchBar;
    private static EditText searchEditText;
    private static EventsActivity activity = new EventsActivity();

    public static void eventsTabBar(String tabName) {

        switch (tabName) {

            case "joined":

                //Átszínezzük a kiválasztott joined tabot szürkére a többit fehérre
                joinedTab.setBackgroundColor(Color.parseColor("#777777"));
                upcomingTab.setBackgroundResource(R.drawable.tab_bar_border_big);
                searchTab.setBackgroundResource(R.drawable.tab_bar_border_big);

                joinedTab.setTextColor(Color.WHITE);
                upcomingTab.setTextColor(Color.parseColor("#777777"));
                searchTab.setTextColor(Color.parseColor("#777777"));

                //Berakjuk a Headert a searchBar helyett
                eventsHeader.setVisibility(View.VISIBLE);
                backPoints.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.INVISIBLE);

                break;

            case "upcoming":

                //Átszínezzük a kiválasztott upcoming tabot szürkére a többit fehérre
                joinedTab.setBackgroundResource(R.drawable.tab_bar_border_big);
                upcomingTab.setBackgroundColor(Color.parseColor("#777777"));
                searchTab.setBackgroundResource(R.drawable.tab_bar_border_big);

                joinedTab.setTextColor(Color.parseColor("#777777"));
                upcomingTab.setTextColor(Color.WHITE);
                searchTab.setTextColor(Color.parseColor("#777777"));

                //Berakjuk a Headert a searchBar helyett
                eventsHeader.setVisibility(View.VISIBLE);
                backPoints.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.INVISIBLE);

                break;

            case "search":

                //Átszínezzük a kiválasztott search tabot szürkére a többit fehérre
                joinedTab.setBackgroundResource(R.drawable.tab_bar_border_big);
                upcomingTab.setBackgroundResource(R.drawable.tab_bar_border_big);
                searchTab.setBackgroundColor(Color.parseColor("#777777"));

                joinedTab.setTextColor(Color.parseColor("#777777"));
                upcomingTab.setTextColor(Color.parseColor("#777777"));
                searchTab.setTextColor(Color.WHITE);

                //Berakjuk a searchBart a Header helyett
                eventsHeader.setVisibility(View.INVISIBLE);
                backPoints.setVisibility(View.GONE);
                searchBar.setVisibility(View.VISIBLE);

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
        setContentView(R.layout.activity_events);

        //A kezdő tab a joined ezért betöltjük azt
        //TODO teszt céljából előszőr az upcomingot töltjük be
        eventsList = (ListView) findViewById(R.id.events_list);
        upcomingEvents();

        joinedTab = (TextView) findViewById(R.id.evetns_joined);
        upcomingTab = (TextView) findViewById(R.id.events_upcoming);
        searchTab = (TextView) findViewById(R.id.events_search);
        eventsHeader = (RelativeLayout) findViewById(R.id.events_header);
        backPoints = (LinearLayout) findViewById(R.id.events_back_points);
        searchBar = (LinearLayout) findViewById(R.id.events_search_bar);
        searchEditText = (EditText) findViewById(R.id.events_search_edittext);

        joinedTab.setOnClickListener(this);
        upcomingTab.setOnClickListener(this);
        searchTab.setOnClickListener(this);
        eventsList.setOnItemClickListener(this);
        backPoints.setOnClickListener(this);

        //Feltöltjük az első adatokkal
        upcomingAdapter = new EventsUpcomingAdapter(this, currentEventList);
        eventsList.setAdapter(joinedAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Bundle bundleDetails = new Bundle();
        HashMap<String, EventData> eventData = new HashMap<>();

        //Telerakjuk az adott Event adataival egy Bundle-t
        //eventData.put("eventData", (EventData) parent.getItemAtPosition(position));
        //bundleDetails.putSerializable("messageBody", eventData);

        Winq.eventDatas = (ArrayList<EventData>) parent.getItemAtPosition(position);

        //Elküldjük az EventDetailsActivity-nek
        Intent openEventDatails = new Intent(this, EventDetailsActivity.class);
        //openEventDatails.putExtra("detailsBundle", bundleDetails);

        startActivity(openEventDatails);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.evetns_joined:
                //A tabbaron színváltás történik
                eventsTabBar("joined");
                //Adatlekérdezés
                currentEventList.clear();
                joinedEvents();

                break;

            case R.id.events_upcoming:
                //A tabbaron színváltás történik
                eventsTabBar("upcoming");
                //Adatlekérdezés
                currentEventList.clear();
                upcomingEvents();

                break;

            case R.id.events_search:
                //A tabbaron színváltás történik
                eventsTabBar("search");
                //Adatlekérdezés
                currentEventList.clear();
                searchEvents();

                break;
        }

    }

    public void joinedEvents() {

        Map<String, Object> map = new HashMap<>();
        map.put("apikey", "a");
        map.put("username", Winq.username);
        map.put("password", Winq.password);
        map.put("facebookid", "no");

        NetworkManager.getInstance().listJoinedEvents(map, new EventsJoinedCallback() {
            @Override
            public void forwardResponse(EventsJoinedResponse eventsJoinedResponse) {

                if (eventsJoinedResponse.getSuccess() == 1) {
                    // Válasz rendben
                    //currentEventList = (ArrayList<EventData>) eventsJoinedResponse.getData();

                } else {
                    // Válasz visszautasítva
                    Log.w("listEvents_Refused:",
                            "FirstErrorText= " + eventsJoinedResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("listEvents_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }


    public void upcomingEvents() {

        Map<String, Object> map = new HashMap<>();
        map.put("apikey", "a");
        map.put("username", Winq.username);
        map.put("password", Winq.password);
        map.put("facebookid", "no");
        map.put("page", "0");
        map.put("homepage", "0");

        NetworkManager.getInstance().listEvents(map, new EventListCallback() {
            @Override
            public void forwardResponse(EventListResponse eventListResponse) {

                if (eventListResponse.getSuccess() == 1) {
                    // Válasz rendben
                    currentEventList = (ArrayList<EventData>) eventListResponse.getData().getEventList();
                    setAdapters("upcoming");

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

    public void searchEvents() {

        Map<String, Object> map = new HashMap<>();
        map.put("apikey", "a");
        map.put("username", Winq.username);
        map.put("password", Winq.password);
        map.put("facebookid", "no");
        map.put("searchinput", searchEditText.getText().toString());
        map.put("page", "0");
        map.put("homepage", "0");


        NetworkManager.getInstance().searchEvents(map, new EventsSearchCallback() {
            @Override
            public void forwardResponse(EventListResponse eventListResponse) {

                if (eventListResponse.getSuccess() == 1) {
                    // Válasz rendben
                    currentEventList = (ArrayList<EventData>) eventListResponse.getData().getEventList();
                    searchAdapter.notifyDataSetChanged();
                    setAdapters("search");
                } else {
                    // Válasz visszautasítva
                    Log.w("searchEvents_Refused:",
                            "FirstErrorText= " + eventListResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("searchEvents_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    public void setAdapters(String adapter) {

        switch (adapter) {

            case "joined":
                joinedAdapter = new EventsJoinedAdapter(this, currentEventList);
                eventsList.setAdapter(joinedAdapter);
                break;

            case "upcoming":
                upcomingAdapter = new EventsUpcomingAdapter(this, currentEventList);
                eventsList.setAdapter(upcomingAdapter);
                break;

            case "search":
                searchAdapter = new EventsSearchAdapter(this, currentEventList);
                eventsList.setAdapter(searchAdapter);
                break;
        }

    }
}
