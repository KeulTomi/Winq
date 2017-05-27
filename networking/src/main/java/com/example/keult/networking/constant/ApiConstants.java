package com.example.keult.networking.constant;


import com.example.keult.networking.BuildConfig;

/**
 *  API elérési utak konstansai
 */

public class ApiConstants {

    public static final String BASE_URL = BuildConfig.BASE_URL;

    // api -- Profile
    public static final String LOG_IN = "profile/login"; // Belépés
    public static final String SIGN_UP = "profile/register"; // Regisztráció
        // api - Profile/Dates
        public static final String DATE_ADD = "profile/dates/add"; // Randi hozzáadása
        public static final String DATE_DONT_LIKE = "profile/dates/dont_like"; // Randi don't like
        public static final String DATE_LIST = "profile/dates"; // Randi lista

    // api -- Pages
    public static final String CONDITIONS = "pages/aszf"; // ASZF


}
