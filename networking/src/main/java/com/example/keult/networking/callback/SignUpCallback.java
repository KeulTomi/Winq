package com.example.keult.networking.callback;

import com.example.keult.networking.model.SignUpResponse;

/**
 * Regisztráció callback
 */

public interface SignUpCallback extends BaseCallback {

    void forwardResponse(SignUpResponse signUpResponse);

}
