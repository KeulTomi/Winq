package com.example.keult.networking.callback;

import com.example.keult.networking.model.EventListResponse;

/**
 *  Esemény keresés válaszfüggvény
 */

public interface EventsSearchCallback extends BaseCallback {

    void forwardResponse(EventListResponse eventListResponse);

}
