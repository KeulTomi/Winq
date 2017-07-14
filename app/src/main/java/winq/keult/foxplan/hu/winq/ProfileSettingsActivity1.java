package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.InterestTypesCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.InterestData;
import com.example.keult.networking.model.InterestTypesResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileSettingsActivity1 extends AppCompatActivity implements View.OnClickListener {

    private ImageView backToFirstPartBtn;
    private TextView nextTo3PartBtn;
    private TextView sexTypeWoman;
    private TextView sexTypeMan;
    private DatePicker settingsBirthDate;
    private Spinner settingsIntrests;
    private ArrayList<String> interestTypeList;
    private ArrayList<String> interestIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up1);

        setInterestSpinner();

        backToFirstPartBtn = (ImageView) findViewById(R.id.back_to_signup_first_part);
        nextTo3PartBtn = (TextView) findViewById(R.id.next_to_3_part_btn);
        sexTypeWoman = (TextView) findViewById(R.id.sex_type_woman);
        sexTypeMan = (TextView) findViewById(R.id.sex_type_man);
        settingsBirthDate = (DatePicker) findViewById(R.id.birth_date);
        settingsIntrests = (Spinner) findViewById(R.id.interests_spinner);

        //2000-nél később születettek nem regisztrálhatnak


        sexTypeWoman.setOnClickListener(this);
        sexTypeMan.setOnClickListener(this);
        backToFirstPartBtn.setOnClickListener(this);
        nextTo3PartBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        boolean isWomanChoosen = true;

        switch (view.getId()) {
            case R.id.back_to_signup_first_part:
                finish();
                overridePendingTransition(R.anim.activity_slide_left2, R.anim.activity_slide_right2);
                break;
            case R.id.next_to_3_part_btn:
                Intent start2Part = new Intent(this, ProfileSettingsActivity2.class);

                Bundle bundleSecondPart = getIntent().getBundleExtra("signUpBundle");
                HashMap<String, Object> userParams = (HashMap<String, Object>) bundleSecondPart.get("messageBody");

                if (isWomanChoosen) {
                    userParams.put("sexType", "2");
                } else {
                    userParams.put("sexType", "1");
                }

                int day = settingsBirthDate.getDayOfMonth();
                int month = settingsBirthDate.getMonth() + 1;
                int year = settingsBirthDate.getYear();

                if (month < 10) {
                    userParams.put("birthDay", "0" + String.valueOf(day));
                } else {
                    userParams.put("birthDay", String.valueOf(day));
                }

                userParams.put("birthMonth", String.valueOf(month));
                userParams.put("birthYear", String.valueOf(year));

                if (settingsIntrests.getSelectedItem() == null) {
                    userParams.put("intrest", "0");
                } else {
                    userParams.put("intrest", interestIdList.get(settingsIntrests.getSelectedItemPosition()));
                }

                bundleSecondPart.putSerializable("messageBody", userParams);
                start2Part.putExtra("signUpBundle", bundleSecondPart);

                startActivity(start2Part);
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

    public void setInterestSpinner() {

        interestTypeList = new ArrayList<>();
        interestIdList = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("apikey", "a");

        NetworkManager.getInstance().listInterestTypes(map, new InterestTypesCallback() {
            @Override
            public void forwardResponse(InterestTypesResponse interestTypesResponse) {
                if (interestTypesResponse.getSuccess() == 1) {
                    //Válasz rendben
                    // Az érdeklődési körök kilistázása egy ArrayListbe amit majd az adapterre kötünk rá
                    for (InterestData list : interestTypesResponse.getData().getInterestList()) {
                        interestTypeList.add(list.getName());

                        try {
                            interestIdList.add(list.getId());
                        } catch (NumberFormatException e) {

                        }
                    }

                    ArrayAdapter<String> typeSpinnerAdapter = new SignUpActivity1.CustomArrayAdapter<String>(getApplicationContext(), interestTypeList);
                    //ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, interestTypeList);
                    typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    settingsIntrests.setAdapter(typeSpinnerAdapter);

                } else {
                    // Válasz visszautasítva
                    Log.w("listEvents_Refused:",
                            "FirstErrorText= " + interestTypesResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {

            }
        });
    }

    static class CustomArrayAdapter<T> extends ArrayAdapter<T> {
        public CustomArrayAdapter(Context ctx, ArrayList<String> objects) {
            super(ctx, android.R.layout.simple_spinner_item, (List<T>) objects);
        }

        //other constructors

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            //we know that simple_spinner_item has android.R.id.text1 TextView:

        /* if(isDroidX) {*/
            TextView text = (TextView) view.findViewById(android.R.id.text1);
            text.setTextColor(Color.BLACK);//choose your color :)
            text.setPadding(0, 10, 0, 10);
            text.setTextSize(18f);
        /*}*/

            return view;

        }


    }
}
