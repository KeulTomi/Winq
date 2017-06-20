package com.example.keult.networking.model;

import java.util.List;

/**
 * Friend list válasz adatmodell
 */

class FriendsListResponseData {

    private List<FriendsData> profile_friends;

    public List<FriendsData> getFriendsList() {
        return profile_friends;
    }
}
