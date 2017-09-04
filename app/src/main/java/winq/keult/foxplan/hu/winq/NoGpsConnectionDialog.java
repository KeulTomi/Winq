package winq.keult.foxplan.hu.winq;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by tomi on 2017.07.15..
 */

public class NoGpsConnectionDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public TextView cancel;

    public NoGpsConnectionDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.no_gps_connection_dialog);

        cancel = (TextView) findViewById(R.id.no_gps_popup_cancel_btn);

        cancel.setOnClickListener(this);

        ScaleHelper.scaleViewAndChildren(findViewById(R.id.no_gps_root), Winq.getScaleX(), Winq.getScaleY());

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.no_gps_popup_cancel_btn:
                dismiss();
                break;
        }
    }
}
