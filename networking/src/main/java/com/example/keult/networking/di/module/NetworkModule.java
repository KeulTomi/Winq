package com.example.keult.networking.di.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import com.example.keult.networking.constant.ApiConstants;
import com.example.keult.networking.service.ApiService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by demdani on 2016. 10. 10..
 */

@Module
public class NetworkModule {

    @Provides
    ApiService provideApiService(
            GsonConverterFactory converterFactory,
            RxJavaCallAdapterFactory callAdapterFactory,
            OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(ApiConstants.BASE_URL);
        builder.client(okHttpClient);
        builder.addConverterFactory(converterFactory);
        builder.addCallAdapterFactory(callAdapterFactory);
        return builder.build().create(ApiService.class);
    }

    @Provides
    OkHttpClient provideHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(loggingInterceptor);
        return builder.build();
    }

    @Provides
    RxJavaCallAdapterFactory provideRxJavaAdapter(Scheduler scheduler) {
        return RxJavaCallAdapterFactory.createWithScheduler(scheduler);
    }

    @Provides
    GsonConverterFactory provideGsonConverterFactoryLowerCaseWithUnderScores(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    Scheduler provideScheduler() {
        return Schedulers.io();
    }
}
