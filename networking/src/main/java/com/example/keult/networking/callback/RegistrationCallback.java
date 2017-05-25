package com.example.keult.networking.callback;

import com.example.keult.networking.model.RegistrationResponse;

/**
 * Created by KeulT on 2017.05.26..
 */

public interface RegistrationCallback extends BaseCallback {

    void forwardResponse(RegistrationResponse registrationResponse);

}
