package com.example.keult.networking.callback;

import com.example.keult.networking.model.EventRateResponse;

/**
 * Event értékelés válasz függvénye
 */

public interface EventRateCallback extends BaseCallback {

    void forwardResponse(EventRateResponse eventRateResponse);

}
