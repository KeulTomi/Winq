package winq.keult.foxplan.hu.winq;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.FriendsListCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.FriendsData;
import com.example.keult.networking.model.FriendsListResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnKeyListener {

    static TextView myFriendsTab;
    static TextView winqsTab;
    static TextView searchTab;
    static GridView friendsList;
    private static ArrayList<FriendsData> currentFriendList = new ArrayList<>();
    private static ConnectWinqsAdapter winqsAdapter;

    //TODO Nincs még a myFriend-re és a Search-re api hívás, ezért az adapter classok is teéljesen üresek
    private static ConnectMyFriendsAdapter myFriendsAdapter;
    private static ConnectSearchAdapter searchAdapter;

    private static RelativeLayout connectHeader;
    private static LinearLayout backPoints;
    private static LinearLayout searchBar;
    private static EditText searchEditText;
    private TextView headerDateYear;
    private TextView headerDateMonthAndDay;
    private ProgressBar connectListProgress;

    public static void eventsTabBar(String tabName) {

        switch (tabName) {

            case "joined":

                //Átszínezzük a kiválasztott joined tabot szürkére a többit fehérre
                myFriendsTab.setBackgroundColor(Color.parseColor("#777777"));
                winqsTab.setBackgroundResource(R.drawable.tab_bar_border_big);
                searchTab.setBackgroundResource(R.drawable.tab_bar_border_big);

                myFriendsTab.setTextColor(Color.WHITE);
                winqsTab.setTextColor(Color.parseColor("#777777"));
                searchTab.setTextColor(Color.parseColor("#777777"));

                //Berakjuk a Headert a searchBar helyett
                connectHeader.setVisibility(View.VISIBLE);
                backPoints.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.INVISIBLE);

                break;

            case "upcoming":

                //Átszínezzük a kiválasztott upcoming tabot szürkére a többit fehérre
                myFriendsTab.setBackgroundResource(R.drawable.tab_bar_border_big);
                winqsTab.setBackgroundColor(Color.parseColor("#777777"));
                searchTab.setBackgroundResource(R.drawable.tab_bar_border_big);

                myFriendsTab.setTextColor(Color.parseColor("#777777"));
                winqsTab.setTextColor(Color.WHITE);
                searchTab.setTextColor(Color.parseColor("#777777"));

                //Berakjuk a Headert a searchBar helyett
                connectHeader.setVisibility(View.VISIBLE);
                backPoints.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.INVISIBLE);

                break;

            case "search":

                //Átszínezzük a kiválasztott search tabot szürkére a többit fehérre
                myFriendsTab.setBackgroundResource(R.drawable.tab_bar_border_big);
                winqsTab.setBackgroundResource(R.drawable.tab_bar_border_big);
                searchTab.setBackgroundColor(Color.parseColor("#777777"));

                myFriendsTab.setTextColor(Color.parseColor("#777777"));
                winqsTab.setTextColor(Color.parseColor("#777777"));
                searchTab.setTextColor(Color.WHITE);

                //Berakjuk a searchBart a Header helyett
                connectHeader.setVisibility(View.INVISIBLE);
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
        setContentView(R.layout.activity_connect);

        //A kezdő tab a joined ezért betöltjük azt (és a szükséges innitek)
        friendsList = (GridView) findViewById(R.id.connect_list);
        connectListProgress = (ProgressBar) findViewById(R.id.connect_list_progress);

        connectListProgress.setVisibility(View.VISIBLE);
        myFriendsEvents();

        //Inicializálások
        myFriendsTab = (TextView) findViewById(R.id.connect_joined);
        winqsTab = (TextView) findViewById(R.id.connect_upcoming);
        searchTab = (TextView) findViewById(R.id.connect_search);
        connectHeader = (RelativeLayout) findViewById(R.id.connect_header);
        backPoints = (LinearLayout) findViewById(R.id.connect_back_points);
        searchBar = (LinearLayout) findViewById(R.id.connect_search_bar);
        searchEditText = (EditText) findViewById(R.id.connect_search_edittext);
        headerDateYear = (TextView) findViewById(R.id.connect_headertime_year);
        headerDateMonthAndDay = (TextView) findViewById(R.id.connect_headertime_month_day);

        //Listenerek
        myFriendsTab.setOnClickListener(this);
        winqsTab.setOnClickListener(this);
        searchTab.setOnClickListener(this);
        friendsList.setOnItemClickListener(this);
        backPoints.setOnClickListener(this);
        searchEditText.setOnKeyListener(this);

        //Feltöltjük az első adatokkal
        myFriendsAdapter = new ConnectMyFriendsAdapter(this, currentFriendList);
        friendsList.setAdapter(myFriendsAdapter);

        //A headerre kiírjuk a valós dátumot
        Winq.setTheRealTime(headerDateYear, headerDateMonthAndDay);
    }

    private void myFriendsEvents() {

        //Nem csinálunk semmit amíg nem lesz kész az api hívás
        connectListProgress.setVisibility(View.GONE);
        return;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Bundle bundleDetails = new Bundle();

        //TODO: Ha készen áll a ProfilActivity akkor itt kell átadni neki az adatokat

        //Telerakjuk az adott Event adataival egy Bundle-t
        Winq.connectData.put("friendData", (FriendsData) parent.getItemAtPosition(position));

        //Elküldjük az EventDetailsActivity-nek
        ////Intent openEventDatails = new Intent(this, EventDetailsActivity.class);


        ////startActivity(openEventDatails);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.connect_joined:
                //A tabbaron színváltás történik
                eventsTabBar("joined");

                //Adatlekérdezés
                currentFriendList.clear();
                connectListProgress.setVisibility(View.VISIBLE);
                myFriendsEvents();

                break;

            case R.id.connect_upcoming:
                //A tabbaron színváltás történik
                eventsTabBar("upcoming");

                //Adatlekérdezés
                currentFriendList.clear();
                connectListProgress.setVisibility(View.VISIBLE);
                winqsEvents();

                break;

            case R.id.connect_search:
                //A tabbaron színváltás történik
                eventsTabBar("search");
                //Adatok törlése és várakozás az Enter gombra, hogy kereshessen
                currentFriendList.clear();

                break;

            case R.id.connect_back_points:
                finish();
                overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_down);
                break;
        }

    }

    private void winqsEvents() {
        Map<String, Object> map = new HashMap<>();
        map.put("apikey", "a");
        map.put("username", Winq.username);
        map.put("password", Winq.password);
        map.put("facebookid", "no");

        NetworkManager.getInstance().listFriends(map, new FriendsListCallback() {

            @Override
            public void forwardResponse(FriendsListResponse friendsListResponse) {
                if (friendsListResponse.getSuccess() == 1) {
                    // Válasz rendben
                    currentFriendList = (ArrayList<FriendsData>) friendsListResponse.getData().getFriendsList();
                    setAdapters("upcoming");

                } else {
                    // Válasz visszautasítva
                    Log.w("listEvents_Refused:",
                            "FirstErrorText= " + friendsListResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                connectListProgress.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setAdapters(String adapter) {

        switch (adapter) {

            case "joined":
                myFriendsAdapter = new ConnectMyFriendsAdapter(this, currentFriendList);
                friendsList.setAdapter(myFriendsAdapter);
                connectListProgress.setVisibility(View.GONE);
                break;

            case "upcoming":
                winqsAdapter = new ConnectWinqsAdapter(this, currentFriendList);
                friendsList.setAdapter(winqsAdapter);
                connectListProgress.setVisibility(View.GONE);
                break;

            case "search":
                searchAdapter = new ConnectSearchAdapter(this, currentFriendList);
                friendsList.setAdapter(searchAdapter);
                connectListProgress.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }
}