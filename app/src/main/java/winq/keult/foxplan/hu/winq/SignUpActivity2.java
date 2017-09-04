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
import android.widget.TextView;
import android.widget.Toast;

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.SignUpCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.SignUpResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity2 extends AppCompatActivity implements View.OnClickListener {

    private EditText signUpDescription;
    private EditText signUpActivities;
    private TextView signUpAllActivities;
    private ImageView backToSecondPartBtn;
    private TextView finishSignUp;
    private TextView addButton;
    private TextView undoButton;
    private ArrayList hashtagList;
    private Bundle bundleThirdPart;
    private HashMap<String, Object> userParams;
    private TextView aszfCheckBox;

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
        signUpDescription = (EditText) findViewById(R.id.sign_up_description);
        signUpActivities = (EditText) findViewById(R.id.sign_up_activities);
        signUpAllActivities = (TextView) findViewById(R.id.sign_up_all_activities);
        addButton = (TextView) findViewById(R.id.sign_up_add);
        undoButton = (TextView) findViewById(R.id.sign_up_activities_undo);

        hashtagList = new ArrayList();
        bundleThirdPart = getIntent().getBundleExtra("signUpBundle");
        userParams = (HashMap<String, Object>) bundleThirdPart.get("messageBody");

        aszfCheckBox = (CheckBox) findViewById(R.id.aszf_check_box);
        aszfCheckBox.setOnClickListener(this);

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

            case R.id.aszf_check_box:
                Intent openAszf = new Intent(this, AszfActivity.class);
                startActivity(openAszf);
                overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_stay);
                break;

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

                //if (findViewById(R.id.aszf_check_box).isActivated()) {
                //  Toast.makeText(this, "El kell fogadnod az ÁSZF-et!", Toast.LENGTH_LONG).show();
                //return;
                //}

                userParams.put("description", signUpDescription.getText().toString());
                userParams.put("activities", signUpAllActivities.getText().toString());
                userParams.put("mobileid", "no");

                if (checkOutTheInternetConnection()) {
                    sendSignUpData();
                } else return;
                break;

            case R.id.sign_up_add:
                //Hozzá adjuk a begépelt hashtaget a többihez
                String addedHashtag = signUpActivities.getText().toString();

                //Üres hashtaget nem lehet hozzáadni
                if (TextUtils.isEmpty(addedHashtag)) {
                    return;
                }

                String currentHashtags = signUpAllActivities.getText().toString();
                signUpAllActivities.setText(currentHashtags + "  #" + addedHashtag);
                signUpActivities.setText("");
                hashtagList.add(addedHashtag);
                break;

            case R.id.sign_up_activities_undo:
                //A sorban utolsó hashtaget töröljük
                if (hashtagList.size() != 0) {
                    hashtagList.remove(hashtagList.size() - 1);
                    String hashtagsAfterUndo = "";
                    int i = 0;
                    while (i <= (hashtagList.size() - 1)) {
                        signUpAllActivities.setText(hashtagsAfterUndo + "  #" + hashtagList.get(i));
                        hashtagsAfterUndo = signUpAllActivities.getText().toString();
                        i++;
                    }
                    if (hashtagList.size() == 0)
                        signUpAllActivities.setText("");
                }
                break;
        }
    }

    private void sendSignUpData() {

        Map<String, Object> map;
        map = new HashMap<>();
        // TODO: Csak a kötelező mezőket tettem ide, de Dani kéri, hogy minden mezőt tegyünk be
        // TODO: ha nincs adat, adjunk üres String-et

        /*String finalHashtags = "";

        if (hashtagList.size() != 0){
            int i = 0;

            while (i == (hashtagList.size() - 1)){
                if (i == 0){
                    finalHashtags = finalHashtags + "#" + hashtagList.get(i);
                }
                finalHashtags = finalHashtags + ",#" + hashtagList.get(i);
            }
        }*/

        map.put("apikey", "a");
        map.put("username", userParams.get("email"));
        map.put("password", userParams.get("password"));
        map.put("facebookid", "no");
        map.put("email", userParams.get("email"));
        map.put("fullname", userParams.get("fullname"));
        map.put("fiulany", userParams.get("sexType"));
        map.put("user_born", userParams.get("birthDay") + "." + userParams.get("birthMonth") + "." + userParams.get("birthYear"));
        map.put("user_country_short", userParams.get("country"));
        map.put("user_interest", userParams.get("intrest"));
        map.put("user_behavior", userParams.get("intrest"));
        map.put("user_activity", userParams.get("activities"));
        map.put("user_description", userParams.get("description"));
        map.put("user_looking", "Egyfejű lányt");
        map.put("mobileid", userParams.get("mobileid"));
        map.put("user_type", "{\"123\",\"532\"}");

        NetworkManager.getInstance().signup(map, new SignUpCallback() {
            @Override
            public void forwardResponse(SignUpResponse signUpResponse) {
                if (signUpResponse.getSuccess() == 1) {
                    Log.v("Registration:", "User successfully signed up");

                    Intent finishSignUp = new Intent(getApplicationContext(), LoginActivity.class);

                    Toast.makeText(getApplicationContext(), "Sikeres regisztráció", Toast.LENGTH_LONG).show();

                    finishSignUp.putExtra("freshSignUpPassword", userParams.get("password").toString());
                    finishSignUp.putExtra("freshSignUpEmail", userParams.get("email").toString());
                    startActivity(finishSignUp);
                } else {
                    Toast.makeText(getApplicationContext(), signUpResponse.getError().get(0), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {

                Toast.makeText(getApplicationContext(), networkError.getThrowable().getMessage(), Toast.LENGTH_LONG).show();
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
