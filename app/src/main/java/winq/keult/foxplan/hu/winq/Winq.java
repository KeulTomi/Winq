package winq.keult.foxplan.hu.winq;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.keult.networking.model.DateData;
import com.example.keult.networking.model.EventData;
import com.example.keult.networking.model.EventsJoinedData;
import com.example.keult.networking.model.ProfileData;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 *  Mielőtt bármi elindulna, ez a class példányosítódik,
 *  itt vannak tárolva a glogális változók
 */

public class Winq extends Application {

    public static String username;
    public static String password;
    public static String facebookid;
    public static List<EventData> homepageEventDatas;
    public static HashMap<String, EventData> eventsEventData = new HashMap<>();
    public static HashMap<String, DateData> connectData = new HashMap<>();
    public static HashMap<String, EventsJoinedData> connectEventData = new HashMap<>();
    private static ProfileData mCurrentUserProfileData;
    private static Context mContext;

    public static void initApp(Context context) {
        mContext = context;

        if (savedDataExist()) {

            // Van mentett felhasználói adat, el kell tárolni
            String usr = getAppPref(R.string.user_prefs_key).getString(getStringResource(R.string.saved_username_key), null);
            String pass = getAppPref(R.string.user_prefs_key).getString(getStringResource(R.string.saved_password_key), null);
            String fbid = getAppPref(R.string.user_prefs_key).getString(getStringResource(R.string.saved_facebookid_key), null);

            mCurrentUserProfileData = new ProfileData();

            mCurrentUserProfileData.setUserName(usr);
            mCurrentUserProfileData.setPassword(pass);
            mCurrentUserProfileData.setFacebookId(fbid);
        }
    }

    public static void setTheRealTime (TextView yearText, TextView montAndDayText){
        //A headerre kiírjuk a valós dátumot
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        yearText.setText(String.valueOf(year));

        if (month + 1 <= 10) {
        montAndDayText.setText(String.valueOf("0" + (month+1) + "." + day));
        if (day <= 10) {
            montAndDayText.setText(String.valueOf("0" + (month + 1) + "." + "0" + day));
        }
        } else {
            montAndDayText.setText(String.valueOf((month + 1) + "." + day));
            if (day <= 10) {
                montAndDayText.setText(String.valueOf((month + 1) + "." + "0" + day));
            }
        }
    }

    public static ProfileData getCurrentUserProfileData() {
        return mCurrentUserProfileData;
    }

    public static void saveCurrentUserProfileData(ProfileData currentUserProfileData) {

        SharedPreferences.Editor editor = getAppPref(R.string.user_prefs_key).edit();
        editor.putString(getStringResource(R.string.saved_username_key), currentUserProfileData.getUsername());
        editor.putString(getStringResource(R.string.saved_password_key), currentUserProfileData.getPassword());
        editor.putString(getStringResource(R.string.saved_facebookid_key), currentUserProfileData.getFacebookid());
        editor.putString(getStringResource(R.string.saved_userid_key), currentUserProfileData.getId());
        editor.apply();
        mCurrentUserProfileData = currentUserProfileData;
    }

    private static String getStringResource(int resource) {
        return mContext.getResources().getString(resource);
    }

    public static void clearCurrentUserProfileData(ProfileData currentUserProfileData) {

        SharedPreferences.Editor editor = getAppPref(R.string.user_prefs_key).edit();
        editor.clear();
        editor.apply();
        mCurrentUserProfileData = null;
    }


    private static SharedPreferences getAppPref(int resourceId) {

        String key = mContext.getResources().getString(resourceId);
        return mContext.getSharedPreferences(key, Context.MODE_PRIVATE);
    }

    public static boolean savedDataExist() {

        String savedUser = getAppPref(R.string.user_prefs_key).getString(getStringResource(R.string.saved_username_key), null);

        if (savedUser == null)
            return false;
        else
            return true;

    }
}
