package com.example.keult.networking.callback;

import com.example.keult.networking.model.ImageUploadResponse;

/**
 * Képfeltöltés callback függvény
 */

public interface ImageUploadCallback extends BaseCallback {

    void forwardResponse(ImageUploadResponse imageUploadResponse);

}
