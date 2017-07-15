package winq.keult.foxplan.hu.winq;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.EventJoinedByIdCallback;
import com.example.keult.networking.callback.FriendsAddCallback;
import com.example.keult.networking.callback.ImageUploadCallback;
import com.example.keult.networking.callback.NewMessageCallback;
import com.example.keult.networking.callback.ProfileImagesCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.EventJoinedByIdResponse;
import com.example.keult.networking.model.EventsJoinedData;
import com.example.keult.networking.model.FriendAddResponse;
import com.example.keult.networking.model.ImageData;
import com.example.keult.networking.model.ImageUploadResponse;
import com.example.keult.networking.model.NewMessageResponse;
import com.example.keult.networking.model.ProfileData;
import com.example.keult.networking.model.ProfileImagesResponse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import winq.keult.foxplan.hu.winq.locationshare.LocationShareService;


public class ProfileActivity extends AppCompatActivity
        implements View.OnClickListener, UploadSelectorDialog.UploadSelectorListener {

    public static final int CHECK_LOCATION_SETTINGS = 1003;
    private static final int MSG_SET_LAYOUT_IMAGES = 1;
    private static final int MSG_GET_STORY_IMAGES = 2;
    private static final int MSG_PRELOAD_FIRST_STORY_IMAGE = 3;
    private static final int MSG_SET_LAYOUT_EVENTS = 4;
    private static final int CAMERA_REQUEST = 1001;
    private static final int GALERY_REQUEST = 1002;
    public static boolean writeMessage = false;
    public static String connectWroteMessage;
    private static ArrayList<EventsJoinedData> currentUserEvents;
    private final int PROFILE_PIC_UPLOAD = 1;
    private final int IMAGE_PIC_UPLOAD = 2;
    private final int STORY_PIC_UPLOAD = 3;
    private ArrayList<ImageData> mStoryImages;
    private Bitmap mFirstStoryImage;
    private Uri fileUri;
    private ProfileData mProfileData;
    private Handler mHandler;
    private String gotMessage;

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

        mHandler = new MessageHandler(this);

        boolean isItUsersProfile = false;

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
            findViewById(R.id.profile_premium_button).setVisibility(View.VISIBLE);

            // Gombok listenereinek beállítása
            findViewById(R.id.profile_take_photo_button).setOnClickListener(this);
            findViewById(R.id.profile_choose_from_gallery_button).setOnClickListener(this);
            findViewById(R.id.profile_settings_button).setOnClickListener(this);
            findViewById(R.id.profile_logout_button).setOnClickListener(this);
            findViewById(R.id.profile_premium_button).setOnClickListener(this);
            findViewById(R.id.profile_main_layout).setOnClickListener(this);

            if (!LocationShareService.isServiceRunning) {
                this.startService(
                        new Intent(this.getBaseContext(),
                                LocationShareService.class));
            }
        } else {
            // Idegen profil layout elemeinek engedélyezése
            findViewById(R.id.profile_kamera_buttons_layout).setVisibility(View.GONE);
            findViewById(R.id.profile_settings_button).setVisibility(View.GONE);
            findViewById(R.id.profile_logout_button).setVisibility(View.GONE);
            findViewById(R.id.profile_premium_button).setVisibility(View.GONE);

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

            //Ha van üzenet akkor azt beletesszük a gotMessage-be (Ettől még lehet, hogy a message üres, lesz ezt később vizsgáljuk!!!)
            if (getIntent().getStringExtra("message") != null) {
                gotMessage = getIntent().getStringExtra("message");
            }
        }

        // Szövegmezők inicializálása
        initLayoutTexts(mProfileData);

        // Aktuális dátum kiírása a headerre
        Winq.setTheRealTime(
                (TextView) findViewById(R.id.profile_headertime_year),
                (TextView) findViewById(R.id.profile_headertime_month_day));

        // Profil és további képek lékérése (második paraméter: false)
        requestForImages(mProfileData, false);

        // Ha idegen profilt jelenít meg, event-ek lekérése
        if (!isItUsersProfile)
            requestForUserEvents(mProfileData);

        // Wissza gomb onClickListenerek beállítása
        findViewById(R.id.profile_back_points).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        View view = this.getCurrentFocus();
        switch (v.getId()) {

            case R.id.connect_add_as_friend:
                if (checkOutTheInternetConnection()) {
                    requestForAddAsFriend();
                } else return;
                break;

            case R.id.profile_premium_button:
                Intent openPremium = new Intent(this, PremiumActivity.class);
                startActivity(openPremium);
                overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_stay);
                break;

            case R.id.profile_settings_button:
                Intent openSettins = new Intent(this, ProfileSettingsActivity.class);
                startActivity(openSettins);
                overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_stay);
                break;

            case R.id.profile_main_layout:
                // Check if no view has focus:
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                break;


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
                ProfilePhotosDialog cdd = new ProfilePhotosDialog(this, url);
                cdd.show();

                break;
            case R.id.connect_extra_images_01:
            case R.id.connect_extra_images_02:
            case R.id.connect_extra_images_03:
            case R.id.connect_extra_images_04:
            case R.id.connect_extra_images_05:
            case R.id.connect_extra_images_06:
            case R.id.connect_extra_images_07:
            case R.id.connect_extra_images_08:

                Winq.connectEventData.put("eventData", currentUserEvents.get((int) v.getTag(v.getId())));
                Intent openDetails = new Intent(this, EventDetailsActivity.class);
                openDetails.putExtra("eventNum", 2);
                startActivity(openDetails);

                break;

            case R.id.connect_write_message:
                SendMessageDialog sendMessageDialog = new SendMessageDialog(this);
                sendMessageDialog.show();
                sendMessageDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (writeMessage) {
                            if (checkOutTheInternetConnection()) {
                                sendWroteMessage(connectWroteMessage);
                            } else return;
                        }
                    }
                });
                break;

            case R.id.connect_get_message:
                if (gotMessage != "") {
                    GetMessageDialog getMessageDialog = new GetMessageDialog(this, gotMessage);
                    getMessageDialog.show();
                } else {
                    Toast.makeText(this, "You don't have any message", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.profile_take_photo_button:
                if (checkOutTheInternetConnection()) {
                    takePhotoWithCamera();
                } else return;
                break;

            case R.id.profile_choose_from_gallery_button:
                if (checkOutTheInternetConnection()) {
                    selectImageFromGalery();
                } else return;
                break;

            case R.id.profile_back_points:
                finish();
                overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_down);
                break;

            case R.id.profile_logout_button:
                Winq.clearCurrentUserProfileData(Winq.getCurrentUserProfileData());
                Intent openLogIn = new Intent(this, LoginActivity.class);
                startActivity(openLogIn);
        }
    }

    private void sendWroteMessage(String connectWroteMessage) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", Winq.getCurrentUserProfileData().getUsername());
        map.put("password", Winq.getCurrentUserProfileData().getPassword());
        map.put("apikey", getResources().getString(R.string.apikey));
        map.put("facebookid", Winq.getCurrentUserProfileData().getFacebookid());
        map.put("touser", mProfileData.getId());

        if (connectWroteMessage != null)
            map.put("message", connectWroteMessage);
        else
            map.put("message", "-");

        NetworkManager.getInstance().newMessage(map, new NewMessageCallback() {
            @Override
            public void forwardResponse(NewMessageResponse newMessageResponse) {

                if (newMessageResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("sendMsg_OK:",
                            "Success");

                    Toast.makeText(getApplicationContext(), "Üzenet elküldve", Toast.LENGTH_LONG).show();
                } else {
                    Log.w("sendMsg_Refused:",
                            "FirstErrorText= " + newMessageResponse.getError().get(0));
                    Toast.makeText(getApplicationContext(), newMessageResponse.getError().get(0), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.w("sendMsg_Error:", networkError.getThrowable());

                Toast.makeText(getApplicationContext(), networkError.getThrowable().getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void requestForAddAsFriend() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", Winq.getCurrentUserProfileData().getUsername());
        map.put("password", Winq.getCurrentUserProfileData().getPassword());
        map.put("apikey", getResources().getString(R.string.apikey));
        map.put("facebookid", Winq.getCurrentUserProfileData().getFacebookid());
        map.put("to_user", mProfileData.getId());

        NetworkManager.getInstance().addAsFriend(map, new FriendsAddCallback() {
            @Override
            public void forwardResponse(FriendAddResponse friendAddResponse) {
                if (friendAddResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("getImages_OK:",
                            "Success");

                    Toast.makeText(getApplicationContext(), "You are now friends", Toast.LENGTH_LONG).show();
                } else {
                    // Válasz visszautasítva
                    Log.w("geImages_Refused:",
                            "FirstErrorText= " + friendAddResponse.getError().get(0));

                    Toast.makeText(getApplicationContext(), friendAddResponse.getError().get(0), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void forwardError(NetworkError networkError) {

                Toast.makeText(getApplicationContext(), networkError.getThrowable().getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void requestForImages(ProfileData profileData, final boolean getStoryImages) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", Winq.getCurrentUserProfileData().getUsername());
        map.put("password", Winq.getCurrentUserProfileData().getPassword());
        map.put("apikey", getResources().getString(R.string.apikey));
        map.put("facebookid", Winq.getCurrentUserProfileData().getFacebookid());
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

    private void requestForUserEvents(ProfileData profileData) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", Winq.getCurrentUserProfileData().getUsername());
        map.put("password", Winq.getCurrentUserProfileData().getPassword());
        map.put("apikey", getResources().getString(R.string.apikey));
        map.put("facebookid", Winq.getCurrentUserProfileData().getFacebookid());
        map.put("userid", profileData.getId());

        NetworkManager.getInstance().listJoinedEventsById(map, new EventJoinedByIdCallback() {
            @Override
            public void forwardResponse(EventJoinedByIdResponse eventJoinedByIdResponse) {
                if (eventJoinedByIdResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("getImages_OK:",
                            "Success");

                    // Üzenet layout event imageView-k inicializálásához
                    Message msg = new Message();
                    msg.what = MSG_SET_LAYOUT_EVENTS;
                    msg.obj = eventJoinedByIdResponse;
                    mHandler.sendMessage(msg);
                    currentUserEvents = (ArrayList<EventsJoinedData>) eventJoinedByIdResponse.getData().getEventJoinedList();

                } else {
                    // Válasz visszautasítva
                    Log.w("geImages_Refused:",
                            "FirstErrorText= " + eventJoinedByIdResponse.getError().get(0));
                }
            }
            @Override
            public void forwardError(NetworkError networkError) {

            }
        });
    }

    private void initLayoutTexts(ProfileData profileData) {

        if (profileData != null) {

            // Országkód beállítása
            ((EditText) findViewById(R.id.profile_country_of_current_user))
                    .setText(profileData.getUserCountryShort());

            EditText country = (EditText) findViewById(R.id.profile_country_of_current_user);
            country.setTag(country.getKeyListener());
            country.setKeyListener(null);

            // Név beállítása
            ((EditText) findViewById(R.id.profile_fullname_of_current_user))
                    .setText(profileData.getFullName());

            EditText fullname = (EditText) findViewById(R.id.profile_fullname_of_current_user);
            fullname.setTag(fullname.getKeyListener());
            fullname.setKeyListener(null);

            // Leírás mező beállítása
            ((EditText) findViewById(R.id.profile_description))
                    .setText(profileData.getUserDescription());

            EditText description = (EditText) findViewById(R.id.profile_description);
            description.setTag(description.getKeyListener());
            description.setKeyListener(null);

            // Életkor mező beállítása
            int userAge = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(profileData.getUserborn().substring(0, 4));
            ((EditText) findViewById(R.id.profile_age_of_current_user))
                    .setText(String.valueOf(userAge));

            EditText age = (EditText) findViewById(R.id.profile_age_of_current_user);
            age.setTag(age.getKeyListener());
            age.setKeyListener(null);

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

    private void initLayoutEvents(EventJoinedByIdResponse eventJoinedByIdResponse) {

        ViewGroup imagesContainerView = ((ViewGroup) findViewById(R.id.connect_extra_images_layout));
        int imageCount = eventJoinedByIdResponse.getData().getEventJoinedList().size();

        int imagesToLoad = Math.min(
                imagesContainerView.getChildCount(),
                imageCount);

        // Képek egymás után betöltése
        for (int i = 0; i < imagesToLoad; i++) {

            String url = eventJoinedByIdResponse.getData().getEventJoinedList().get(i).getImage();
            final ImageView imageView = (ImageView) imagesContainerView.getChildAt(i);

            int eventsNumber = i;

            // Url mentése az View Tag-jébe, hogy tudjuk honnan jött a kép ha meg kell nyitni
            imageView.setTag(imageView.getId(), eventsNumber);

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

    /*
    * Fotó választása galériából
    */

    /**
     * Fénykép készítése
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


        // Fotó készült
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            // Jelezni kell a galéria számára, hogy új fájl jött létre (különben sokáig nem látszik)
            if (fileUri != null)
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, fileUri));

            DialogFragment uploadSelectorDialog = new UploadSelectorDialog();
            uploadSelectorDialog.show(getFragmentManager(), "UploadFilePicker");

        }

        // Galériából választott
        if (requestCode == GALERY_REQUEST && resultCode == RESULT_OK) {

            // Kiválasztott fájl uri tárolása
            fileUri = data.getData();

            // Feltöltendő kép típusának kiválasztása
            DialogFragment uploadSelectorDialog = new UploadSelectorDialog();
            uploadSelectorDialog.show(getFragmentManager(), "UploadFilePicker");
        }

        if (requestCode == CHECK_LOCATION_SETTINGS && resultCode == RESULT_OK) {
            this.startService(
                    new Intent(this.getBaseContext(),
                            LocationShareService.class));
        }
    }

    /**
     * Fotó fájl és könyvtár előkészítése
     */
    public Uri getPhotoFileUri() {

        // Könyvtár megnyitása vagy létrehozása
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                getString(R.string.photo_album_name));

        // Album (könyvtár) létrehozása ha még nem létezik
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(getString(R.string.photo_album_name), "Oops! Failed create "
                        + getString(R.string.photo_album_name) + " directory");
                return null;
            }
        }

        // Fájl létrehozása
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + getString(R.string.app_name) + "_" + timeStamp + ".jpg");

        return Uri.fromFile(mediaFile);
    }

    @Override
    public void onProfileUploadSelected(DialogFragment dialogFragment) {
        uploadFile(PROFILE_PIC_UPLOAD);
    }

    @Override
    public void onImageUploadSelected(DialogFragment dialogFragment) {
        uploadFile(IMAGE_PIC_UPLOAD);
    }

    @Override
    public void onStoryUploadSelected(DialogFragment dialogFragment) {
        uploadFile(STORY_PIC_UPLOAD);
    }

    private void uploadFile(int uploadType) {

        // Kulcs-érték pár összeállítása
        Map<String, RequestBody> mapRequest = new HashMap<>();
        mapRequest.put("apikey",
                RequestBody.create(MediaType.parse("text/plain"), "a"));
        mapRequest.put("username",
                RequestBody.create(MediaType.parse("text/plain"),
                        Winq.getCurrentUserProfileData().getUsername()));
        mapRequest.put("password",
                RequestBody.create(MediaType.parse("text/plain"),
                        Winq.getCurrentUserProfileData().getPassword()));
        mapRequest.put("facebookid",
                RequestBody.create(MediaType.parse("text/plain"),
                        Winq.getCurrentUserProfileData().getFacebookid()));
        mapRequest.put("comment",
                RequestBody.create(MediaType.parse("text/plain"), "uploaded from android"));

        switch (uploadType) {
            case PROFILE_PIC_UPLOAD:
                mapRequest.put("main",
                        RequestBody.create(MediaType.parse("text/plain"), "profile"));
                break;
            case IMAGE_PIC_UPLOAD:
                mapRequest.put("main",
                        RequestBody.create(MediaType.parse("text/plain"), "image"));
                break;
            case STORY_PIC_UPLOAD:
                mapRequest.put("main",
                        RequestBody.create(MediaType.parse("text/plain"), "story"));
                break;
        }

        // Fájl body összeállítása

        // Content Type és fájlhivatkozás beállítása
        String mimeType = getContentResolver().getType(fileUri);
        MediaType mediaType;
        File imageFile;

        if (mimeType != null) {
            // Galériából jött a fájl
            //

            // Fájltípus lekérdezése
            mediaType = MediaType.parse(mimeType);

            // Elérési utat konvertálni kell a galéria miatt (Content Provider adja)
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(
                    fileUri, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            // Fájl előállítása a path alapján
            imageFile = new File(filePath);
        } else {
            // Fotó készült, a típust nem kell lekérdezni, csak be kell állítani
            mediaType = MediaType.parse("image/jpeg; charset=utf-8");

            // Fájl előállítása az uri path alapján
            imageFile = new File(fileUri.getPath());
        }

        RequestBody requestFile = RequestBody.create(mediaType, imageFile);

        MultipartBody.Part fileBody =
                MultipartBody.Part.createFormData("userfile", imageFile.getName(), requestFile);

        NetworkManager.getInstance().uploadImage(mapRequest, fileBody, new ImageUploadCallback() {
            @Override
            public void forwardResponse(ImageUploadResponse imageUploadResponse) {

                if (imageUploadResponse.getSuccess() == 1) {
                    // Válasz rendben
                    requestForImages(mProfileData, false);
                    Toast.makeText(getApplicationContext(), "Succesful upload", Toast.LENGTH_LONG).show();
                } else {
                    // Válasz visszautasítva
                    Log.w("Upload_Refused:",
                            "FirstErrorText= " + imageUploadResponse.getData()[0]);

                    Toast.makeText(getApplicationContext(), imageUploadResponse.getError().get(0), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Toast.makeText(getApplicationContext(), networkError.getThrowable().getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // ApiTester.imageUpload(null, fileBody);
    }

    public void getMessage() {
        //TODO: Ide jön a kapott üzeneteknek a lekérdezése
    }

    private boolean checkOutTheInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        boolean hasWIFI = true;

        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi

            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile network
            }
        } else {
            // not connected to the internet
            hasWIFI = false;
            InternetProblemDialog dialog = new InternetProblemDialog(this);
            dialog.show();
        }

        return hasWIFI;
    }

    private class MessageHandler extends Handler {

        AppCompatActivity activity;

        MessageHandler(AppCompatActivity callingActivity) {

        }

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
                case MSG_SET_LAYOUT_EVENTS:
                    //Toast.makeText(activity, getString(R.string.usr_msg_upload_success), Toast.LENGTH_LONG).show();
                    initLayoutEvents((EventJoinedByIdResponse) msg.obj);
                    break;
            }

        }
    }
}
