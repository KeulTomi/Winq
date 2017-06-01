package winq.keult.foxplan.hu.winq;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView headerDateYear;
    private TextView headerDateMonthAndDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        /* Ez az 5 sor felelős azért, hogy (egyelőre csak) a profil képet kör alakban jeleníti meg. Amikor majd betöteted a profilképét
         * az adott usernek, akkor azt ilyennel kell majd megcsinálni csak az R.drawable.default_avatar helyett a megjelenítendő képet
         * rakod oda, ha pedig nincs képe az illetőnek, akkor defaultban ezt a képet allítsa be.
         * Na mármost a ProfilActivitynek a layoutjában majd fogod látni, hogy van egy sor ami tele van kis "körökkel", ezek
         * a képeit fogják majd mutatni, ott lesznek felsorolva. Ezeket a képeket is ugyanezzel a technikával ell majd megjeleníteni,
         * azzal a különbséggel, hogy ott a default az nem az R.drawable.default_avatar hanrem az R.drawable.round_for_images lesz.
         * Én szerintem a legcélszerűbb, ha valamilyen for ciklussal állítod be az összeset mivel 12 db van és ez sosem fog változni
          * (nem dinamikus lista).*/
        ImageView profileImage = (ImageView) findViewById(R.id.profile_image);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
        RoundedBitmapDrawable roundedBitmap = RoundedBitmapDrawableFactory.create(getResources(), image);
        roundedBitmap.setCircular(true);
        profileImage.setImageDrawable(roundedBitmap);

        headerDateYear = (TextView) findViewById(R.id.profile_headertime_year);
        headerDateMonthAndDay = (TextView) findViewById(R.id.profile_headertime_month_day);

        //A headerre kiírjuk a valós dátumot
        Winq.setTheRealTime(headerDateYear, headerDateMonthAndDay);
    }
}
