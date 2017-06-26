package winq.keult.foxplan.hu.winq;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by tomi on 2017.06.21..
 */

public class ShareOpinionDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public LinearLayout bad;
    public LinearLayout medium;
    public LinearLayout great;

    public ShareOpinionDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.share_opinion_dialog);

        bad = (LinearLayout) findViewById(R.id.opinion_bad);
        medium = (LinearLayout) findViewById(R.id.opinion_medium);
        great = (LinearLayout) findViewById(R.id.opinion_great);

        bad.setOnClickListener(this);
        medium.setOnClickListener(this);
        great.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        EventDetailsActivity details = new EventDetailsActivity();

        switch (v.getId()) {
            case R.id.opinion_bad:
                EventDetailsActivity.rate = 1;
                details.rateEvent();
                dismiss();
                break;
            case R.id.opinion_medium:
                EventDetailsActivity.rate = 2;
                details.rateEvent();
                dismiss();
                break;
            case R.id.opinion_great:
                EventDetailsActivity.rate = 3;
                details.rateEvent();
                dismiss();
                break;
        }
    }
}
