package winq.keult.foxplan.hu.winq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class ProfileSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView backToProfileBtn;
    private TextView nextTo2PartBtn;
    private EditText settingsFullName;
    private EditText settingsEmail;
    private EditText settingsPassword;
    private EditText settingsCountry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        backToProfileBtn = (TextView) findViewById(R.id.back_to_login_btn);
        nextTo2PartBtn = (TextView) findViewById(R.id.next_to_2_part_btn);
        settingsFullName = (EditText) findViewById(R.id.sign_up_fullnaem_txt);
        settingsEmail = (EditText) findViewById(R.id.sign_up_email_txt);
        settingsPassword = (EditText) findViewById(R.id.sign_up_password_txt);
        settingsCountry = (EditText) findViewById(R.id.sign_up_country_txt);

        backToProfileBtn.setText("PROFILE");
        settingsEmail.setVisibility(View.GONE);
        settingsPassword.setVisibility(View.GONE);


        backToProfileBtn.setOnClickListener(this);
        nextTo2PartBtn.setOnClickListener(this);

        ScaleHelper.scaleViewAndChildren(findViewById(R.id.sign_up_root), Winq.getScaleX(), Winq.getScaleY());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_to_login_btn:
                finish();
                overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_down);
                break;
            case R.id.next_to_2_part_btn:

                String strCountry = settingsCountry.getText().toString();
                String strFullName = settingsCountry.getText().toString();

                if (TextUtils.isEmpty(strCountry)) {
                    settingsCountry.setError("Country field cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(strFullName)) {
                    settingsFullName.setError("Name field cannot be empty");
                    return;
                }

                Intent start2Part = new Intent(this, ProfileSettingsActivity1.class);
                Bundle bundleSendParamsToNextStage = new Bundle();
                HashMap<String, Object> userParams = new HashMap<>();

                userParams.put("fullname", settingsFullName.getText().toString());
                userParams.put("country", settingsCountry.getText().toString());

                bundleSendParamsToNextStage.putSerializable("messageBody", userParams);
                start2Part.putExtra("signUpBundle", bundleSendParamsToNextStage);

                startActivity(start2Part);
                overridePendingTransition(R.anim.activity_slide_left, R.anim.activity_slide_right);
                break;
        }
    }
}

