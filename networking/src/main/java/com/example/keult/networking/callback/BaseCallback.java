package com.example.keult.networking.callback;

import com.example.keult.networking.error.NetworkError;

/**
 * Created by demdani on 2016. 10. 18..
 */

public interface BaseCallback {

    void forwardError(NetworkError networkError);
}
