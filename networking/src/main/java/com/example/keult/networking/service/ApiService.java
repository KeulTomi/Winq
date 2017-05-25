package com.example.keult.networking.service;

import java.util.Map;

import com.example.keult.networking.model.LoginResponse;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

import static com.example.keult.networking.constant.ApiConstants.LOG_IN;

/**
 *
 */

public interface ApiService {

    @FormUrlEncoded
    @POST(LOG_IN)
    Observable<LoginResponse> login(@FieldMap Map<String, Object> body);
}
