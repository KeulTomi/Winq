package com.example.keult.networking.callback;

import com.example.keult.networking.model.EventJoinResponse;

/**
 * Eseményhez csatlakozás válaszfüggvénye
 */

public interface EventJoinCallback extends BaseCallback {

    void forwardResponse(EventJoinResponse eventJoinResponse);

}
