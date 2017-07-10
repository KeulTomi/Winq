package com.example.keult.networking.model;

import java.util.List;

/**
 * User keresés adatmodell
 */

public class UserSearchResponseData {

    private List<ProfileData> users;
    private int users_all;

    public List<ProfileData> getUsers() {
        return users;
    }

    public int getUsersCount() {
        return users_all;
    }
}
