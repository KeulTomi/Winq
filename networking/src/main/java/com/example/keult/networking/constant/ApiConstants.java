package com.example.keult.networking.constant;


import com.example.keult.networking.BuildConfig;

/**
 *  API elérési utak konstansai
 */

public class ApiConstants {

    public static final String BASE_URL = BuildConfig.BASE_URL;

    // api
    public static final String EVENT_LIST = "events"; // Események
    public static final String EVENT_SEARCH = "search_events"; // Események keresése
    public static final String GENERAL_SEARCH = "search_eventsandusers"; // Általános keresés

    // api/profile
    public static final String LOG_IN = "profile/login"; // Belépés
    public static final String SIGN_UP = "profile/register"; // Regisztráció
        // api/profile/dates
        public static final String DATE_ADD = "profile/dates/add"; // Randi hozzáadása
        public static final String DATE_DONT_LIKE = "profile/dates/dont_like"; // Randi don't like
        public static final String DATE_LIST = "profile/dates"; // Randi lista

    // api/pages
    public static final String CONDITIONS = "pages/aszf"; // ASZF


}
