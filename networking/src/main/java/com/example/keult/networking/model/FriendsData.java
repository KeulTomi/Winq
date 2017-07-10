package com.example.keult.networking.model;

/**
 * Friends adatok modellje
 */

public class FriendsData extends ProfileData {

    private String friends_from;
    private String friends_to;
    private String friends_both;
    private String messages_cansend;
    private String messages;
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

    public String getMessagesCansend() {
        return messages_cansend;
    }

    public String getMessages() {
        return messages;
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
