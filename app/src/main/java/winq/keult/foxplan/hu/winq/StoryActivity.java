package winq.keult.foxplan.hu.winq;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Story képeket sorban, időzítve megjelenítő activity
 */

public class StoryActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_viewer);

        Bitmap firstImage = getIntent().getParcelableExtra(getString(R.string.intent_key_story_viewer));
        ((ImageView) findViewById(R.id.popup_image_view_01)).setImageBitmap(firstImage);
    }
}
