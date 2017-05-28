package com.example.keult.networking.model;

import java.util.List;

/**
 *  Események lista elemek adatmodell
 */

public class EventListResponseData {

    private List<EventData> events;
    private int events_all;

    public List<EventData> getEventList() {
        return events;
    }

    public int getEventsCount() {
        return events_all;
    }
}
