package com.example.keult.networking.service;

import com.example.keult.networking.model.ConditionsResponse;
import com.example.keult.networking.model.DateAddResponse;
import com.example.keult.networking.model.DateDoNotLikeResponse;
import com.example.keult.networking.model.DateListResponse;
import com.example.keult.networking.model.EventJoinResponse;
import com.example.keult.networking.model.EventJoinedByIdResponse;
import com.example.keult.networking.model.EventListResponse;
import com.example.keult.networking.model.ExploreResponse;
import com.example.keult.networking.model.GeneralSearchResponse;
import com.example.keult.networking.model.LoginResponse;
import com.example.keult.networking.model.ProfileImagesResponse;
import com.example.keult.networking.model.SignUpResponse;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

import static com.example.keult.networking.constant.ApiConstants.CONDITIONS;
import static com.example.keult.networking.constant.ApiConstants.DATE_ADD;
import static com.example.keult.networking.constant.ApiConstants.DATE_DONT_LIKE;
import static com.example.keult.networking.constant.ApiConstants.DATE_LIST;
import static com.example.keult.networking.constant.ApiConstants.EVENT_JOIN;
import static com.example.keult.networking.constant.ApiConstants.EVENT_LIST;
import static com.example.keult.networking.constant.ApiConstants.EVENT_LIST_BY_ID;
import static com.example.keult.networking.constant.ApiConstants.EVENT_SEARCH;
import static com.example.keult.networking.constant.ApiConstants.EXPLORE;
import static com.example.keult.networking.constant.ApiConstants.GENERAL_SEARCH;
import static com.example.keult.networking.constant.ApiConstants.GET_IMAGES;
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
    Observable<DateAddResponse> requestForDate(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(DATE_DONT_LIKE)
    Observable<DateDoNotLikeResponse> dontLikeDate(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(DATE_LIST)
    Observable<DateListResponse> listDates(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(EVENT_LIST)
    Observable<EventListResponse> listEvents(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(EVENT_LIST_BY_ID)
    Observable<EventJoinedByIdResponse> listEventsById(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(EVENT_SEARCH)
    Observable<EventListResponse> searchEvents(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(GENERAL_SEARCH)
    Observable<GeneralSearchResponse> searchGeneral(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(EXPLORE)
    Observable<ExploreResponse> exploreUsers(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(EVENT_JOIN)
    Observable<EventJoinResponse> joinToEvent(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(GET_IMAGES)
    Observable<ProfileImagesResponse> getProfileImages(@FieldMap Map<String, Object> body);
}
