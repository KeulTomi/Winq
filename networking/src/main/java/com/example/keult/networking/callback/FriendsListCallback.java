package com.example.keult.networking.callback;

import com.example.keult.networking.model.FriendsListResponse;

/**
 * Friends lista callback függvénye
 */

public interface FriendsListCallback extends BaseCallback {

    void forwardResponse(FriendsListResponse friendsListResponse);

}
