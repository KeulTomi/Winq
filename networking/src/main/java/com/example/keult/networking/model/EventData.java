package com.example.keult.networking.model;

/**
 *  Esemény adatok
 */

public class EventData {

    private String id;
    private String title;
    private String image;
    private String date;
    private String date_end;
    private String location;
    private String loc_x;
    private String loc_y;
    private String text;
    private String text_action;
    private String youtube_key;
    private String joined;
    private String kiemel;
    private String status;
    private String rates_num;
    private String rates_avg;
    private String added_user;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getDateEnd() {
        return date_end;
    }

    public String getLocation() {
        return location;
    }

    public String getLocX() {
        return loc_x;
    }

    public String getLocY() {
        return loc_y;
    }

    public String getText() {
        return text;
    }

    public String getTextAction() {
        return text_action;
    }

    public String getYoutubeKey() {
        return youtube_key;
    }

    public String getJoined() {
        return joined;
    }

    public String getKiemel() {
        return kiemel;
    }

    public String getStatus() {
        return status;
    }

    public String getRatesNum() {
        return rates_num;
    }

    public String getRatesAvg() {
        return rates_avg;
    }

    public String getAddedUser() {
        return added_user;
    }
}
