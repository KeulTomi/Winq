package com.example.keult.networking.model;

import java.util.List;

/**
 *  Válaszok alap-struktúrája
 */

public class BaseResponse {

    protected int status; // Státusz visszajelzés
    protected int success; // Sikeres válasz esetén 1-et ad
    private Double version; // API verziószáma
    protected List<String> error; // Hibák visszajelzése

    public int getStatus() {
        return status;
    }

    public int getSuccess() {
        return success;
    }

    public List<String> getError() {
        return error;
    }

    public Double getVersion() {
        return version;
    }
}
