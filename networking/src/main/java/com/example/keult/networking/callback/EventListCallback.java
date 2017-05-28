package com.example.keult.networking.callback;

import com.example.keult.networking.model.EventListResponse;

/**
 *  Eseménylista válaszfüggvénye
 */

public interface EventListCallback extends BaseCallback {

    void forwardResponse(EventListResponse eventListResponse);

}
