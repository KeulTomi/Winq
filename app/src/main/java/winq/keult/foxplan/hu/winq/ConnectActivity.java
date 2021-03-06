package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.DateListCallback;
import com.example.keult.networking.callback.FriendsListCallback;
import com.example.keult.networking.callback.UserSearchCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.DateData;
import com.example.keult.networking.model.DateListResponse;
import com.example.keult.networking.model.FriendsData;
import com.example.keult.networking.model.FriendsListResponse;
import com.example.keult.networking.model.ProfileData;
import com.example.keult.networking.model.UserSearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnKeyListener {

    static TextView myFriendsTab;
    static TextView winqsTab;
    static TextView searchTab;
    static GridView friendsList;
    private static ArrayList<FriendsData> currentFriendList = new ArrayList<>();
    private static ArrayList<ProfileData> currentSearchResultList = new ArrayList<>();
    private static ArrayList<DateData> dateList = new ArrayList<>();
    private static ConnectMyFriendsAdapter myFriendsAdapter;

    //TODO Nincs még a myFriend-re és a Search-re api hívás, ezért az adapter classok is teéljesen üresek
    private static ConnectWinqsAdapter winqsAdapter;
    private static ConnectSearchAdapter searchAdapter;

    private static String currentTabName = "joined";

    private static RelativeLayout connectHeader;
    private static LinearLayout backPoints;
    private static LinearLayout searchBar;
    private static EditText searchEditText;
    private TextView headerDateYear;
    private TextView headerDateMonthAndDay;
    private ProgressBar connectListProgress;
    private ProgressBar searchListProgress;

    public void eventsTabBar() {

        // Check if no view has focus:
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        switch (currentTabName) {

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
        myFriendsList();

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
        searchListProgress = (ProgressBar) findViewById(R.id.connect_list_progress);

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

        ScaleHelper.scaleViewAndChildren(findViewById(R.id.connect_root), Winq.getScaleX(), Winq.getScaleY());
    }

    private void winqsList() {
        Map<String, Object> map = new HashMap<>();
        map.put("apikey", "a");
        map.put("username", Winq.username);
        map.put("password", Winq.password);
        map.put("facebookid", "no");

        NetworkManager.getInstance().listDates(map, new DateListCallback() {
            @Override
            public void forwardResponse(DateListResponse dateListResponse) {

                if (dateListResponse.getSuccess() == 1) {
                    // Válasz rendben
                    dateList = (ArrayList<DateData>) dateListResponse.getData().getDateList();
                    setAdapters("upcoming");

                } else {
                    // Válasz visszautasítva
                    Log.w("listEvents_Refused:",
                            "FirstErrorText= " + dateListResponse.getError().get(0));

                }

            }

            @Override
            public void forwardError(NetworkError networkError) {

            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (checkOutTheInternetConnection()) {
            Bundle bundleDetails = new Bundle();

            //Az Item adataiból ProfileData készítése, amit egy Bundle-ben kell átadni a ProfileActivity-nek
            ProfileData profileData = (ProfileData) parent.getItemAtPosition(position);

            Intent intentProfile = new Intent(this, ProfileActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.intent_key_profile_data), profileData);
            intentProfile.putExtras(bundle);

            switch (currentTabName) {

                case "joined":
                    FriendsData friendsData = (FriendsData) parent.getItemAtPosition(position);
                    intentProfile.putExtra("message", friendsData.getMessages());
                    break;

                case "upcoming":
                    DateData dateData = (DateData) parent.getItemAtPosition(position);
                    intentProfile.putExtra("message", dateData.getMessages());
                    break;

                case "search":
                    break;
            }

            startActivity(intentProfile);
        } else return;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.connect_joined:
                //A tabbaron színváltás történik
                currentTabName = "joined";
                eventsTabBar();

                if (checkOutTheInternetConnection()) {
                    //Adatlekérdezés
                    currentFriendList.clear();
                    dateList.clear();
                    friendsList.setAdapter(null);
                    connectListProgress.setVisibility(View.VISIBLE);
                    myFriendsList();
                } else return;
                break;

            case R.id.connect_upcoming:
                //A tabbaron színváltás történik
                currentTabName = "upcoming";
                eventsTabBar();

                if (checkOutTheInternetConnection()) {
                    //Adatlekérdezés
                    currentFriendList.clear();
                    dateList.clear();
                    friendsList.setAdapter(null);
                    connectListProgress.setVisibility(View.VISIBLE);
                    winqsList();
                } else return;

                break;

            case R.id.connect_search:
                //A tabbaron színváltás történik
                currentTabName = "search";
                eventsTabBar();

                if (checkOutTheInternetConnection()) {
                    //Adatlekérdezés
                    currentFriendList.clear();
                    dateList.clear();
                    friendsList.setAdapter(null);
                } else return;

                break;

            case R.id.connect_back_points:
                finish();
                overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_down);
                break;
        }

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

    private void myFriendsList() {
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
                    setAdapters("joined");

                } else {
                    // Válasz visszautasítva
                    Log.w("listEvents_Refused:",
                            "FirstErrorText= " + friendsListResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                connectListProgress.setVisibility(View.GONE);
            }
        });
    }

    public void setAdapters(String adapter) {

        TextView noResultText = (TextView) findViewById(R.id.connect_no_result_text);
        ImageView noResultPicture = (ImageView) findViewById(R.id.connect_no_result_sad_picture);

        switch (adapter) {

            case "joined":
                if (currentFriendList.size() == 0) {
                    noResultPicture.setVisibility(View.VISIBLE);
                    noResultText.setVisibility(View.VISIBLE);
                    connectListProgress.setVisibility(View.GONE);
                } else {
                    noResultPicture.setVisibility(View.GONE);
                    noResultText.setVisibility(View.GONE);
                    myFriendsAdapter = new ConnectMyFriendsAdapter(this, currentFriendList);
                    friendsList.setAdapter(myFriendsAdapter);
                    connectListProgress.setVisibility(View.GONE);
                }
                break;

            case "upcoming":
                if (dateList.size() == 0) {
                    noResultPicture.setVisibility(View.VISIBLE);
                    noResultText.setVisibility(View.VISIBLE);
                    connectListProgress.setVisibility(View.GONE);
                } else {
                    noResultPicture.setVisibility(View.GONE);
                    noResultText.setVisibility(View.GONE);
                    winqsAdapter = new ConnectWinqsAdapter(this, dateList);
                    friendsList.setAdapter(winqsAdapter);
                    connectListProgress.setVisibility(View.GONE);
                }
                break;

            case "search":
                if (currentSearchResultList.size() == 0) {
                    noResultPicture.setVisibility(View.VISIBLE);
                    noResultText.setVisibility(View.VISIBLE);
                    connectListProgress.setVisibility(View.GONE);
                    friendsList.setAdapter(null);
                } else {
                    noResultPicture.setVisibility(View.GONE);
                    noResultText.setVisibility(View.GONE);
                    searchAdapter = new ConnectSearchAdapter(this, currentSearchResultList);
                    friendsList.setAdapter(searchAdapter);
                    connectListProgress.setVisibility(View.GONE);
                }
                break;
        }

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {


        if (event.getAction() != event.ACTION_DOWN) {

            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //Akkor indítjuk a keresést amikot lenyomta az Entert
                searchListProgress.setVisibility(View.VISIBLE);

                // Check if no view has focus:
                View view = this.getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                searchUsers();
            }
        }



        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return false;
    }

    private void searchUsers() {

        Map<String, Object> map = new HashMap<>();
        map.put("apikey", "a");
        map.put("username", Winq.username);
        map.put("password", Winq.password);
        map.put("facebookid", "no");
        map.put("searchinput", searchEditText.getText().toString());
        map.put("page", "0");


        NetworkManager.getInstance().searchUsers(map, new UserSearchCallback() {
            @Override
            public void forwardResponse(UserSearchResponse userSearchResponse) {
                if (userSearchResponse.getSuccess() == 1) {
                    // Válasz rendben
                    currentSearchResultList = (ArrayList<ProfileData>) userSearchResponse.getData().getUsers();
                    setAdapters("search");
                } else {
                    // Válasz visszautasítva
                    Log.w("search_Refused:",
                            "FirstErrorText= " + userSearchResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("search_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }
}
