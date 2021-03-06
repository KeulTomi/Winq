package winq.keult.foxplan.hu.winq;

import android.util.Log;

import com.example.keult.networking.NetworkManager;
import com.example.keult.networking.callback.ConditionsCallback;
import com.example.keult.networking.callback.DateAddCallback;
import com.example.keult.networking.callback.DateDoNotLikeCallback;
import com.example.keult.networking.callback.DateListCallback;
import com.example.keult.networking.callback.EventJoinCallback;
import com.example.keult.networking.callback.EventJoinedByIdCallback;
import com.example.keult.networking.callback.EventListCallback;
import com.example.keult.networking.callback.EventsSearchCallback;
import com.example.keult.networking.callback.ExploreCallback;
import com.example.keult.networking.callback.GeneralSearchCallback;
import com.example.keult.networking.callback.ImageUploadCallback;
import com.example.keult.networking.callback.InterestTypesCallback;
import com.example.keult.networking.callback.LoginCallback;
import com.example.keult.networking.callback.ProfileImagesCallback;
import com.example.keult.networking.callback.SignUpCallback;
import com.example.keult.networking.error.NetworkError;
import com.example.keult.networking.model.ConditionsResponse;
import com.example.keult.networking.model.DateAddResponse;
import com.example.keult.networking.model.DateDoNotLikeResponse;
import com.example.keult.networking.model.DateListResponse;
import com.example.keult.networking.model.EventJoinResponse;
import com.example.keult.networking.model.EventJoinedByIdResponse;
import com.example.keult.networking.model.EventListResponse;
import com.example.keult.networking.model.ExploreResponse;
import com.example.keult.networking.model.GeneralSearchResponse;
import com.example.keult.networking.model.ImageUploadResponse;
import com.example.keult.networking.model.InterestTypesResponse;
import com.example.keult.networking.model.LoginResponse;
import com.example.keult.networking.model.ProfileImagesResponse;
import com.example.keult.networking.model.SignUpResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 *  Az API teszteléséhez kívülről egyszerűen hívható metódusok
 *  TODO: A végleges verzióban ez nem kell, csak a minták követésére használatos
 */

class ApiTester {

    static void login(Map<String, Object> map) {

        if ( map == null ) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "ios@test.com");
            map.put("password", "test");
            map.put("facebookid", "no");
        }

        NetworkManager.getInstance().login(map, new LoginCallback() {
            @Override
            public void forwardResponse(LoginResponse loginResponse) {
                if (loginResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("Login_OK:",
                            "FullName= " + loginResponse.getData().getProfileData().getFullName());

                } else {
                    // Válasz visszautasítva
                    Log.w("Login_Refused:",
                            "FirstErrorText= " + loginResponse.getError().get(0));


                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("Login_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void signup(Map<String, Object> map ) {

        if ( map == null ) {
            map = new HashMap<>();

            map.put("apikey", "a");
            map.put("username", "keul-4@keul.com");
            map.put("password", "asd123");
            map.put("facebookid", "no");
            map.put("email", "keul-4@keul.com");
            map.put("fullname", "Keul Tamás-3");
            map.put("fiulany", "0");
            map.put("user_born", "1998.04.24");
            map.put("user_country_short", "GER");
            map.put("user_interest", "1");
            map.put("user_looking", "Egyfejű lányt");
            map.put("user_behavior", "Minden nap piálok");
            map.put("user_activity", "#vadfiú #alkohol #agresszió");
            map.put("user_type", "{\"123\",\"532\"}");
            map.put("user_description", "Egy fejű fiú");
            map.put("mobileid", "no");
        }

        NetworkManager.getInstance().signup(map, new SignUpCallback() {
            @Override
            public void forwardResponse(SignUpResponse signUpResponse) {

                if (signUpResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("SignUp_OK:", "User successfully signed up");
                } else {
                    // Válasz visszautasítva
                    Log.w("Login_Refused:",
                            "FirstErrorText= " + signUpResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("SignUp_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void getASZF(Map<String, Object> map) {

        if ( map == null ) {
            map = new HashMap<>();
            map.put("apikey", "a");
        }

        NetworkManager.getInstance().getASZF(map, new ConditionsCallback() {
            @Override
            public void forwardResponse(ConditionsResponse conditionsResponse) {

                if (conditionsResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("getASZF_OK:", conditionsResponse.getData().getPages());
                } else {
                    // Válasz visszautasítva
                    Log.w("getASZF_Refused:",
                            "FirstErrorText= " + conditionsResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.v("getASZF_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void requestForDate(Map<String, Object> map) {

        if ( map == null ) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "keul-4@keul.com");
            map.put("password", "asd123");
            map.put("facebookid", "no");
            map.put("to_user", "1");
        }

        NetworkManager.getInstance().requestForDate(map, new DateAddCallback() {
            @Override
            public void forwardResponse(DateAddResponse dateAddResponse) {

                if (dateAddResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("addDate_OK:", dateAddResponse.getData()[0]);
                } else {
                    // Válasz visszautasítva
                    Log.w("addDate_Refused:",
                            "FirstErrorText= " + dateAddResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("addDate_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void dontLikeDate(Map<String, Object> map) {

        if ( map == null ) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "keul-4@keul.com");
            map.put("password", "asd123");
            map.put("facebookid", "no");
            map.put("to_user", "1");
        }

        NetworkManager.getInstance().dontLikeDate(map, new DateDoNotLikeCallback() {
            @Override
            public void forwardResponse(DateDoNotLikeResponse dateDoNotLikeResponse) {

                if (dateDoNotLikeResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("dontLikeDate_OK:", dateDoNotLikeResponse.getData()[0]);
                } else {
                    // Válasz visszautasítva
                    Log.w("dontLikeDate_Refused:",
                            "FirstErrorText= " + dateDoNotLikeResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("dontLikeDate_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void listDates(Map<String, Object> map) {

        if ( map == null ) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "ios@test.com");
            map.put("password", "test");
            map.put("facebookid", "no");
        }

        NetworkManager.getInstance().listDates(map, new DateListCallback() {
            @Override
            public void forwardResponse(DateListResponse dateListResponse) {

                if (dateListResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("listDates_OK:",
                            "FullName= " + dateListResponse.getData().getDateList().get(0).getFullName());
                } else {
                    // Válasz visszautasítva
                    Log.w("listDates_Refused:",
                            "FirstErrorText= " + dateListResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("listDates_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void listEvents(Map<String, Object> map) {

        if ( map == null ) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "ios@test.com");
            map.put("password", "test");
            map.put("facebookid", "no");
            map.put("page", "0");
            map.put("homepage", "0");
        }

        NetworkManager.getInstance().listEvents(map, new EventListCallback() {
            @Override
            public void forwardResponse(EventListResponse eventListResponse) {

                if (eventListResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("listEvents_OK:",
                            "events_all= " + Integer.toString(eventListResponse.getData().getEventsCount()));
                } else {
                    // Válasz visszautasítva
                    Log.w("listEvents_Refused:",
                            "FirstErrorText= " + eventListResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("listEvents_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void listEventsById(Map<String, Object> map) {

        if (map == null) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "ios@test.com");
            map.put("password", "test");
            map.put("facebookid", "no");
            map.put("userid", "17");
        }

        NetworkManager.getInstance().listJoinedEventsById(map, new EventJoinedByIdCallback() {
            @Override
            public void forwardResponse(EventJoinedByIdResponse eventJoinedByIdResponse) {

                if (eventJoinedByIdResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("listEventsById_OK:",
                            "title= " + eventJoinedByIdResponse.getData().getEventJoinedList().get(0).getTitle());
                } else {
                    // Válasz visszautasítva
                    Log.w("listEventsById_Refused:",
                            "FirstErrorText= " + eventJoinedByIdResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("listEventsById_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void searchEvents(Map<String, Object> map) {

        if ( map == null ) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "ios@test.com");
            map.put("password", "test");
            map.put("facebookid", "no");
            map.put("searchinput", "a");
            map.put("page", "0");
            map.put("homepage", "0");
        }

        NetworkManager.getInstance().searchEvents(map, new EventsSearchCallback() {
            @Override
            public void forwardResponse(EventListResponse eventListResponse) {

                if (eventListResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("searchEvents_OK:",
                            "events_all= " + Integer.toString(eventListResponse.getData().getEventsCount()));
                } else {
                    // Válasz visszautasítva
                    Log.w("searchEvents_Refused:",
                            "FirstErrorText= " + eventListResponse.getError().get(0));

                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("searchEvents_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void searchGeneral(Map<String, Object> map) {

        if ( map == null ) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "ios@test.com");
            map.put("password", "test");
            map.put("facebookid", "no");
            map.put("searchinput", "a");
            map.put("page", "0");
            map.put("homepage", "0");
        }

        NetworkManager.getInstance().searchGeneral(map, new GeneralSearchCallback() {
            @Override
            public void forwardResponse(GeneralSearchResponse generalSearchResponse) {

                if (generalSearchResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("searchGeneral_OK:",
                            "events_all= " + Integer.toString(generalSearchResponse.getData().getEventsCount()));

                    Log.v("searchGeneral_OK:",
                            "users_all= " + Integer.toString(generalSearchResponse.getData().getUsersCount()));
                } else {
                    // Válasz visszautasítva
                    Log.w("searchEvents_Refused:",
                            "FirstErrorText= " + generalSearchResponse.getError().get(0));
                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("searchGeneral_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void exploreUsers(Map<String, Object> map) {

        if (map == null) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "ios@test.com");
            map.put("password", "test");
            map.put("facebookid", "no");
        }

        NetworkManager.getInstance().exploreUsers(map, new ExploreCallback() {
            @Override
            public void forwardResponse(ExploreResponse exploreResponse) {

                if (exploreResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("exploreUsers_OK:",
                            "FullName(first_result)= "
                                    + exploreResponse.getData().getUsersList().get(0).getFullName());
                } else {
                    // Válasz visszautasítva
                    Log.w("exploreUsers_Refused:",
                            "FirstErrorText= " + exploreResponse.getError().get(0));
                }
            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("exploreUsers_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void joinToEvent(Map<String, Object> map) {

        if (map == null) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "ios@test.com");
            map.put("password", "test");
            map.put("facebookid", "no");
            map.put("eventid", "33"); // Csak egyszer lehet egy event-hez csatlakozni, a számot változtatni kell
        }

        NetworkManager.getInstance().joinToEvent(map, new EventJoinCallback() {
            @Override
            public void forwardResponse(EventJoinResponse eventJoinResponse) {

                if (eventJoinResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("joinToEvent_OK:",
                            "Data= "
                                    + eventJoinResponse.getData()[0]);
                } else {
                    // Válasz visszautasítva
                    Log.w("joinToEvent_Refused:",
                            "FirstErrorText= " + eventJoinResponse.getError().get(0));
                }

            }

            @Override
            public void forwardError(NetworkError networkError) {
                Log.e("exploreUsers_Error:", networkError.getThrowable().getLocalizedMessage());
            }
        });
    }

    static void getProfileImages(Map<String, Object> map) {

        if (map == null) {
            map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "ios@test.com");
            map.put("password", "test");
            map.put("facebookid", "no");
            map.put("limit", "0");
            map.put("story", "0");
            map.put("userid", "17");
        }

        NetworkManager.getInstance().getProfileImages(map, new ProfileImagesCallback() {
            @Override
            public void forwardResponse(ProfileImagesResponse profileImagesResponse) {

                if (profileImagesResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("getImagest_OK:",
                            "ImgUrl= "
                                    + profileImagesResponse.getData().getImageList().get(0).getUrl());
                } else {
                    // Válasz visszautasítva
                    Log.w("getImages_Refused:",
                            "FirstErrorText= " + profileImagesResponse.getError().get(0));
                }

            }

            @Override
            public void forwardError(NetworkError networkError) {

            }
        });
    }

    static void listInterstTypes(Map<String, Object> map) {

        if (map == null) {
            map = new HashMap<>();
            map.put("apikey", "a");
        }

        NetworkManager.getInstance().listInterestTypes(map, new InterestTypesCallback() {
            @Override
            public void forwardResponse(InterestTypesResponse interestTypesResponse) {
                if (interestTypesResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("listIntrst_OK:",
                            "Type1= "
                                    + interestTypesResponse.getData().getInterestList().get(0).getName());
                } else {
                    // Válasz visszautasítva
                    Log.w("listIntrst_Refused:",
                            "FirstErrorText= " + interestTypesResponse.getError().get(0));
                }
            }

            @Override
            public void forwardError(NetworkError networkError) {

            }
        });
    }

    static void imageUpload(Map<String, RequestBody> mapRequest, MultipartBody.Part filePart) {

        if (mapRequest == null) {

            mapRequest = new HashMap<>();
            mapRequest.put("apikey", RequestBody.create(MediaType.parse("text/plain"), "a"));
            mapRequest.put("username", RequestBody.create(MediaType.parse("text/plain"), "ios@test.com"));
            mapRequest.put("password", RequestBody.create(MediaType.parse("text/plain"), "test"));
            mapRequest.put("facebookid", RequestBody.create(MediaType.parse("text/plain"), "no"));
            mapRequest.put("comment", RequestBody.create(MediaType.parse("text/plain"), "that's sooo cool!!"));
            mapRequest.put("main", RequestBody.create(MediaType.parse("text/plain"), "profile"));

            /*map = new HashMap<>();
            map.put("apikey", "a");
            map.put("username", "ios@test.com");
            map.put("password", "test");
            map.put("facebookid", "no");
            map.put("comment", "uploaded from android");
            map.put("main", "profile");*/
        }

        NetworkManager.getInstance().uploadImage(mapRequest, filePart, new ImageUploadCallback() {
            @Override
            public void forwardResponse(ImageUploadResponse imageUploadResponse) {
                if (imageUploadResponse.getSuccess() == 1) {
                    // Válasz rendben
                    Log.v("Upload_OK:",
                            "Type1= "
                                    + imageUploadResponse.getData()[0]);
                } else {
                    // Válasz visszautasítva
                    Log.w("Upload_Refused:",
                            "FirstErrorText= " + imageUploadResponse.getData()[0]);
                }
            }

            @Override
            public void forwardError(NetworkError networkError) {

            }
        });
    }
}
