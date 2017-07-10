package com.example.keult.networking.model;

import java.io.Serializable;

/**
 * Felhasználói profil adatok
 */

public class ProfileData implements Serializable {

    private String id;
    private String username;
    private String password;
    private String facebookid;
    private String email;
    private String fullname;
    private String fiulany;
    private String image;
    private String regip;
    private String regdate;
    private String status;
    private String lastlogin;
    private String mobileid;
    private String loc_x;
    private String loc_y;
    private String user_born;
    private String user_country_short;
    private String user_interest;
    private String user_description;
    private String user_looking;
    private String user_behavior;
    private String user_activity;
    private String user_type;
    private String premium_active;
    private String premium_end_date;
    private String user_age;

    private String user_interest_text;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFacebookid() {
        return facebookid;
    }

    public void setFacebookId(String facebookId) {
        this.facebookid = facebookId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullname;
    }

    public String getSex() {
        return fiulany;
    }

    public String getImage() {
        return image;
    }

    public String getRegip() {
        return regip;
    }

    public String getRegdate() {
        return regdate;
    }

    public String getStatus() {
        return status;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public String getMobileid() {
        return mobileid;
    }

    public String getLocX() {
        return loc_x;
    }

    public String getLocY() {
        return loc_y;
    }

    public String getUserborn() {
        return user_born;
    }

    public String getUserCountryShort() {
        return user_country_short;
    }

    public String getUserInterest() {
        return user_interest;
    }

    public String getUserDescription() {
        return user_description;
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

    public String getPremiumEnddate() {
        return premium_end_date;
    }

    public String getUserInterestText() {
        return user_interest_text;
    }

}
