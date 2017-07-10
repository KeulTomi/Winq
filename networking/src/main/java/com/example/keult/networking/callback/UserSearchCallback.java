package com.example.keult.networking.callback;

import com.example.keult.networking.model.UserSearchResponse;

/**
 * User keresés callback függvények
 */

public interface UserSearchCallback extends BaseCallback {

    void forwardResponse(UserSearchResponse userSearchResponse);

}
