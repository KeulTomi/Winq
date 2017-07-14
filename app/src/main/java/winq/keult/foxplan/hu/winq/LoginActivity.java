package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.LoginCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.LoginResponse;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final Context mContext = this;
    private EditText loginEmail;
    private EditText loginPassword;
    private TextView goButton;
    private TextView signUpButton;
    private ProgressBar goButtonProgress;
    private TextView aszfText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Applikáció inicializálása
        Winq.initApp(getApplicationContext());


        FirebaseMessaging.getInstance().subscribeToTopic("news");

        if (Winq.savedDataExist()) {

            HashMap<String, Object> userParams = new HashMap<>();
            userParams.put("username", Winq.getCurrentUserProfileData().getUsername());
            userParams.put("password", Winq.getCurrentUserProfileData().getPassword());
            userParams.put("apikey", getResources().getString(R.string.apikey));
            userParams.put("facebookid", Winq.getCurrentUserProfileData().getFacebookid());
            userParams.put("mobileid", Winq.mobileid);

            login(userParams);

        }

        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        loginEmail = (EditText) findViewById(R.id.email_edittext);
        loginPassword = (EditText) findViewById(R.id.password_edittext);


        signUpButton = (TextView) findViewById(R.id.sign_up_button);
        goButton = (TextView) findViewById(R.id.GO_button);

        goButtonProgress = (ProgressBar) findViewById(R.id.GO_button_progress);

        goButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

        if (getIntent().getStringExtra("freshSignUpPassword") != null) {

            loginEmail.setText(getIntent().getStringExtra("freshSignUpEmail"));
            loginPassword.setText(getIntent().getStringExtra("freshSignUpPassword"));
        }
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

                String strUserName = loginEmail.getText().toString();
                String strPassword = loginPassword.getText().toString();

                if (TextUtils.isEmpty(strUserName)) {
                    loginEmail.setError("Email field cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(strPassword)) {
                    loginPassword.setError("Password field cannot be empty");
                    return;
                }


                // Login használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                goButton.setText("");
                goButtonProgress.setVisibility(View.VISIBLE);

                Winq.mobileid = FirebaseInstanceId.getInstance().getToken();

                Map<String, Object> userParams = new HashMap<>();
                userParams.put("username", loginEmail.getText().toString());
                userParams.put("password", loginPassword.getText().toString());
                userParams.put("apikey", getResources().getString(R.string.apikey));
                userParams.put("facebookid", "no");
                userParams.put("mobileid", "no");

                login(userParams);

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
                    Winq.username = map.get("username").toString();
                    Winq.password = map.get("password").toString();

                    goButton.setText("GO");
                    goButtonProgress.setVisibility(View.INVISIBLE);

                    // Felhasználónév és jelszó mentése a beírt adatok alapján
                    loginResponse.getData().getProfileData().setUserName(map.get("username").toString());
                    loginResponse.getData().getProfileData().setPassword(map.get("password").toString());

                    // Profile adatok mentése
                    Winq.saveCurrentUserProfileData(loginResponse.getData().getProfileData());

                    // Kezdő layout indítása
                    Intent loginToMain = new Intent(mContext, MainActivity.class);
                    startActivity(loginToMain);
                } else {
                    // Válasz visszautasítva
                    Log.w("Login_Refused:",
                            "FirstErrorText= " + loginResponse.getError().get(0));
                    goButton.setText("GO");
                    goButtonProgress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("Login_Error:", networkError.getThrowable().getLocalizedMessage());

                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
                goButton.setText("GO");
                goButtonProgress.setVisibility(View.INVISIBLE);
            }
        });
    }
}
