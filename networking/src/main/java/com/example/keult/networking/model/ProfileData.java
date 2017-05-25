package com.example.keult.networking.model;

/**
 * Felhasználói profil adatok
 */

public class ProfileData {

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

    public String getUserId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFacebookid() {
        return facebookid;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getFiuLany() {
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
}