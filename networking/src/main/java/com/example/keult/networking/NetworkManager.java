package com.example.keult.networking;

import com.example.keult.networking.callback.ConditionsCallback;
import com.example.keult.networking.callback.DateAddCallback;
import com.example.keult.networking.callback.DateDoNotLikeCallback;
import com.example.keult.networking.callback.DateListCallback;
import com.example.keult.networking.callback.EventListCallback;
import com.example.keult.networking.callback.EventsSearchCallback;
import com.example.keult.networking.callback.GeneralSearchCallback;
import com.example.keult.networking.callback.LoginCallback;
import com.example.keult.networking.callback.SignUpCallback;
import com.example.keult.networking.di.component.DaggerNetworkComponent;
import com.example.keult.networking.error.factory.ErrorFactory;
import com.example.keult.networking.service.ApiService;

import java.util.Map;

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

    public void login(Map<String, Object> body, LoginCallback loginCallback) {
        apiService.login(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loginCallback::forwardResponse,
                        throwable -> loginCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    public void signup(Map<String, Object> body, SignUpCallback signUpCallback) {
        apiService.signup(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        signUpCallback::forwardResponse,
                        throwable -> signUpCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    public void getASZF(Map<String, Object> body, ConditionsCallback conditionsCallback) {
        apiService.getASZF(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        conditionsCallback::forwardResponse,
                        throwable -> conditionsCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    public void requestForDate(Map<String, Object> body, DateAddCallback dateAddCallback) {
        apiService.requestForDate(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        dateAddCallback::forwardResponse,
                        throwable -> dateAddCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    public void dontLikeDate(Map<String, Object> body, DateDoNotLikeCallback dateDoNotLikeCallback) {
        apiService.dontLikeDate(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        dateDoNotLikeCallback::forwardResponse,
                        throwable -> dateDoNotLikeCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    public void listDates(Map<String, Object> body, DateListCallback dateListCallback) {
        apiService.listDates(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        dateListCallback::forwardResponse,
                        throwable -> dateListCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    public void listEvents(Map<String, Object> body, EventListCallback eventListCallback) {
        apiService.listEvents(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        eventListCallback::forwardResponse,
                        throwable -> eventListCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    public void searchEvents(Map<String, Object> body, EventsSearchCallback eventsSearchCallback) {
        apiService.searchEvents(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        eventsSearchCallback::forwardResponse,
                        throwable -> eventsSearchCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }

    public void searchGeneral(Map<String, Object> body, GeneralSearchCallback generalSearchCallback) {
        apiService.searchGeneral(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        generalSearchCallback::forwardResponse,
                        throwable -> generalSearchCallback.forwardError(ErrorFactory.createNetworkError(throwable))
                );
    }
}
