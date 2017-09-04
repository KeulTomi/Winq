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
    public LinearLayout greatPlus;
    public LinearLayout greatPlusPlus;

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

        bad = (LinearLayout) findViewById(R.id.opinion_one);
        medium = (LinearLayout) findViewById(R.id.opinion_two);
        great = (LinearLayout) findViewById(R.id.opinion_three);
        greatPlus = (LinearLayout) findViewById(R.id.opinion_four);
        greatPlusPlus = (LinearLayout) findViewById(R.id.opinion_five);

        bad.setOnClickListener(this);
        medium.setOnClickListener(this);
        great.setOnClickListener(this);
        greatPlus.setOnClickListener(this);
        greatPlusPlus.setOnClickListener(this);

        ScaleHelper.scaleViewAndChildren(findViewById(R.id.share_opinion_root), Winq.getScaleX(), Winq.getScaleY());

    }

    @Override
    public void onClick(View v) {

        //EventDetailsActivity details = new EventDetailsActivity();

        switch (v.getId()) {
            case R.id.opinion_one:
                EventDetailsActivity.rate = 1;
                //details.rateEvent();
                dismiss();
                break;
            case R.id.opinion_two:
                EventDetailsActivity.rate = 2;
                //details.rateEvent();
                dismiss();
                break;
            case R.id.opinion_three:
                EventDetailsActivity.rate = 3;
                //details.rateEvent();
                dismiss();
                break;

            case R.id.opinion_four:
                EventDetailsActivity.rate = 4;
                //details.rateEvent();
                dismiss();
                break;

            case R.id.opinion_five:
                EventDetailsActivity.rate = 5;
                //details.rateEvent();
                dismiss();
                break;
        }
    }
}
