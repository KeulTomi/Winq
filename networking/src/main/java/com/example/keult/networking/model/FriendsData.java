package com.example.keult.networking.model;

/**
 * Friends adatok modellje
 */

public class FriendsData {

    private String id;
    private String friends_from;
    private String friends_to;
    private String friends_both;
    private String username;
    private String password;
    private String facebookid;
    private String email;
    private String fullname;
    //private String fiulany;
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
    //private String user_interest_text;
    private String user_looking;
    private String user_behavior;
    private String user_activity;
    private String user_type;
    private String premium_active;
    private String premium_end_date;
    private String user_age;
    private String messages_cansend;
    private String messages_receive;
    private String messages_fromuser;
    private String messages_date;
    private String messages_fromuser_name;

    public String getId() {
        return id;
    }

    public String getFriendsFrom() {
        return friends_from;
    }

    public String getFriendsTo() {
        return friends_to;
    }

    public String getFriendsBoth() {
        return friends_both;
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

    public String getUserBorn() {
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

    public void setUserActivity(String user_activity) {
        this.user_activity = user_activity;
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

    public String getUserAge() {
        return user_age;
    }

    public String getMessagesCansend() {
        return messages_cansend;
    }

    public String getMessagesReceive() {
        return messages_receive;
    }

    public String getMessagesFromuser() {
        return messages_fromuser;
    }

    public String getMessagesDate() {
        return messages_date;
    }

    public String getMessagesFromuserName() {
        return messages_fromuser_name;
    }
}
