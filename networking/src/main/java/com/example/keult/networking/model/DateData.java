package com.example.keult.networking.model;

/**
 *  Randi adatok
 */

public class DateData extends ProfileData{

    private String dates_from;
    private String dates_to;
    private String dates_both;
    private String user_looking;
    private String user_behavior;
    private String user_activity;
    private String user_type;
    private String premium_active;
    private String premium_end_date;
    private String user_age;

    public String getDatesFrom() {
        return dates_from;
    }

    public String getDatesTo() {
        return dates_to;
    }

    public String getDatesBoth() {
        return dates_both;
    }

    public String getUserLooking() {
        return user_looking;
    }

    public String getUserBehavior() {
        return user_behavior;
    }

    public String getUserActivity() {
        return user_activity;
    }

    public String getUserType() {
        return user_type;
    }

    public String getPremiumActive() {
        return premium_active;
    }

    public String getPremiumEndDate() {
        return premium_end_date;
    }

    public String getUserAge() {
        return user_age;
    }
}
