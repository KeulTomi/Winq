package com.example.keult.networking.model;

import java.util.List;

/**
 * Bejelentkezés válasz adatmodell
 */

public class LoginResponseData {

    private ProfileData profile;
    private List<EventsJoined> events_joined;
    private List<DatesInLogin> profile_dates;
    private List<FriendsInLogin> profile_friends;

    public ProfileData getProfileData() {
        return profile;
    }

    public List<EventsJoined> getEventsJoinedList() {
        return events_joined;
    }

    public List<DatesInLogin> getDatesInLogin() {
        return profile_dates;
    }

    public List<FriendsInLogin> getFriendsInLoginList() {
        return profile_friends;
    }
}
