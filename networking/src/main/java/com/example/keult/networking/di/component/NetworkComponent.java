package com.example.keult.networking.di.component;

import dagger.Component;
import com.example.keult.networking.di.module.NetworkModule;
import com.example.keult.networking.service.ApiService;

/**
 * Created by demdani on 2016. 10. 10..
 */

@Component(
        modules = {
                NetworkModule.class
        }
)
public interface NetworkComponent {

        ApiService provideApiService();
}
