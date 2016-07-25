package com.icaboalo.yana.di.module;

import android.app.Application;
import android.content.Context;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.io.ApiClient;
import com.icaboalo.yana.io.ApiService;

import javax.inject.Singleton;

import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by icaboalo on 22/07/16.
 */
public class ApplicationModule {

    private final MyApplication application;

    public ApplicationModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofitInstance(){
        return ApiClient.sRetrofit();
    }

    @Provides
    @Singleton
    public ApiService providesApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }
}
