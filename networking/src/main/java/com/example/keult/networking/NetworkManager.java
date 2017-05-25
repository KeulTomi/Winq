package com.example.keult.networking;

import java.util.Map;

import com.example.keult.networking.callback.LoginCallback;
import com.example.keult.networking.di.component.DaggerNetworkComponent;
import com.example.keult.networking.error.factory.ErrorFactory;
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
}
