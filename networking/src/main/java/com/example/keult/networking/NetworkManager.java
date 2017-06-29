package com.example.keult.networking;

import com.example.keult.networking.callback.ConditionsCallback;
import com.example.keult.networking.callback.DateAddCallback;
import com.example.keult.networking.callback.DateDoNotLikeCallback;
import com.example.keult.networking.callback.DateListCallback;
import com.example.keult.networking.callback.EventJoinCallback;
import com.example.keult.networking.callback.EventJoinedByIdCallback;
import com.example.keult.networking.callback.EventListCallback;
import com.example.keult.networking.callback.EventRateCallback;
import com.example.keult.networking.callback.EventsJoinedCallback;
import com.example.keult.networking.callback.EventsSearchCallback;
import com.example.keult.networking.callback.ExploreCallback;
import com.example.keult.networking.callback.FriendsAddCallback;
import com.example.keult.networking.callback.FriendsListCallback;
import com.example.keult.networking.callback.GeneralSearchCallback;
import com.example.keult.networking.callback.ImageUploadCallback;
import com.example.keult.networking.callback.InterestTypesCallback;
import com.example.keult.networking.callback.LoginCallback;
import com.example.keult.networking.callback.NewMessageCallback;
import com.example.keult.networking.callback.ProfileImagesCallback;
import com.example.keult.networking.callback.SignUpCallback;
import com.example.keult.networking.di.component.DaggerNetworkComponent;
import com.example.keult.networking.error.factory.ErrorFactory;
import com.example.keult.networking.service.ApiService;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;

/**
 *  Api hívásokat kezelő osztály, ahol a metódusok elérhetők
 */

public class NetworkManager {

    private static NetworkManager instance;
    private ApiService apiService;

    private NetworkManager() {
        this.apiService = DaggerNetworkComponent
                .create()
                .provideApiService();
    }

    public static synchronized NetworkManager getInstance() {
        if (instance == null) {
            instance =  new NetworkManager();
        }
        return instance;
    }

    // Belépés
    public void login(Map<String, Object> body, LoginCallback loginCallback) {
        apiService.login(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loginCallback::forwardResponse,
                        throwable -> loginCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Dates - add
    public void requestForDate(Map<String, Object> body, DateAddCallback dateAddCallback) {
        apiService.requestForDate(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        dateAddCallback::forwardResponse,
                        throwable -> dateAddCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Dates - don't like
    public void dontLikeDate(Map<String, Object> body, DateDoNotLikeCallback dateDoNotLikeCallback) {
        apiService.dontLikeDate(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        dateDoNotLikeCallback::forwardResponse,
                        throwable -> dateDoNotLikeCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Dates - list
    public void listDates(Map<String, Object> body, DateListCallback dateListCallback) {
        apiService.listDates(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        dateListCallback::forwardResponse,
                        throwable -> dateListCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }


    // Events - Csatlakozott
    public void listJoinedEvents(Map<String, Object> body, EventsJoinedCallback eventsJoinedCallback) {
        apiService.listJoinedEvents(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        eventsJoinedCallback::forwardResponse,
                        throwable -> eventsJoinedCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Events - Join
    public void joinToEvent(Map<String, Object> body, EventJoinCallback eventJoinCallback) {
        apiService.joinToEvent(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        eventJoinCallback::forwardResponse,
                        throwable -> eventJoinCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Events - joined by id
    public void listJoinedEventsById(Map<String, Object> body, EventJoinedByIdCallback eventJoinedByIdCallback) {
        apiService.listJoinedEventsById(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        eventJoinedByIdCallback::forwardResponse,
                        throwable -> eventJoinedByIdCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Events - list
    public void listEvents(Map<String, Object> body, EventListCallback eventListCallback) {
        apiService.listEvents(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        eventListCallback::forwardResponse,
                        throwable -> eventListCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Events - rate
    public void rateEvent(Map<String, Object> body, EventRateCallback eventRateCallback) {
        apiService.rateEvent(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        eventRateCallback::forwardResponse,
                        throwable -> eventRateCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Explore - Lista
    public void exploreUsers(Map<String, Object> body, ExploreCallback exploreCallback) {
        apiService.exploreUsers(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        exploreCallback::forwardResponse,
                        throwable -> exploreCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Friends - add
    public void addAsFriend(Map<String, Object> body, FriendsAddCallback friendsAddCallback) {
        apiService.addAsFriend(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        friendsAddCallback::forwardResponse,
                        throwable -> friendsAddCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Friends - list
    public void listFriends(Map<String, Object> body, FriendsListCallback friendsListCallback) {
        apiService.listFriends(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        friendsListCallback::forwardResponse,
                        throwable -> friendsListCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    /// Interest types - list
    public void listInterestTypes(Map<String, Object> body, InterestTypesCallback interestTypesCallback) {
        apiService.listInterestTypes(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        interestTypesCallback::forwardResponse,
                        throwable -> interestTypesCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Keresés - Általános
    public void searchGeneral(Map<String, Object> body, GeneralSearchCallback generalSearchCallback) {
        apiService.searchGeneral(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        generalSearchCallback::forwardResponse,
                        throwable -> generalSearchCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Keresés - Events
    public void searchEvents(Map<String, Object> body, EventsSearchCallback eventsSearchCallback) {
        apiService.searchEvents(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        eventsSearchCallback::forwardResponse,
                        throwable -> eventsSearchCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Keresés - Users
    // TODO: http://dev.balaz98.hu/winq/api/search_users meghívására 404 hibát (nem található) kapok


    // Profil - képek
    public void getProfileImages(Map<String, Object> body, ProfileImagesCallback profileImagesCallback) {
        apiService.getProfileImages(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        profileImagesCallback::forwardResponse,
                        throwable -> profileImagesCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Profil - képfeltöltés
    public void uploadImage(Map<String, RequestBody> body, MultipartBody.Part filePart, ImageUploadCallback imageUploadCallback) {
        apiService.uploadImage(body, filePart)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        imageUploadCallback::forwardResponse,
                        throwable -> imageUploadCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // Regisztráció
    public void signup(Map<String, Object> body, SignUpCallback signUpCallback) {
        apiService.signup(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        signUpCallback::forwardResponse,
                        throwable -> signUpCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // z - ÁSZF
    public void getASZF(Map<String, Object> body, ConditionsCallback conditionsCallback) {
        apiService.getASZF(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        conditionsCallback::forwardResponse,
                        throwable -> conditionsCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    // message - új üzenet
    public void newMessage(Map<String, Object> body, NewMessageCallback newMessageCallback) {
        apiService.newMessage(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newMessageCallback::forwardResponse,
                        throwable -> newMessageCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }
}
