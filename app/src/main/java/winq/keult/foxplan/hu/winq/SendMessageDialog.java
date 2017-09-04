package winq.keult.foxplan.hu.winq;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by tomi on 2017.06.28..
 */

public class SendMessageDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public EditText text;
    public TextView cancel;
    public TextView send;
    public String url;

    public SendMessageDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.send_message_popup);

        cancel = (TextView) findViewById(R.id.send_message_popup_cancel_btn);
        send = (TextView) findViewById(R.id.send_message_popup_send_btn);
        text = (EditText) findViewById(R.id.send_message_popup_editText);

        cancel.setOnClickListener(this);
        send.setOnClickListener(this);

        ScaleHelper.scaleViewAndChildren(findViewById(R.id.send_meassage_root), Winq.getScaleX(), Winq.getScaleY());

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.send_message_popup_cancel_btn:
                dismiss();
                break;

            case R.id.send_message_popup_send_btn:
                ProfileActivity.writeMessage = true;
                ProfileActivity.connectWroteMessage = text.getText().toString();
                dismiss();
                break;
        }
    }
}
