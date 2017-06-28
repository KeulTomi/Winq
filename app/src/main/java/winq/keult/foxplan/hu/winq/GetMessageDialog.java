package winq.keult.foxplan.hu.winq;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by tomi on 2017.06.28..
 */

public class GetMessageDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public TextView text;
    public TextView cancel;
    public TextView send;
    public String message;

    public GetMessageDialog(Activity a, String getMessage) {
        super(a);
        // TODO Auto-generated constructor stub
        message = getMessage;
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.get_message_popup);

        cancel = (TextView) findViewById(R.id.get_message_popup_cancel_btn);
        text = (TextView) findViewById(R.id.get_messages_popup_textView);

        text.setText(message);

        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.get_message_popup_cancel_btn:
                dismiss();
                break;

        }
    }
}