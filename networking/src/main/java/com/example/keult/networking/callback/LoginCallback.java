package com.example.keult.networking.callback;

import com.example.keult.networking.model.LoginResponse;

/**
 *  Bejelentkezés válaszfüggvénye
 */

public interface LoginCallback extends BaseCallback {

    void forwardResponse(LoginResponse loginResponse);
}
