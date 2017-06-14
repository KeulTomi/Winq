package com.example.keult.networking.callback;

import com.example.keult.networking.model.EventsJoinedResponse;

/**
 * Created by tomi on 2017.06.13..
 */

public interface EventsJoinedCallback extends BaseCallback {

    void forwardResponse(EventsJoinedResponse eventsJoinedResponse);

}
