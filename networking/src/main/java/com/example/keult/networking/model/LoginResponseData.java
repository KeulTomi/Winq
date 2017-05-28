package com.example.keult.networking.model;

import java.util.List;

/**
 * Bejelentkezés válasz adatmodell
 */

public class LoginResponseData {

    private ProfileData profile;
    private List<EventsJoinedData> events_joined;
    private List<ProfileData> profile_dates;
    private List<ProfileData> profile_friends;

    public ProfileData getProfileData() {
        return profile;
    }

    public List<EventsJoinedData> getEventsJoinedList() {
        return events_joined;
    }

    public List<ProfileData> getDatesInLogin() {
        return profile_dates;
    }

    public List<ProfileData> getFriendsInLoginList() {
        return profile_friends;
    }
}
