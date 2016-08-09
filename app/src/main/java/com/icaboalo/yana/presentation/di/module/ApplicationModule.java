package com.icaboalo.yana.presentation.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.data.db.DataBaseManager;
import com.icaboalo.yana.data.db.GeneralRealmManagerImpl;
import com.icaboalo.yana.data.executor.JobExecutor;
import com.icaboalo.yana.data.repository.DataRepository;
import com.icaboalo.yana.domain.executors.PostExecutionThread;
import com.icaboalo.yana.domain.executors.ThreadExecutor;
import com.icaboalo.yana.domain.respository.Repository;
import com.icaboalo.yana.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {

    private final MyApplication application;

    public ApplicationModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(){
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor){
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread){
        return uiThread;
    }

    @Singleton
    @Provides
    DataBaseManager provideGeneralRealmManager(GeneralRealmManagerImpl generalRealmManager){
        return generalRealmManager;
    }

    @Singleton
    @Provides
    Repository provideRepository(DataRepository dataRepository){
        return dataRepository;
    }

    @Singleton
    @Provides
    Gson provideGson(){
        return new Gson();
    }
}
