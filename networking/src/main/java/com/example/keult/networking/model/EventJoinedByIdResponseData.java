package com.example.keult.networking.model;

import java.util.List;

/**
 * Csatlakozott események adatstruktúra
 */

public class EventJoinedByIdResponseData {

    private List<EventsJoinedData> events_joined;

    public List<EventsJoinedData> getEventJoinedList() {
        return events_joined;
    }

}
