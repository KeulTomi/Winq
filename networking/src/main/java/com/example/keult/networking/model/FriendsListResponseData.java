package com.example.keult.networking.model;

import java.util.List;

/**
 * Friend list válasz adatmodell
 */

public class FriendsListResponseData {

    private List<FriendsData> profile_friends;

    public List<FriendsData> getFriendsList() {
        return profile_friends;
    }
}
