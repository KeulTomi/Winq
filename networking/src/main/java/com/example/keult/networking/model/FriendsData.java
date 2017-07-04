package com.example.keult.networking.model;

/**
 * Friends adatok modellje
 */

public class FriendsData extends ProfileData {

    private String friends_from;
    private String friends_to;
    private String friends_both;
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

    public String getFriendsFrom() {
        return friends_from;
    }

    public String getFriendsTo() {
        return friends_to;
    }

    public String getFriendsBoth() {
        return friends_both;
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
