package winq.keult.foxplan.hu.winq;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by tomi on 2017.07.05..
 */

public class InternetProblemDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public TextView cancel;
    public TextView again;
    public String url;

    public InternetProblemDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.internet_problem_dialog);

        cancel = (TextView) findViewById(R.id.internet_problem_cancel);
        again = (TextView) findViewById(R.id.internet_problem_again);

        cancel.setOnClickListener(this);
        again.setOnClickListener(this);

        ScaleHelper.scaleViewAndChildren(findViewById(R.id.internet_problem_root), Winq.getScaleX(), Winq.getScaleY());

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.internet_problem_cancel:
                dismiss();
                break;

            case R.id.internet_problem_again:
                ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                        dismiss();

                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // connected to the mobile network
                        dismiss();
                    }
                } else {
                    // not connected to the internet
                    dismiss();
                    InternetProblemDialog dialog = new InternetProblemDialog(c);
                    dialog.show();
                }
                break;
        }
    }
}
