package winq.keult.foxplan.hu.winq;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.keult.networking.model.ImageData;

import java.util.ArrayList;

/**
 * Story képeket sorban, időzítve megjelenítő activity
 */

public class StoryActivity extends AppCompatActivity implements View.OnClickListener {


    private final static int PROGBAR_REFRESH_TIME = 500; // Progressbar frissítési időköze (ezredmásodperc)
    private final static int IMAGE_DISPLAY_TIME = 5000; // Képváltás időköze (ezredmásodperc)
    private final static int PROGBAR_MAX_VAL = 1000; // Progressbar maximuma
    private final static int MSG_PROGBAR_UPDATE = 1;
    private static final int MSG_CHANGE_IMAGE = 2;
    private final static int MSG_PRELOAD_IMAGE = 3;

    private static AppCompatActivity activity;
    private static MessageHandler mHandler;
    private static float mProgbarDelta;
    private static int mCurrentImageNum;
    private static int mCurrentStep;
    private static ImageView mImageView01;
    private static ImageView mImageView02;
    private static boolean isRunning;
    private static int mDeltaPerImage;
    private ArrayList<ImageData> mStoryImages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_viewer);

        activity = this;

        // Progressbar max érték beállítása
        ((ProgressBar) findViewById(R.id.image_viewer_progbar)).setMax(PROGBAR_MAX_VAL);

        //A Close-ra onClickListener
        ((ImageView) findViewById(R.id.close_button)).setOnClickListener(this);

        // Képek url-jeit tartalmazó lista kicsomagolása
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mStoryImages = (ArrayList<ImageData>) bundle.getSerializable(getString(R.string.intent_key_story_images));
        }

        // Handler inicializálása üzenetek kezeléséhez
        mHandler = new MessageHandler();

        // ProgressBar léptetéshez használt változók inicializálása
        mDeltaPerImage = PROGBAR_MAX_VAL / mStoryImages.size(); // Ennyit megy feljebb a progbar képenként
        float stepsPerImage = IMAGE_DISPLAY_TIME / PROGBAR_REFRESH_TIME; // Ennyiszer frissül a progbar egy kép alatt

        // Ennyit megy feljebb a progbar frissítésenként
        mProgbarDelta = mDeltaPerImage / stepsPerImage;

        mCurrentImageNum = 1; // Aktuális kép sorszáma
        mCurrentStep = 0; // Aktuális lépés sorszáma

        // Progressbar indításaa
        Message msg = new Message();
        msg.what = MSG_PROGBAR_UPDATE;
        msg.obj = findViewById(R.id.image_viewer_progbar);
        msg.arg1 = (int) (mProgbarDelta * mCurrentStep * mCurrentImageNum);
        mHandler.sendMessage(msg);
        isRunning = true;

        // Első kép beállítása
        mImageView01 = ((ImageView) findViewById(R.id.popup_image_view_01));
        mImageView01.setImageBitmap((Bitmap) getIntent().getParcelableExtra(getString(R.string.intent_key_story_bitmap)));
        mImageView01.setOnClickListener(this);
        mImageView01.setVisibility(View.VISIBLE);

        // Második kép előkészítése
        mImageView02 = ((ImageView) findViewById(R.id.popup_image_view_02));
        mImageView02.setOnClickListener(this);
        mImageView02.setVisibility(View.GONE);
        mHandler.sendEmptyMessage(MSG_PRELOAD_IMAGE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.close_button:
                isRunning = false;
                break;
            case R.id.popup_image_view_01:
            case R.id.popup_image_view_02:
                mHandler.removeMessages(MSG_PROGBAR_UPDATE);
                Message msgToSend = new Message();
                msgToSend.what = MSG_PROGBAR_UPDATE;
                msgToSend.obj = findViewById(R.id.image_viewer_progbar);
                msgToSend.arg1 = (int) ((mCurrentImageNum) * mDeltaPerImage);
                mHandler.sendMessage(msgToSend);
                break;
        }

    }

    private class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message msgReceived) {
            super.handleMessage(msgReceived);

            if (!isRunning) {
                stopActivity();
                return;
            }

            int progbarVal;
            Message msgToSend = new Message();

            switch (msgReceived.what) {
                case MSG_PROGBAR_UPDATE:

                    progbarVal = msgReceived.arg1;

                    if (progbarVal >= PROGBAR_MAX_VAL) {
                        // Elérte a progbar végét, nem kell frissíteni, kilépés az activity-ből
                        stopActivity();
                        return;
                    }

                    // Progressbar frissítése
                    ProgressBar progressBar = (ProgressBar) msgReceived.obj;
                    progressBar.setProgress(progbarVal);

                    // Képváltás vizsgálata
                    if (progbarVal >= (mDeltaPerImage * mCurrentImageNum)) {

                        // Progbar elérte a következő kép értékét

                        if (mCurrentImageNum == mStoryImages.size()) {
                            // Utolsó kép, mégsincs képváltás, kilépés az activity-ből
                            stopActivity();
                            return;
                        }

                        // Képváltás
                        mCurrentImageNum++;
                        mCurrentStep = 1;
                        mHandler.sendEmptyMessage(MSG_CHANGE_IMAGE);

                        // Következő kép lekérése a szerverről
                        mHandler.sendEmptyMessage(MSG_PRELOAD_IMAGE);
                    } else {
                        // Nincs képváltás, csak a progbar növekszik
                        mCurrentStep++;
                    }


                    // Következő frissítés időzítése
                    msgToSend.what = MSG_PROGBAR_UPDATE;
                    msgToSend.obj = progressBar;
                    msgToSend.arg1 = (int) (mProgbarDelta * mCurrentStep + (mCurrentImageNum - 1) * mDeltaPerImage);
                    mHandler.sendMessageDelayed(msgToSend, PROGBAR_REFRESH_TIME);

                    break;

                case MSG_CHANGE_IMAGE:

                    if (mCurrentImageNum % 2 == 1) {
                        // Páratlan képet kell aktívvá tenni
                        mImageView01.setVisibility(View.VISIBLE);
                        mImageView02.setVisibility(View.GONE);
                    } else {
                        // Páros képet kell aktívvá tenni
                        mImageView02.setVisibility(View.VISIBLE);
                        mImageView01.setVisibility(View.GONE);
                    }

                    // Váltás megtörtént, következő kép lekérdezése
                    mHandler.sendEmptyMessage(MSG_PRELOAD_IMAGE);

                    break;
                case MSG_PRELOAD_IMAGE:
                    requestForImage(mCurrentImageNum % 2);
                    break;
            }

        }

        private void stopActivity() {
            isRunning = false;
            activity.finish();
            activity.overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_down);
        }

        private void requestForImage(final int loadTo) {

            SimpleTarget target = new SimpleTarget() {

                @Override
                public void onResourceReady(Object receivedBmp, GlideAnimation glideAnimation) {

                    switch (loadTo) {
                        case 0:
                            mImageView01.setImageBitmap((Bitmap) receivedBmp);
                            break;
                        case 1:
                            mImageView02.setImageBitmap((Bitmap) receivedBmp);
                            break;
                    }
                }
            };

            Glide.with(activity)
                    .load(mStoryImages.get(mCurrentImageNum - 1).getUrl())
                    .asBitmap()
                    .into(target);

        }
    }
}
