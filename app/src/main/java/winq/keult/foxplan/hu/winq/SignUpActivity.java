package winq.keult.foxplan.hu.winq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView backToLoginBtn;
    private TextView nextTo2PartBtn;
    private EditText signUpFullName;
    private EditText signUpEmail;
    private EditText signUpPassword;
    private EditText signUpCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        backToLoginBtn = (TextView) findViewById(R.id.back_to_login_btn);
        nextTo2PartBtn = (TextView) findViewById(R.id.next_to_2_part_btn);
        signUpFullName = (EditText) findViewById(R.id.sign_up_fullnaem_txt);
        signUpEmail = (EditText) findViewById(R.id.sign_up_email_txt);
        signUpPassword = (EditText) findViewById(R.id.sign_up_password_txt);
        signUpCountry = (EditText) findViewById(R.id.sign_up_country_txt);


        backToLoginBtn.setOnClickListener(this);
        nextTo2PartBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_to_login_btn:
                finish();
                overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_down);
                break;
            case R.id.next_to_2_part_btn:
                Intent start2Part = new Intent(this, SignUpActivity1.class);
                Bundle bundleSendParamsToNextStage = new Bundle();
                HashMap<String, Object> userParams = new HashMap<>();

                userParams.put("fullname", signUpFullName.getText().toString());
                userParams.put("email", signUpEmail.getText().toString());
                userParams.put("password", signUpPassword.getText().toString());
                userParams.put("country", signUpCountry.getText().toString());

                bundleSendParamsToNextStage.putSerializable("messageBody", userParams);
                start2Part.putExtra("signUpBundle", bundleSendParamsToNextStage);

                startActivity(start2Part);
                overridePendingTransition(R.anim.activity_slide_left, R.anim.activity_slide_right);
                break;
        }
    }
}
