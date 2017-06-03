package com.example.keult.networking.callback;

import com.example.keult.networking.model.ProfileImagesResponse;

/**
 * Profilképek letöltésének callback függvénye
 */

public interface ProfileImagesCallback extends BaseCallback {

    void forwardResponse(ProfileImagesResponse profileImagesResponse);
}

