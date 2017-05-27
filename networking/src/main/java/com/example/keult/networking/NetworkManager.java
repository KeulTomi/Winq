package com.example.keult.networking;

import java.util.Map;

import com.example.keult.networking.callback.ConditionsCallback;
import com.example.keult.networking.callback.DateAddCallback;
import com.example.keult.networking.callback.LoginCallback;
import com.example.keult.networking.callback.SignUpCallback;
import com.example.keult.networking.di.component.DaggerNetworkComponent;
import com.example.keult.networking.error.factory.ErrorFactory;
import com.example.keult.networking.model.DateAddResponse;
import com.example.keult.networking.service.ApiService;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by demdani on 2016. 10. 18..
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
                        throwable -> {
                            loginCallback.forwardError(ErrorFactory.createNetworkError(throwable));
                        }
                );
    }

    public void signup(Map<String, Object> body, SignUpCallback signUpCallback) {
        apiService.signup(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        signUpCallback::forwardResponse,
                        throwable -> {
                            signUpCallback.forwardError(ErrorFactory.createNetworkError(throwable));
                        }
                );
    }

    public void getASZF(Map<String, Object> body, ConditionsCallback conditionsCallback) {
        apiService.getASZF(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        conditionsCallback::forwardResponse,
                        throwable -> {
                            conditionsCallback.forwardError(ErrorFactory.createNetworkError(throwable));
                        }
                );
    }

    public void addDate(Map<String, Object> body, DateAddCallback dateAddCallback) {
        apiService.addDate(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        dateAddCallback::forwardResponse,
                        throwable -> {
                            dateAddCallback.forwardError(ErrorFactory.createNetworkError(throwable));
                        }
                );
    }
}
