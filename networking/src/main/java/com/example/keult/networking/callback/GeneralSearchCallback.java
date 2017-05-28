package com.example.keult.networking.callback;

import com.example.keult.networking.model.GeneralSearchResponse;

/**
 *  Általános keresés válaszfüggvénye
 */

public interface GeneralSearchCallback extends BaseCallback {

    void forwardResponse(GeneralSearchResponse eventListResponse);

}
