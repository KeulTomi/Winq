package winq.keult.foxplan.hu.winq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class SignUpActivity1 extends AppCompatActivity implements View.OnClickListener {

    private ImageView backToFirstPartBtn;
    private TextView nextTo3PartBtn;
    private TextView sexTypeWoman;
    private TextView sexTypeMan;
    private DatePicker signUpBirthDate;
    private Spinner signUpIntrests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up1);

        backToFirstPartBtn = (ImageView) findViewById(R.id.back_to_signup_first_part);
        nextTo3PartBtn = (TextView) findViewById(R.id.next_to_3_part_btn);
        sexTypeWoman = (TextView) findViewById(R.id.sex_type_woman);
        sexTypeMan = (TextView) findViewById(R.id.sex_type_man);
        signUpBirthDate = (DatePicker) findViewById(R.id.birth_date);
        signUpIntrests = (Spinner) findViewById(R.id.interests_spinner);

        sexTypeWoman.setOnClickListener(this);
        sexTypeMan.setOnClickListener(this);
        backToFirstPartBtn.setOnClickListener(this);
        nextTo3PartBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        boolean isWomanChoosen = true;

        switch (view.getId()){
            case R.id.back_to_signup_first_part:
                finish();
                overridePendingTransition(R.anim.activity_slide_left2, R.anim.activity_slide_right2);
                break;
            case R.id.next_to_3_part_btn:
                Intent start2Part = new Intent(this, SignUpActivity2.class);
                startActivity(start2Part);

                Bundle bundleSecondPart = getIntent().getBundleExtra("signUpBundle");
                HashMap<String, Object> userParams = (HashMap<String, Object>) bundleSecondPart.get("messageBody");

                if (isWomanChoosen){
                    userParams.put("sexType", "2");
                }
                else {
                    userParams.put("sexType", "1");
                }

                int day = signUpBirthDate.getDayOfMonth();
                int month = signUpBirthDate.getMonth() + 1;
                int year = signUpBirthDate.getYear();

                userParams.put("birthDay", day);
                userParams.put("birthMonth", month);
                userParams.put("birthYear", year);

                userParams.put("intrest", signUpIntrests.getSelectedItem().toString());

                bundleSecondPart.putSerializable("messageBody", userParams);
                start2Part.putExtra("signUpBundle", bundleSecondPart);

                overridePendingTransition(R.anim.activity_slide_left, R.anim.activity_slide_right);
                break;
            case R.id.sex_type_woman:
                //Átszinezzük kiválaszztáskor az adott TextView-t
                isWomanChoosen = true;

                sexTypeMan.setBackgroundResource(R.drawable.sex_type_border);
                sexTypeMan.setTextColor(Color.parseColor("#00ccd2"));
                sexTypeWoman.setBackgroundColor(Color.parseColor("#00ccd2"));
                sexTypeWoman.setTextColor(Color.WHITE);
                break;
            case R.id.sex_type_man:
                //Átszinezzük kiválaszztáskor az adott TextView-t
                isWomanChoosen = false;

                sexTypeWoman.setBackgroundResource(R.drawable.sex_type_border);
                sexTypeWoman.setTextColor(Color.parseColor("#00ccd2"));
                sexTypeMan.setBackgroundColor(Color.parseColor("#00ccd2"));
                sexTypeMan.setTextColor(Color.WHITE);
                break;
        }
    }
}
