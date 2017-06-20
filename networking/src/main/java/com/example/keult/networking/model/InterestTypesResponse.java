package com.example.keult.networking.model;

/**
 * Interest típusok listájának válasz modellje
 */

public class InterestTypesResponse extends BaseResponse {

    private InterestTypeListResponseData data;

    public InterestTypeListResponseData getData() {
        return data;
    }

}
