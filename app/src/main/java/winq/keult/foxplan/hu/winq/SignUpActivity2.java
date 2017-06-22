package winq.keult.foxplan.hu.winq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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


        undoButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        backToSecondPartBtn.setOnClickListener(this);
        finishSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.back_to_signup_second_part:
                finish();
                overridePendingTransition(R.anim.activity_slide_left2, R.anim.activity_slide_right2);
                break;
            case R.id.finish_sign_up:

                Intent finishSignUp = new Intent(this, LoginActivity.class);

                userParams.put("description", signUpDescription.getText().toString());
                userParams.put("activities", signUpAllActivities.getText().toString());

                sendSignUpData();

                startActivity(finishSignUp);
                break;
            case R.id.sign_up_add:
                //Hozzá adjuk a begépelt hashtaget a többihez
                String addedHashtag = signUpActivities.getText().toString();
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

        map.put("apikey", "a");
        map.put("username", userParams.get("email"));
        map.put("password", userParams.get("password"));
        map.put("facebookid", "no");
        map.put("email", userParams.get("email"));
        map.put("fullname", userParams.get("fullname"));
        map.put("fiulany", userParams.get("sexType"));
        map.put("user_born", userParams.get("birthDay") + "." + userParams.get("birthMonth") + "." + userParams.get("birthYear"));
        map.put("user_country_short", userParams.get("country"));
        map.put("user_interest", "1");
        map.put("user_behavior", userParams.get("intrest"));
        map.put("user_activity", userParams.get("activities"));
        map.put("user_activity", userParams.get("description"));
        map.put("user_looking", "Egyfejű lányt");
        map.put("mobileid", "no");
        map.put("user_type", "{\"123\",\"532\"}");

        NetworkManager.getInstance().signup(map, new SignUpCallback() {
            @Override
            public void forwardResponse(SignUpResponse signUpResponse) {
                if ( signUpResponse.getSuccess() == 1 )
                    Log.v("Registration:", "User successfully signed up");

                Toast.makeText(getApplicationContext(), "Succesful sign up", Toast.LENGTH_LONG).show();
            }

            @Override
            public void forwardError(NetworkError networkError) {

                Toast.makeText(getApplicationContext(), networkError.getThrowable().getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }
}
