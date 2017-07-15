package com.example.keult.networking.callback;

import com.example.keult.networking.model.PositionSendResponse;

/**
 * Pozíció küldését végző callback fügvény
 */

public interface PositionSendCallback extends BaseCallback {

    void forwardResponse(PositionSendResponse positionSendResponse);

}
