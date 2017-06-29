package com.example.keult.networking.callback;

import com.example.keult.networking.model.NewMessageResponse;

/**
 * Új üzenet küldése másik felhasználónak callback függvénye
 */

public interface NewMessageCallback extends BaseCallback {

    void forwardResponse(NewMessageResponse newMessageResponse);

}
