package com.example.keult.networking.service;

import com.example.keult.networking.model.ConditionsResponse;
import com.example.keult.networking.model.DateAddResponse;
import com.example.keult.networking.model.DateDoNotLikeResponse;
import com.example.keult.networking.model.DateListResponse;
import com.example.keult.networking.model.EventJoinResponse;
import com.example.keult.networking.model.EventJoinedByIdResponse;
import com.example.keult.networking.model.EventListResponse;
import com.example.keult.networking.model.EventRateResponse;
import com.example.keult.networking.model.EventsJoinedResponse;
import com.example.keult.networking.model.ExploreResponse;
import com.example.keult.networking.model.FriendAddResponse;
import com.example.keult.networking.model.FriendsListResponse;
import com.example.keult.networking.model.GeneralSearchResponse;
import com.example.keult.networking.model.ImageUploadResponse;
import com.example.keult.networking.model.InterestTypesResponse;
import com.example.keult.networking.model.LoginResponse;
import com.example.keult.networking.model.NewMessageResponse;
import com.example.keult.networking.model.PositionSendResponse;
import com.example.keult.networking.model.ProfileImagesResponse;
import com.example.keult.networking.model.ProfileModifyResponse;
import com.example.keult.networking.model.SignUpResponse;
import com.example.keult.networking.model.UserSearchResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

import static com.example.keult.networking.constant.ApiConstants.CONDITIONS;
import static com.example.keult.networking.constant.ApiConstants.DATE_ADD;
import static com.example.keult.networking.constant.ApiConstants.DATE_DONT_LIKE;
import static com.example.keult.networking.constant.ApiConstants.DATE_LIST;
import static com.example.keult.networking.constant.ApiConstants.EVENT_JOIN;
import static com.example.keult.networking.constant.ApiConstants.EVENT_JOINED;
import static com.example.keult.networking.constant.ApiConstants.EVENT_LIST;
import static com.example.keult.networking.constant.ApiConstants.EVENT_RATE;
import static com.example.keult.networking.constant.ApiConstants.EVENT_SEARCH;
import static com.example.keult.networking.constant.ApiConstants.EXPLORE;
import static com.example.keult.networking.constant.ApiConstants.FRIEND_ADD;
import static com.example.keult.networking.constant.ApiConstants.FRIEND_LIST;
import static com.example.keult.networking.constant.ApiConstants.GENERAL_SEARCH;
import static com.example.keult.networking.constant.ApiConstants.GET_IMAGES;
import static com.example.keult.networking.constant.ApiConstants.IMAGE_UPLOAD;
import static com.example.keult.networking.constant.ApiConstants.INTEREST_TYPE_LIST;
import static com.example.keult.networking.constant.ApiConstants.JOINED_EVENT_LIST_BY_ID;
import static com.example.keult.networking.constant.ApiConstants.LOG_IN;
import static com.example.keult.networking.constant.ApiConstants.MESSAGE_NEW;
import static com.example.keult.networking.constant.ApiConstants.POSITION_SEND;
import static com.example.keult.networking.constant.ApiConstants.PROFILE_MODIFY;
import static com.example.keult.networking.constant.ApiConstants.SIGN_UP;
import static com.example.keult.networking.constant.ApiConstants.USER_SEARCH;

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
    @POST(JOINED_EVENT_LIST_BY_ID)
    Observable<EventJoinedByIdResponse> listJoinedEventsById(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(EVENT_SEARCH)
    Observable<EventListResponse> searchEvents(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(USER_SEARCH)
    Observable<UserSearchResponse> searchUsers(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(GENERAL_SEARCH)
    Observable<GeneralSearchResponse> searchGeneral(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(EXPLORE)
    Observable<ExploreResponse> exploreUsers(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(FRIEND_LIST)
    Observable<FriendsListResponse> listFriends(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(EVENT_JOIN)
    Observable<EventJoinResponse> joinToEvent(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(EVENT_JOINED)
    Observable<EventsJoinedResponse> listJoinedEvents(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(GET_IMAGES)
    Observable<ProfileImagesResponse> getProfileImages(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(FRIEND_ADD)
    Observable<FriendAddResponse> addAsFriend(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(INTEREST_TYPE_LIST)
    Observable<InterestTypesResponse> listInterestTypes(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(EVENT_RATE)
    Observable<EventRateResponse> rateEvent(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(MESSAGE_NEW)
    Observable<NewMessageResponse> newMessage(@FieldMap Map<String, Object> body);

    @Multipart
    @POST(IMAGE_UPLOAD)
    Observable<ImageUploadResponse> uploadImage(@PartMap Map<String, RequestBody> body, @Part MultipartBody.Part filePart);

    @FormUrlEncoded
    @POST(POSITION_SEND)
    Observable<PositionSendResponse> sendPosition(@FieldMap Map<String, Object> body);

    @FormUrlEncoded
    @POST(PROFILE_MODIFY)
    Observable<ProfileModifyResponse> modifyProfile(@FieldMap Map<String, Object> body);
}
