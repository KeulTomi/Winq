package com.example.keult.networking.error;

/**
 *  Hálózati hiba  adatmodell
 */

public class NetworkError {

    private Throwable throwable;
    private Integer httpStatusCode;

    public NetworkError(Throwable throwable, Integer httpStatusCode) {
        this.throwable = throwable;
        this.httpStatusCode = httpStatusCode;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}
