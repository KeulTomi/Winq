package com.example.keult.networking.callback;

import com.example.keult.networking.model.ExploreResponse;

/**
 * Explore menü válaszfüggvénye
 */

public interface ExploreCallback extends BaseCallback {

    void forwardResponse(ExploreResponse exploreResponse);

}
