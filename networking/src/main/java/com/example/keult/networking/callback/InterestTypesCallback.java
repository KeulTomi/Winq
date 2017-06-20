package com.example.keult.networking.callback;

import com.example.keult.networking.model.InterestTypesResponse;

/**
 * Interest típus lista callback függvénye
 */

public interface InterestTypesCallback extends BaseCallback {

    void forwardResponse(InterestTypesResponse interestTypesResponse);

}
