package winq.keult.foxplan.hu.winq;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.ProfileImagesCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.ProfileImagesResponse;

import java.util.HashMap;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

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


        // Szövegmezők beállítása
        initLayoutTexts();

        headerDateYear = (TextView) findViewById(R.id.profile_headertime_year);
        headerDateMonthAndDay = (TextView) findViewById(R.id.profile_headertime_month_day);

        //A headerre kiírjuk a valós dátumot
        Winq.setTheRealTime(headerDateYear, headerDateMonthAndDay);

        // Képek lékérése
        requestProfileData();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.profile_images_01:
            case R.id.profile_images_02:
            case R.id.profile_images_03:
            case R.id.profile_images_04:
            case R.id.profile_images_05:
            case R.id.profile_images_06:
            case R.id.profile_images_07:
            case R.id.profile_images_08:
            case R.id.profile_images_09:
            case R.id.profile_images_10:
            case R.id.profile_images_11:
            case R.id.profile_images_12:
                String url = (String) v.getTag(v.getId());

                // TODO: Popup ablakot megjeleníteni a kép részletes nézetével

                break;
        }
    }

    private void requestProfileData() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", Winq.getCurrentUserProfileData().getEmail());
        map.put("password", Winq.getCurrentUserProfileData().getPassword());
        map.put("apikey", getResources().getString(R.string.apikey));
        map.put("facebookid", Winq.getCurrentUserProfileData().getFacebookid());
        map.put("story", "0");
        map.put("userid", Winq.getCurrentUserProfileData().getId());

        // Elég annyit lekérdezni, ahány ImageView van a scrollozható layoutban
        map.put("limit", 1 + ((ViewGroup) findViewById(R.id.profile_image_layout)).getChildCount());

        NetworkManager.getInstance().getProfileImages(map, new ProfileImagesCallback() {
            @Override
            public void forwardResponse(ProfileImagesResponse profileImagesResponse) {

                if (profileImagesResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("getImages_OK:",
                            "Success");

                    // Képek betöltése
                    initLayoutImages(profileImagesResponse);

                } else {
                    // Válasz visszautasítva
                    Log.w("geImages_Refused:",
                            "FirstErrorText= " + profileImagesResponse.getError().get(0));
                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("geImages_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });

    }

    private void initLayoutTexts() {

        // Országkód beállítása
        ((TextView) findViewById(R.id.profile_country_of_current_user))
                .setText(Winq.getCurrentUserProfileData().getUserCountryShort());

        // Név beállítása
        ((TextView) findViewById(R.id.profile_fullname_of_current_user))
                .setText(Winq.getCurrentUserProfileData().getFullName());

        // Leírás mező beállítása
        ((TextView) findViewById(R.id.profile_description))
                .setText(Winq.getCurrentUserProfileData().getUserDescription());

        // Életkor mező beállítása TODO:A felhasználó korát nem tudom honnan vegyem
        ((TextView) findViewById(R.id.profile_age_of_current_user))
                .setText("21");

    }

    private void initLayoutImages(ProfileImagesResponse profileImagesResponse) {

        int imageCount = profileImagesResponse.getData().getImageList().size();
        final ImageView profileImage = (ImageView) findViewById(R.id.profile_image);

        //
        // Profilkép betöltése
        //
        if (imageCount == 0) {
            // Ha nincsenek képek akkor az alapértelmezett képet kell betölteni
            Glide.with(this).load(R.drawable.default_avatar).asBitmap()
                    .into(new BitmapImageViewTarget(profileImage) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            profileImage.setImageDrawable(circularBitmapDrawable);
                        }
                    });

            return;
        } else {

            // Ha van legalább egy kép azt be kell tölteni profilképnek
            String url = profileImagesResponse.getData().getImageList().get(0).getUrl();

            // Url mentése az View Tag-jébe, hogy tudjuk honnan jött a kép ha meg kell nyitni
            profileImage.setTag(profileImage.getId(), url);

            Glide.with(this).load(url).asBitmap().placeholder(R.drawable.default_avatar).centerCrop()
                    .into(new BitmapImageViewTarget(profileImage) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            profileImage.setImageDrawable(circularBitmapDrawable);
                        }
                    });

            // A részletes képnézethez be kell állítani az onClick listenert
            profileImage.setOnClickListener(this);

            imageCount--; // Egy kép már be lett töltve a profilkép helyére, egyel kevesebb maradt
        }


        //
        // További képek betöltése
        //

        ViewGroup imagesContainerView = ((ViewGroup) findViewById(R.id.profile_image_layout));

        // pl. a 12 helyre lehet, hogy csak 2-t kell betölteni VAGY
        // pl. a 12 helyre több, mint 12 kép lenne >> Ezért a kisebb értéket kell betölteni
        int imagesToLoad = Math.min(
                imagesContainerView.getChildCount(),
                imageCount);

        // Képek egymás után betöltése
        for (int i = 0; i < imagesToLoad; i++) {

            String url = profileImagesResponse.getData().getImageList().get(i + 1).getUrl();
            final ImageView imageView = (ImageView) imagesContainerView.getChildAt(i);

            // Url mentése az View Tag-jébe, hogy tudjuk honnan jött a kép ha meg kell nyitni
            imageView.setTag(imageView.getId(), url);

            Glide.with(this).load(url).asBitmap().placeholder(R.drawable.round_for_images).centerCrop()
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });

            // A részletes képnézethez be kell állítani az onClick listenert
            imageView.setOnClickListener(this);

        }

    }
}
