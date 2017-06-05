package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.LoginCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.LoginResponse;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final Context mContext = this;
    private EditText loginEmail;
    private EditText loginPassword;
    private TextView goButton;
    private TextView signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Applikáció inicializálása
        Winq.initApp(getApplicationContext());

        if (Winq.savedDataExist()) {

            HashMap<String, Object> userParams = new HashMap<>();
            userParams.put("username", Winq.getCurrentUserProfileData().getUsername());
            userParams.put("password", Winq.getCurrentUserProfileData().getPassword());
            userParams.put("apikey", getResources().getString(R.string.apikey));
            userParams.put("facebookid", Winq.getCurrentUserProfileData().getFacebookid());

            login(userParams);

        }


        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        signUpButton = (TextView) findViewById(R.id.sign_up_button);
        goButton = (TextView) findViewById(R.id.GO_button);

        loginEmail = (EditText) findViewById(R.id.email_edittext);
        loginPassword = (EditText) findViewById(R.id.password_edittext);

        goButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_up_button:

                Intent startSignUp = new Intent(this, SignUpActivity.class);
                startActivity(startSignUp);
                overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_stay);
                break;
            case R.id.GO_button:

                // Login használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                HashMap<String, Object> userParams = new HashMap<>();
                userParams.put("username", loginEmail.getText().toString());
                userParams.put("password", loginPassword.getText().toString());
                userParams.put("apikey", "a");
                userParams.put("facebookid", "no");
                login(userParams);

                // SignUp használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.signup(null);

                // getASZF használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.getASZF(null);

                // requestForDate használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.requestForDate(null);

                // dontLikeDate használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.dontLikeDate(null);

                // listDates használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.listDates(null);

                // listEvents használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.listEvents(null);

                // listEventsById használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.listEventsById(null);

                // searchEvents használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.searchEvents(null);

                // searchEvents használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.searchGeneral(null);

                // searchEvents használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.exploreUsers(null);

                // joinToEvent használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.joinToEvent(null);

                // getProfileImages használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.getProfileImages(null);

                break;
        }
    }

    public void login(final Map<String, Object> map) {

        NetworkManager.getInstance().login(map, new LoginCallback() {
            @Override
            public void forwardResponse(LoginResponse loginResponse) {

                if (loginResponse.getSuccess() == 1) {

                    // Válasz rendben
                    Log.v("Login_OK:",
                            "FullName= " + loginResponse.getData().getProfileData().getFullName());

                    // Felhasználónév és jelszó mentése a beírt adatok alapján
                    loginResponse.getData().getProfileData().setUserName(map.get("username").toString());
                    loginResponse.getData().getProfileData().setPassword(map.get("password").toString());

                    // Profile adatok mentése
                    Winq.saveCurrentUserProfileData(loginResponse.getData().getProfileData());

                    // Kezdő layout indítása
                    Intent loginToMain = new Intent(mContext, ProfileActivity.class);
                    startActivity(loginToMain);
                } else {
                    // Válasz visszautasítva
                    Log.w("Login_Refused:",
                            "FirstErrorText= " + loginResponse.getError().get(0));


                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("Login_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }
}
