package winq.keult.foxplan.hu.winq;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.ProfileImagesCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.ImageData;
import com.example.keult.networking.model.ProfileData;
import com.example.keult.networking.model.ProfileImagesResponse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class ProfileActivity extends AppCompatActivity
        implements View.OnClickListener, UploadSelectorDialog.UploadSelectorListener {

    private static final int MSG_SET_LAYOUT_IMAGES = 1;
    private static final int MSG_GET_STORY_IMAGES = 2;
    private static final int MSG_PRELOAD_FIRST_STORY_IMAGE = 3;

    private static final int CAMERA_REQUEST = 1001;
    private static final int GALERY_REQUEST = 1002;

    private ArrayList<ImageData> mStoryImages;
    private Bitmap mFirstStoryImage;
    private Uri fileUri;
    private ProfileData mProfileData;
    private boolean isItUsersProfile;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        // Activity indításával kapott adatok tárolása
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mProfileData = (ProfileData) bundle.getSerializable(getString(R.string.intent_key_profile_data));
        }

        mHandler = new MessageHandler();

        isItUsersProfile = false;

        // Saját vs. idegen profil eldöntése
        if (mProfileData != null) {
            if (mProfileData.getId().equals(Winq.getCurrentUserProfileData().getId())) {
                isItUsersProfile = true;
            }
        }

        // Layout inicializálása attól függően, hogy saját vagy idegen profilt kell-e megnyitni
        if (isItUsersProfile) {

            // Saját profil layout elemek engedélyezése
            findViewById(R.id.connect_add_as_friend).setVisibility(View.GONE);
            findViewById(R.id.connect_messages_layout).setVisibility(View.GONE);
            findViewById(R.id.connect_extra_images_scrollView).setVisibility(View.GONE);
            findViewById(R.id.profile_image_layout_header_3).setVisibility(View.GONE);

            findViewById(R.id.profile_kamera_buttons_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.profile_settings_button).setVisibility(View.VISIBLE);
            findViewById(R.id.profile_logout_button).setVisibility(View.VISIBLE);

            // Gombok listenereinek beállítása
            findViewById(R.id.profile_take_photo_button).setOnClickListener(this);
            findViewById(R.id.profile_choose_from_gallery_button).setOnClickListener(this);
            findViewById(R.id.profile_settings_button).setOnClickListener(this);
            findViewById(R.id.profile_logout_button).setOnClickListener(this);

        } else {
            // Idegen profil layout elemeinek engedélyezése
            findViewById(R.id.profile_kamera_buttons_layout).setVisibility(View.GONE);
            findViewById(R.id.profile_settings_button).setVisibility(View.GONE);
            findViewById(R.id.profile_logout_button).setVisibility(View.GONE);

            findViewById(R.id.connect_add_as_friend).setVisibility(View.VISIBLE);
            findViewById(R.id.connect_messages_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.connect_extra_images_scrollView).setVisibility(View.VISIBLE);
            findViewById(R.id.profile_image_layout_header_3).setVisibility(View.VISIBLE);

            // Gombok listenereinek beállítása
            findViewById(R.id.connect_add_as_friend).setOnClickListener(this);
            findViewById(R.id.connect_write_message).setOnClickListener(this);
            findViewById(R.id.connect_get_message).setOnClickListener(this);

            // Relative layout átállítása
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById(R.id.profile_description).getLayoutParams();
            layoutParams.addRule(RelativeLayout.BELOW, R.id.profile_image_layout_header_3);
            findViewById(R.id.profile_description).setLayoutParams(layoutParams);
        }

        // Szövegmezők inicializálása
        initLayoutTexts(mProfileData);

        // Aktuális dátum kiírása a headerre
        Winq.setTheRealTime(
                (TextView) findViewById(R.id.profile_headertime_year),
                (TextView) findViewById(R.id.profile_headertime_month_day));

        // Profil és további képek lékérése (második paraméter: false)
        requestForImages(mProfileData, false);

        // Wissza gomb onClickListenerek beállítása
        findViewById(R.id.profile_back_points).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.profile_image:

                if (mFirstStoryImage != null) {
                    // Ha vannak story képek és van első kép a akkor StoryActivity indítása

                    // TODO: Csak tesztelési célra: Story image lista feltöltése
                    for (int i = 0; i < 2; i++) {
                        ImageData imageData = new ImageData(i);
                        mStoryImages.add(imageData);
                    }

                    Intent openStory = new Intent(this, StoryActivity.class);
                    openStory.putExtra(getString(R.string.intent_key_story_bitmap), mFirstStoryImage);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(getString(R.string.intent_key_story_images), mStoryImages);
                    openStory.putExtras(bundle);
                    startActivity(openStory);
                }

                break;

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
            case R.id.profile_take_photo_button:
                takePhotoWithCamera();
                break;

            case R.id.profile_choose_from_gallery_button:
                selectImageFromGalery();
                break;

            case R.id.profile_back_points:
                finish();
                overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_down);
                break;

            case R.id.profile_logout_button:
                //TODO: Törölni kell a felhasználó adatait
                Intent openLogIn = new Intent(this, LoginActivity.class);
                startActivity(openLogIn);
        }
    }

    private void requestForImages(ProfileData profileData, final boolean getStoryImages) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", profileData.getEmail());
        map.put("password", profileData.getPassword());
        map.put("apikey", getResources().getString(R.string.apikey));
        map.put("facebookid", profileData.getFacebookid());
        map.put("story", (getStoryImages ? "1" : "0"));
        map.put("userid", profileData.getId());

        /*HashMap<String, Object> map = new HashMap<>();
        map.put("username", "ios@test.com");
        map.put("password", "test");
        map.put("apikey", "a");
        map.put("facebookid", "no");
        map.put("story", "0");
        map.put("userid", "17");*/

        // Lekérdezendő képek számának paraméterezése
        if (getStoryImages)
            // Story képekre nincs korlátozás, minden képet le kell kérdezni
            map.put("limit", "0");
        else
            // Prfil képekből csak annyit éredemes lekérdezni, amennyinek van ehly a layouton
            map.put("limit", 1 + ((ViewGroup) findViewById(R.id.profile_image_layout)).getChildCount());

        NetworkManager.getInstance().getProfileImages(map, new ProfileImagesCallback() {

            final boolean gettingStory = getStoryImages;

            @Override
            public void forwardResponse(ProfileImagesResponse profileImagesResponse) {

                if (profileImagesResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("getImages_OK:",
                            "Success");

                    if (gettingStory) {
                        // Következő lépés: Első story kép előre töltése, hogy gyorsan meg tudjuk mutatni
                        mStoryImages = (ArrayList) profileImagesResponse.getData().getImageList();
                        mHandler.sendEmptyMessage(MSG_PRELOAD_FIRST_STORY_IMAGE);
                    } else {
                        // Következő lépés: Üzenet layout imageView-k inicializálásához
                        Message msg = new Message();
                        msg.what = MSG_SET_LAYOUT_IMAGES;
                        msg.obj = profileImagesResponse;
                        mHandler.sendMessage(msg);
                    }
                } else {
                    // Válasz visszautasítva
                    Log.w("geImages_Refused:",
                            "FirstErrorText= " + profileImagesResponse.getError().get(0));
                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("getImages_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });

    }

    private void initLayoutTexts(ProfileData profileData) {

        if (profileData != null) {

            // Országkód beállítása
            ((TextView) findViewById(R.id.profile_country_of_current_user))
                    .setText(profileData.getUserCountryShort());

            // Név beállítása
            ((TextView) findViewById(R.id.profile_fullname_of_current_user))
                    .setText(profileData.getFullName());

            // Leírás mező beállítása
            ((TextView) findViewById(R.id.profile_description))
                    .setText(profileData.getUserDescription());

            // Életkor mező beállítása
            int userAge = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(profileData.getUserborn().substring(0, 4));
            ((TextView) findViewById(R.id.profile_age_of_current_user))
                    .setText(String.valueOf(userAge));

        }
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

    private void preloadFirstStoryImage() {


        SimpleTarget target = new SimpleTarget() {

            @Override
            public void onResourceReady(Object bmp, GlideAnimation glideAnimation) {
                mFirstStoryImage = (Bitmap) bmp;
            }
        };

        // TODO: Csak teszteléshez
        Glide.with(this)
                .load("https://www.gstatic.com/webp/gallery3/1.png")
                .asBitmap()
                .into(target);

        /*Glide.with(this)
                .load(mStoryImages.get(0).getUrl())
                .asBitmap()
                .into(target);*/

    }

    /**
     * Fénykép készítés kezelése
     */
    private void takePhotoWithCamera() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ellenőrizni kell, hogy van-e egyáltalán kamera app a telefonon, különben elszáll
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {

            // Fájl és könyvtár előkészítése és Intent-ben tárolása
            fileUri = getPhotoFileUri();
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // Kamera activity hívása
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }

    }

    private void selectImageFromGalery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALERY_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            // Jelezni kell a galéria számára, hogy új fájl jött létre (különben sokáig nem látszik)
            if (fileUri != null)
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, fileUri));


            DialogFragment uploadSelectorDialog = new UploadSelectorDialog();
            uploadSelectorDialog.show(getFragmentManager(), "UploadFilePicker");

        }

        if (requestCode == GALERY_REQUEST && resultCode == RESULT_OK) {
            DialogFragment uploadSelectorDialog = new UploadSelectorDialog();
            uploadSelectorDialog.show(getFragmentManager(), "UploadFilePicker");
        }
    }

    /**
     * Fotó fájl és könyvtár előkészítése
     */
    public Uri getPhotoFileUri() {

        // Nyilvános fénykép könyvtár megnyitása
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                getString(R.string.photo_album_name));

        // Album (könyvtár) létrehozása ha még nem létezik
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(getString(R.string.photo_album_name), "Oops! Failed create "
                        + getString(R.string.photo_album_name) + " directory");
                return null;
            }
        }

        // Fotó fájl létrehozása
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + getString(R.string.app_name) + "_" + timeStamp + ".jpg");

        return Uri.fromFile(mediaFile);
    }

    @Override
    public void onProfileUploadSelected(DialogFragment dialogFragment) {

    }

    @Override
    public void onImageUploadSelected(DialogFragment dialogFragment) {

    }

    @Override
    public void onStoryUploadSelected(DialogFragment dialogFragment) {

    }

    private class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_SET_LAYOUT_IMAGES:

                    // Layout imageView-k inicializálása
                    initLayoutImages((ProfileImagesResponse) msg.obj);

                    // Következő lépés: Üzenet küldése story képek url-jeinek letöltéséhez
                    mHandler.sendEmptyMessage(MSG_GET_STORY_IMAGES);

                    break;
                case MSG_GET_STORY_IMAGES:

                    // Story képek url-jeinek lekérdezése (true= story képeket kerdezi le)
                    requestForImages(mProfileData, true);
                    break;

                case MSG_PRELOAD_FIRST_STORY_IMAGE:
                    //if ( mStoryImages.size() > 0 )
                    preloadFirstStoryImage();
                    break;
            }

        }
    }
}
