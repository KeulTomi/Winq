package winq.keult.foxplan.hu.winq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUpActivity2 extends AppCompatActivity implements View.OnClickListener {

    private EditText signUpDescription;
    private EditText signUpActivities;
    private TextView signUpAllActivities;
    private ImageView backToSecondPartBtn;
    private TextView finishSignUp;
    private TextView addButton;
    private TextView undoButton;
    private ArrayList hashtagList;

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
                Bundle bundleThirdPart = getIntent().getBundleExtra("signUpBundle");
                HashMap<String, Object> userParams = (HashMap<String, Object>) bundleThirdPart.get("messageBody");

                userParams.put("description", signUpDescription.getText().toString());
                userParams.put("activities", signUpAllActivities.getText().toString());

                startActivity(finishSignUp);
                break;
            case R.id.sign_up_add:
                String addedHashtag = signUpActivities.getText().toString();
                String currentHashtags = signUpAllActivities.getText().toString();
                signUpAllActivities.setText(currentHashtags + "  #" + addedHashtag);
                signUpActivities.setText("");
                hashtagList.add(addedHashtag);
                break;

            case R.id.sign_up_activities_undo:
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
}
