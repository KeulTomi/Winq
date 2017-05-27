package com.example.keult.networking.callback;

import com.example.keult.networking.model.DateAddResponse;

/**
 *  Randi hozzáadás válaszfüggvénye
 */

public interface DateAddCallback extends BaseCallback {

    void forwardResponse(DateAddResponse dateAddResponse);

}
