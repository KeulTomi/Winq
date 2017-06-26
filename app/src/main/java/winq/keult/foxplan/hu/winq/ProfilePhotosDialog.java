package winq.keult.foxplan.hu.winq;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by tomi on 2017.06.22..
 */

public class ProfilePhotosDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public TextView close;
    public ImageView image;
    public String url;

    public ProfilePhotosDialog(Activity a, String urlConstructor) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        url = urlConstructor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profile_photos_dialog);

        close = (TextView) findViewById(R.id.profile_photos_dialog_close);
        image = (ImageView) findViewById(R.id.profile_photos_dialog_image);

        close.setOnClickListener(this);

        Glide.with(c)
                .load(url)
                .asBitmap()
                .into(image);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.profile_photos_dialog_close:
                dismiss();
                break;
        }
    }
}
