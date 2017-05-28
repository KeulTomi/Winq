package com.example.keult.networking.model;

/**
 * Csatlakozott események felhasználó Id alapján lekérdezés válasz adatmodell
 */

public class EventJoinedByIdResponse extends BaseResponse {

    private EventJoinedByIdResponseData data;

    public EventJoinedByIdResponseData getData() {
        return data;
    }

}
