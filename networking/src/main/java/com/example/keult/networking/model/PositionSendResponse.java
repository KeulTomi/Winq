package com.example.keult.networking.model;

/**
 * Pozíció küldés válasz modell
 */

public class PositionSendResponse extends BaseResponse {

    private String[] data;

    public String[] getData() {
        return data;
    }
}
