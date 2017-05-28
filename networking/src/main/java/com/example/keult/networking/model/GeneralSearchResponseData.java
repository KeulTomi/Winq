package com.example.keult.networking.model;

import java.util.List;

/**
 *  Általános keresési eredmény adatmodell
 */

public class GeneralSearchResponseData {

    private List<EventData> events;
    private int events_all;
    private List<ProfileData> users;
    private int users_all;

    public List<EventData> getEventList() {
        return events;
    }

    public int getEventsCount() {
        return events_all;
    }

    public List<ProfileData> getUserList() {
        return users;
    }

    public int getUsersCount() {
        return users_all;
    }
}
