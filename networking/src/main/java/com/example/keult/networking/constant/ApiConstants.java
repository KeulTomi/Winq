package com.example.keult.networking.constant;


import com.example.keult.networking.BuildConfig;

/**
 *  API elérési utak konstansai
 */

public class ApiConstants {

    public static final String BASE_URL = BuildConfig.BASE_URL;

    // api
    public static final String EVENT_LIST = "events"; // Események listázása
    public static final String EVENT_LIST_BY_ID = "events_joined_by_userid"; // Csatlakozott események UserId alapján
    public static final String EVENT_SEARCH = "search_events"; // Események keresése
    public static final String GENERAL_SEARCH = "search_eventsandusers"; // Általános keresés

    // api/profile
    public static final String LOG_IN = "profile/login"; // Belépés
    public static final String SIGN_UP = "profile/register"; // Regisztráció
    public static final String EXPLORE = "profile/explore"; // Explore menü
    public static final String DATE_LIST = "profile/dates"; // Randi lista
    public static final String EVENT_JOIN = "profile/events_dojoin"; // Eseményhez csatlakozás
    public static final String GET_IMAGES = "profile/getimages"; // Profil és story képek letöltése
        // api/profile/dates
        public static final String DATE_ADD = "profile/dates/add"; // Randi hozzáadása
        public static final String DATE_DONT_LIKE = "profile/dates/dont_like"; // Randi don't like
    // api/pages
    public static final String CONDITIONS = "pages/aszf"; // ASZF


}
