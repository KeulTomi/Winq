package winq.keult.foxplan.hu.winq;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.keult.networking.model.ImageData;

import java.util.ArrayList;

/**
 * Story képeket sorban, időzítve megjelenítő activity
 */

public class StoryActivity extends AppCompatActivity {


    private final static int PROGBAR_REFRESH_TIME = 500; // Progressbar frissítési időköze (ezredmásodperc)
    private final static int IMAGE_DISPLAY_TIME = 5000; // Képváltás időköze (ezredmásodperc)
    private final static int PROGBAR_MAX_VAL = 1000; // Progressbar maximuma
    private final static int MSG_PROGBAR_UPDATE = 1;
    private static Context context;
    private static MessageHandler mHandler;
    private static float mProgbarDelta;
    private static int mCurrentImageNum;
    private static int mCurrentStepCount;
    private Bitmap imageView01;
    private Bitmap imageView02;
    private ArrayList<ImageData> mStoryImages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_viewer);

        context = this;

        // Progressbar max érték beállítása
        ((ProgressBar) findViewById(R.id.image_viewer_progbar)).setMax(PROGBAR_MAX_VAL);

        // Első kép beállítása
        imageView01 = getIntent().getParcelableExtra(getString(R.string.intent_key_story_bitmap));
        ((ImageView) findViewById(R.id.popup_image_view_01)).setImageBitmap(imageView01);

        // Képek url-jeit tartalmazó lista kicsomagolása
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mStoryImages = (ArrayList<ImageData>) bundle.getSerializable(getString(R.string.intent_key_story_images));
        }

        // Handler inicializálása üzenetek kezeléséhez
        mHandler = new MessageHandler();

        // ProgressBar léptetéshez használt változók inicializálása
        mProgbarDelta = PROGBAR_MAX_VAL / ((mStoryImages.size() + 1) * IMAGE_DISPLAY_TIME / PROGBAR_REFRESH_TIME);
        mCurrentImageNum = 1;
        mCurrentStepCount = 0;

        // Progressbar indításaa
        Message msg = new Message();
        msg.what = MSG_PROGBAR_UPDATE;
        msg.obj = findViewById(R.id.image_viewer_progbar);
        msg.arg1 = (int) (mProgbarDelta * mCurrentStepCount * mCurrentImageNum);
        mHandler.sendMessage(msg);
    }

    private static class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_PROGBAR_UPDATE:

                    // Progressbar frissítése
                    ProgressBar progressBar = (ProgressBar) msg.obj;
                    progressBar.setProgress(msg.arg1);

                    mCurrentStepCount++;
                    msg.arg1 = (int) (mProgbarDelta * mCurrentStepCount * mCurrentImageNum);

                    if (msg.arg1 < PROGBAR_MAX_VAL) {
                        msg.what = MSG_PROGBAR_UPDATE;
                        msg.obj = progressBar;
                        mHandler.sendMessageDelayed(msg, PROGBAR_REFRESH_TIME);
                    } else
                        ((AppCompatActivity) context).finish();

                    break;
            }

        }
    }

}
