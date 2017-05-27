package com.example.keult.networking.service;

import java.util.Map;

import com.example.keult.networking.model.ConditionsResponse;
import com.example.keult.networking.model.DateAddResponse;
import com.example.keult.networking.model.DateDoNotLikeResponse;
import com.example.keult.networking.model.DateListResponse;
import com.example.keult.networking.model.LoginResponse;
import com.example.keult.networking.model.SignUpResponse;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.example.keult.networking.constant.ApiConstants.CONDITIONS;
import static com.example.keult.networking.constant.ApiConstants.DATE_ADD;
import static com.example.keult.networking.constant.ApiConstants.DATE_DONT_LIKE;
import static com.example.keult.networking.constant.ApiConstants.DATE_LIST;
import static com.example.keult.networking.constant.ApiConstants.LOG_IN;
import static com.example.keult.networking.constant.ApiConstants.SIGN_UP;

/**
 *
 */

public interface ApiService {

    @FormUrlEncoded
    @POST(LOG_IN)
    Observable<LoginResponse> login(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(SIGN_UP)
    Observable<SignUpResponse> signup(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(CONDITIONS)
    Observable<ConditionsResponse> getASZF(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(DATE_ADD)
    Observable<DateAddResponse> addDate(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(DATE_DONT_LIKE)
    Observable<DateDoNotLikeResponse> dontLikeDate(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(DATE_LIST)
    Observable<DateListResponse> getDates(@FieldMap Map<String, Object> body);

}
