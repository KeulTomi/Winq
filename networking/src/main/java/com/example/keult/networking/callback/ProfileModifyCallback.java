package com.example.keult.networking.callback;

import com.example.keult.networking.model.ProfileModifyResponse;

/**
 * Profil módosítás callback függvénye
 */

public interface ProfileModifyCallback extends BaseCallback {

    void forwardResponse(ProfileModifyResponse profileModifyResponse);

}
