package winq.keult.foxplan.hu.winq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginEmail;
    private EditText loginPassword;
    private TextView goButton;
    private TextView signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                break;
        }
    }
}
