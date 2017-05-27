package winq.keult.foxplan.hu.winq;

import android.util.Log;

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.ConditionsCallback;
import com.example.keult.networking.callback.DateAddCallback;
import com.example.keult.networking.callback.LoginCallback;
import com.example.keult.networking.callback.SignUpCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.ConditionsResponse;
import com.example.keult.networking.model.DateAddResponse;
import com.example.keult.networking.model.LoginResponse;
import com.example.keult.networking.model.SignUpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 *  Az API teszteléséhez kívülről egyszerűen hívható metódusok
 *  TODO: A végleges verzióban ez nem kell, csak a minták követésére használatos
 */

class ApiTester {

    static void login(Map<String, Object> map) {

        if ( map == null ) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "ios@test.com");
            map.put("password", "test");
            map.put("facebookid", "no");
        }

        NetworkManager.getInstance().login(map, new LoginCallback() {
            @Override
            public void forwardResponse(LoginResponse loginResponse) {
                Log.v("Login:", loginResponse.getData().getProfileData().getFullname());
            }

            @Override
            public void forwardError(NetworkError networkError) {

            }
        });
    }

    static void signup(Map<String, Object> map ) {

        if ( map == null ) {
            map = new HashMap<>();

            map.put("apikey", "a");
            map.put("username", "keul-4@keul.com");
            map.put("password", "asd123");
            map.put("facebookid", "no");
            map.put("email", "keul-4@keul.com");
            map.put("fullname", "Keul Tamás-3");
            map.put("fiulany", "0");
            map.put("user_born", "1998.04.24");
            map.put("user_country_short", "GER");
            map.put("user_interest", "1");
            map.put("user_looking", "Egyfejű lányt");
            map.put("user_behavior", "Minden nap piálok");
            map.put("user_activity", "#vadfiú #alkohol #agresszió");
            map.put("user_type", "{\"123\",\"532\"}");
            map.put("user_description", "Egy fejű fiú");
            map.put("mobileid", "no");
        }

        NetworkManager.getInstance().signup(map, new SignUpCallback() {
            @Override
            public void forwardResponse(SignUpResponse signUpResponse) {
                if ( signUpResponse.getSuccess() == 1 )
                    Log.v("SignUp_OK:", "User successfully signed up");
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("SignUp_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void getASZF(Map<String, Object> map) {

        if ( map == null ) {
            map = new HashMap<>();
            map.put("apikey", "a");
        }

        NetworkManager.getInstance().getASZF(map, new ConditionsCallback() {
            @Override
            public void forwardResponse(ConditionsResponse conditionsResponse) {
                Log.v("getASZF_OK:", conditionsResponse.getData().getPages());
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.v("getASZF_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void addDate(Map<String, Object> map) {

        if ( map == null ) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "keul-4@keul.com");
            map.put("password", "asd123");
            map.put("facebookid", "no");
            map.put("to_user", "1");
        }

        NetworkManager.getInstance().addDate(map, new DateAddCallback() {
            @Override
            public void forwardResponse(DateAddResponse dateAddResponse) {
                Log.v("addDate_OK:", dateAddResponse.getData().toString());
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("addDate_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }
}
