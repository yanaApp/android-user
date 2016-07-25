package com.icaboalo.yana.di.module;

import com.icaboalo.yana.interactor.LoginInteractor;
import com.icaboalo.yana.io.ApiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by icaboalo on 22/07/16.
 */
@Module
public class InteractorModule {

    @Provides
    public LoginInteractor provideLoginInteractor(ApiService apiService){
        return new LoginInteractor(apiService);
    }
}
