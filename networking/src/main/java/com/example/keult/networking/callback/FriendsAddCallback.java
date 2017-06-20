package com.example.keult.networking.callback;

import com.example.keult.networking.model.FriendAddResponse;

/**
 * Friends Add callback függvénye
 */

public interface FriendsAddCallback extends BaseCallback {

    void forwardResponse(FriendAddResponse friendAddResponse);

}
