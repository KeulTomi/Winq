package com.example.keult.networking.callback;

import com.example.keult.networking.model.DateDoNotLikeResponse;

/**
 *  Randi Don't like válaszfüggvénye
 */

public interface DateDoNotLikeCallback extends BaseCallback{

    void forwardResponse(DateDoNotLikeResponse dateDoNotLikeResponse);
}
