package com.example.keult.networking.model;

/**
 *  Randi adatok
 */

public class DateData extends ProfileData{

    private String dates_from;
    private String dates_to;
    private String dates_both;
    private String messages_cansend;
    private String messages;
    private String messages_receive;

    public String getDatesFrom() {
        return dates_from;
    }

    public String getDatesTo() {
        return dates_to;
    }

    public String getDatesBoth() {
        return dates_both;
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
}
