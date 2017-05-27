package com.example.keult.networking.callback;

import com.example.keult.networking.model.ConditionsResponse;

/**
 * ÁSZF válaszfüggvénye
 */

public interface ConditionsCallback extends BaseCallback {

    void forwardResponse(ConditionsResponse conditionsResponse);
}
