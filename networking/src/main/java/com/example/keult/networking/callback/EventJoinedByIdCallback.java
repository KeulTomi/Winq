package com.example.keult.networking.callback;

import com.example.keult.networking.model.EventJoinedByIdResponse;

/**
 * Csatlakozott események lekérdezése felhasználó Id alapján válaszfüggvénye
 */

public interface EventJoinedByIdCallback extends BaseCallback {

    void forwardResponse(EventJoinedByIdResponse eventJoinedByIdResponse);

}
