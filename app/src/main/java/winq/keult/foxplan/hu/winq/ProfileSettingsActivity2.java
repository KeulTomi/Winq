package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.LoginCallback;
import com.example.keult.networking.callback.ProfileModifyCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.LoginResponse;
import com.example.keult.networking.model.ProfileModifyResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileSettingsActivity2 extends AppCompatActivity implements View.OnClickListener {

    private EditText settingsDescription;
    private EditText settingsActivities;
    private TextView settingsAllActivities;
    private ImageView backToSecondPartBtn;
    private TextView finishSignUp;
    private TextView addButton;
    private TextView undoButton;
    private ArrayList hashtagList;
    private Bundle bundleThirdPart;
    private HashMap<String, Object> userParams;
    private TextView aszfCheckBox;
    private ProgressBar signupProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2);

        backToSecondPartBtn = (ImageView) findViewById(R.id.back_to_signup_second_part);
        finishSignUp = (TextView) findViewById(R.id.finish_sign_up);
        settingsDescription = (EditText) findViewById(R.id.sign_up_description);
        settingsActivities = (EditText) findViewById(R.id.sign_up_activities);
        settingsAllActivities = (TextView) findViewById(R.id.sign_up_all_activities);
        addButton = (TextView) findViewById(R.id.sign_up_add);
        undoButton = (TextView) findViewById(R.id.sign_up_activities_undo);
        signupProgress = (ProgressBar) findViewById(R.id.signup_progress);

        hashtagList = new ArrayList();
        bundleThirdPart = getIntent().getBundleExtra("signUpBundle");
        userParams = (HashMap<String, Object>) bundleThirdPart.get("messageBody");

        aszfCheckBox = (CheckBox) findViewById(R.id.aszf_check_box);
        aszfCheckBox.setVisibility(View.GONE);

        finishSignUp.setText("FINISH");

        //Aláhúzás az ászf text alá


        undoButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        backToSecondPartBtn.setOnClickListener(this);
        finishSignUp.setOnClickListener(this);
        findViewById(R.id.main_layout).setOnClickListener(this);

        ScaleHelper.scaleViewAndChildren(findViewById(R.id.main_layout), Winq.getScaleX(), Winq.getScaleY());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.main_layout:
                // Check if no view has focus:
                View v = this.getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                break;

            case R.id.back_to_signup_second_part:
                finish();
                overridePendingTransition(R.anim.activity_slide_left2, R.anim.activity_slide_right2);
                break;
            case R.id.finish_sign_up:

                userParams.put("description", settingsDescription.getText().toString());
                userParams.put("activities", settingsAllActivities.getText().toString());
                userParams.put("mobileid", Winq.mobileid);

                if (checkOutTheInternetConnection()) {
                    finishSignUp.setText("");
                    signupProgress.setVisibility(View.VISIBLE);
                    sendProfileModifyData();
                } else return;
                break;

            case R.id.sign_up_add:
                //Hozzá adjuk a begépelt hashtaget a többihez
                String addedHashtag = settingsActivities.getText().toString();

                //Üres hashtaget nem lehet hozzáadni
                if (TextUtils.isEmpty(addedHashtag)) {
                    return;
                }

                String currentHashtags = settingsAllActivities.getText().toString();
                settingsAllActivities.setText(currentHashtags + "  #" + addedHashtag);
                settingsActivities.setText("");
                hashtagList.add(addedHashtag);
                break;

            case R.id.sign_up_activities_undo:
                //A sorban utolsó hashtaget töröljük
                if (hashtagList.size() != 0) {
                    hashtagList.remove(hashtagList.size() - 1);
                    String hashtagsAfterUndo = "";
                    int i = 0;
                    while (i <= (hashtagList.size() - 1)) {
                        settingsAllActivities.setText(hashtagsAfterUndo + "  #" + hashtagList.get(i));
                        hashtagsAfterUndo = settingsAllActivities.getText().toString();
                        i++;
                    }
                    if (hashtagList.size() == 0)
                        settingsAllActivities.setText("");
                }
                break;
        }
    }

    private void sendProfileModifyData() {

        Map<String, Object> map;
        map = new HashMap<>();

        map.put("apikey", "a");
        map.put("username", Winq.getCurrentUserProfileData().getUsername());
        map.put("password", Winq.getCurrentUserProfileData().getPassword());
        map.put("facebookid", "no");
        map.put("email", Winq.getCurrentUserProfileData().getEmail());
        map.put("fullname", userParams.get("fullname"));
        map.put("fiulany", userParams.get("sexType"));
        map.put("user_born", userParams.get("birthDay") + "." + userParams.get("birthMonth") + "." + userParams.get("birthYear"));
        map.put("user_country_short", userParams.get("country"));
        map.put("user_interest", userParams.get("intrest"));
        map.put("user_behavior", userParams.get("intrest"));
        map.put("user_activity", userParams.get("activities"));
        map.put("user_description", userParams.get("description"));
        map.put("user_looking", "Egyfejű lányt");
        map.put("mobileid", Winq.mobileid);
        map.put("user_type", "{\"123\",\"532\"}");

        NetworkManager.getInstance().modifyProfile(map, new ProfileModifyCallback() {
            @Override
            public void forwardResponse(ProfileModifyResponse profileModifyResponse) {
                if (profileModifyResponse.getSuccess() == 1) {
                    Log.v("Registration:", "User successfully modified his profile");

                    relogin();
                } else {
                    Toast.makeText(getApplicationContext(), profileModifyResponse.getError().get(0), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {

                Toast.makeText(getApplicationContext(), networkError.getThrowable().getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void relogin() {

        Map<String, Object> map;
        map = new HashMap<>();

        map.put("username", Winq.getCurrentUserProfileData().getUsername());
        map.put("password", Winq.getCurrentUserProfileData().getPassword());
        map.put("mobileid", Winq.mobileid);
        map.put("facebookid", Winq.getCurrentUserProfileData().getFacebookid());
        map.put("apikey", "a");

        NetworkManager.getInstance().login(map, new LoginCallback() {
            @Override
            public void forwardResponse(LoginResponse loginResponse) {

                if (loginResponse.getSuccess() == 1) {

                    // Válasz rendben
                    Log.v("Login_OK:",
                            "FullName= " + loginResponse.getData().getProfileData().getFullName());

                    Toast.makeText(getApplicationContext(), "Sikeresen megváltoztattad az adataid", Toast.LENGTH_LONG).show();

                    finishSignUp.setText("finish");
                    signupProgress.setVisibility(View.GONE);

                    // Profile adatok mentése
                    Winq.saveCurrentUserProfileData(loginResponse.getData().getProfileData());

                    // Kezdő layout indítása
                    Intent reOpenProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(reOpenProfile);
                    overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_down);
                } else {
                    // Válasz visszautasítva
                    Log.w("Login_Refused:",
                            "FirstErrorText= " + loginResponse.getError().get(0));
                    Toast.makeText(getApplicationContext(), loginResponse.getError().get(0), Toast.LENGTH_LONG).show();
                    finishSignUp.setText("finish");
                    signupProgress.setVisibility(View.GONE);
                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("Login_Error:", networkError.getThrowable().getLocalizedMessage());

                Toast.makeText(getApplicationContext(), networkError.getThrowable().getMessage(), Toast.LENGTH_LONG).show();
                finishSignUp.setText("finish");
                signupProgress.setVisibility(View.GONE);
            }
        });
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
}
