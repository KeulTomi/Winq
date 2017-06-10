package winq.keult.foxplan.hu.winq;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.DateDoNotLikeCallback;
import com.example.keult.networking.callback.ExploreCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.DateDoNotLikeResponse;
import com.example.keult.networking.model.ExploreResponse;
import com.example.keult.networking.model.ProfileData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExploreActivity extends AppCompatActivity implements View.OnClickListener {

    private static TextView currentUserAge;
    private static TextView currentUserCountry;
    private static TextView currentUserFullname;
    private static TextView currentUserIntrest;
    private static TextView currentUserDescription;
    private static TextView headerDateYear;
    private static TextView headerDateMonthAndDay;
    private static ImageView currentUserImage;
    private static String currentUserID;
    private static List<ProfileData> currentUserProfile;
    private static int numberOfUser = 0;
    private static ImageView dontLike;
    private static ImageView like;
    Handler mUiHandler = new Handler();

    static void exploreUsers() {

        Map<String, Object> map;


        map = new HashMap<>();
        map.put("apikey", "a");
        map.put("username", Winq.username);
        map.put("password", Winq.password);
        map.put("facebookid", "no");

        NetworkManager.getInstance().exploreUsers(map, new ExploreCallback() {
            @Override
            public void forwardResponse(ExploreResponse exploreResponse) {

                if (exploreResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("exploreUsers_OK:",
                            "FullName(first_result)= "
                                    + exploreResponse.getData().getUsersList().get(0).getFullName());

                    currentUserProfile = exploreResponse.getData().getUsersList();

                    setTheCurrentInfos(currentUserProfile, numberOfUser);
                } else {
                    // Válasz visszautasítva
                    Log.w("exploreUsers_Refused:",
                            "FirstErrorText= " + exploreResponse.getError().get(0));
                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("exploreUsers_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void dontLikeDate() {

        if (numberOfUser == 5) {
            numberOfUser = 0;
            exploreUsers();
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("apikey", "a");
        map.put("username", Winq.username);
        map.put("password", Winq.password);
        map.put("facebookid", "no");
        map.put("to_user", currentUserProfile.get(numberOfUser).getId());

        NetworkManager.getInstance().dontLikeDate(map, new DateDoNotLikeCallback() {
            @Override
            public void forwardResponse(DateDoNotLikeResponse dateDoNotLikeResponse) {

                if (dateDoNotLikeResponse.getSuccess() == 1) {
                    // Válasz rendben
                    setTheCurrentInfos(currentUserProfile, numberOfUser);
                } else {
                    // Válasz visszautasítva
                    Log.w("dontLikeDate_Refused:",
                            "FirstErrorText= " + dateDoNotLikeResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("dontLikeDate_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    public static void setTheCurrentInfos(List<ProfileData> profileData, int listNumber) {
//        // Kép aszinkron betöltése
//        ImageLoader imageLoader = new ImageLoader(getActivity());
//        imageLoader.DisplayImage(profileData.get(listNumber).getImage(), currentUserImage, mUiHandler);

        //currentUserAge.setText(profileData.get(listNumber));
        currentUserCountry.setText(profileData.get(listNumber).getUserCountryShort());
        currentUserFullname.setText(profileData.get(listNumber).getFullName());
        currentUserIntrest.setText(profileData.get(listNumber).getUserInterestText());
        currentUserDescription.setText(profileData.get(listNumber).getUserDescription());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_explore);

        headerDateYear = (TextView) findViewById(R.id.explore_headertime_year);
        headerDateMonthAndDay = (TextView) findViewById(R.id.explore_headertime_month_day);

        //A headerre kiírjuk a valós dátumot
        Winq.setTheRealTime(headerDateYear, headerDateMonthAndDay);


        numberOfUser = 0;
        //lekerdezzük a userListát és betesssz
        exploreUsers();

        LinearLayout backToMainMenu = (LinearLayout) findViewById(R.id.explore_back_points);
        currentUserAge = (TextView) findViewById(R.id.explore_age_of_current_user);
        currentUserFullname = (TextView) findViewById(R.id.explore_fullname_of_current_user);
        currentUserCountry = (TextView) findViewById(R.id.explore_country_of_current_user);
        currentUserDescription = (TextView) findViewById(R.id.explore_current_user_description);
        currentUserIntrest = (TextView) findViewById(R.id.explore_current_user_intrest);

        dontLike = (ImageView) findViewById(R.id.explore_dont_like);
        like = (ImageView) findViewById(R.id.explore_like);

        dontLike.setOnClickListener(this);
        like.setOnClickListener(this);
        backToMainMenu.setOnClickListener(this);

//        // Üzeneteket kezelő Handler
//        mUiHandler = new Handler() {
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//
//                    case ImageLoader.UPDATE_IMAGE_MSG_CODE:
//                        currentUserImage.setImageBitmap((Bitmap) msg.obj);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.explore_back_points:
                finish();
                overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_down);
                break;

            case R.id.explore_dont_like:
                dontLikeDate();
                numberOfUser++;
                break;

            case R.id.explore_like:
                //setTheCurrentInfos(currentUserProfile, numberOfUser);
                //numberOfUser++;
                break;
        }
    }

}