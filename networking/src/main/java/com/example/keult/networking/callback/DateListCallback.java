package com.example.keult.networking.callback;

import com.example.keult.networking.model.DateListResponse;

/**
 *  Randik listájának válaszfüggvénye
 */

public interface DateListCallback extends BaseCallback {

    void forwardResponse(DateListResponse dateListResponse);
}
