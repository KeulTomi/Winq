package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

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
                    sendSignUpData();
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

    private void sendSignUpData() {

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
