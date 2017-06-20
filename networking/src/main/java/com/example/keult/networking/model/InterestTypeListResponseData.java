package com.example.keult.networking.model;

import java.util.List;

/**
 * Interest típusok listájának adat modellje
 */

public class InterestTypeListResponseData {

    private List<InterestData> interests;

    public List<InterestData> getInterestList() {
        return interests;
    }
}
