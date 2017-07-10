package com.example.keult.networking.constant;


import com.example.keult.networking.BuildConfig;

/**
 *  API elérési utak konstansai
 */

public class ApiConstants {

    public static final String BASE_URL = BuildConfig.BASE_URL;

    // api
    public static final String EVENT_LIST = "events"; // Események listázása
    public static final String JOINED_EVENT_LIST_BY_ID = "events_joined_by_userid"; // Csatlakozott események UserId alapján
    public static final String EVENT_SEARCH = "search_events"; // Események keresése
    public static final String USER_SEARCH = "search_users"; // User-ek keresése
    public static final String GENERAL_SEARCH = "search_eventsandusers"; // Általános keresés

    // api/profile
    public static final String LOG_IN = "profile/login"; // Belépés
    public static final String SIGN_UP = "profile/register"; // Regisztráció
    public static final String EXPLORE = "profile/explore"; // Explore menü
    public static final String DATE_LIST = "profile/dates"; // Randi lista
    public static final String FRIEND_LIST = "profile/friends"; // Barátok lista
    public static final String EVENT_JOIN = "profile/events_dojoin"; // Eseményhez csatlakozás
    public static final String EVENT_JOINED = "profile/events_joined"; // Csatlakozott eventek listázása
    public static final String EVENT_RATE = "profile/events_rate"; // Event értékelése
    public static final String GET_IMAGES = "profile/getimages"; // Profil és story képek letöltése
    public static final String INTEREST_TYPE_LIST = "profile/profile_interest"; // Érdeklődési típusok listája
    public static final String IMAGE_UPLOAD = "profile/imageupload"; // Kép feltöltése

    // ../dates
        public static final String DATE_ADD = "profile/dates/add"; // Randi hozzáadása
        public static final String DATE_DONT_LIKE = "profile/dates/dont_like"; // Randi don't like

    // ../friends
    public static final String FRIEND_ADD = "profile/friends/add"; // Barát hozzáadás

    // ../messages/newmessage
    public static final String MESSAGE_NEW = "profile/messages/newmessage"; // Üzenet küldése

    // api/pages
    public static final String CONDITIONS = "pages/aszf"; // ASZF


}
