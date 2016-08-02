package com.icaboalo.yana.presentation.di.module;

import android.content.Context;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.data.executor.JobExecutor;
import com.icaboalo.yana.domain.executors.PostExecutionThread;
import com.icaboalo.yana.domain.executors.ThreadExecutor;
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


}
