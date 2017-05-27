package winq.keult.foxplan.hu.winq;

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

                // Login használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                ApiTester.login(null);

                // SignUp használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.signup(null);

                // getASZF használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.getASZF(null);

                // addDate használata: Beteszel egy map-et, vagy null-t írsz, ekkor demo adatokkal küldi
                //ApiTester.addDate(null);

                break;
        }
    }
}
