package com.icaboalo.yana.presentation.di.module;

import android.support.v7.app.AppCompatActivity;

import com.icaboalo.yana.domain.executors.PostExecutionThread;
import com.icaboalo.yana.domain.executors.ThreadExecutor;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.respository.Repository;
import com.icaboalo.yana.presentation.di.PerActivity;
import com.icaboalo.yana.presentation.navigation.Navigator;

import dagger.Module;
import dagger.Provides;

/**
 * @author icaboalo on 09/08/16.
 */
@Module
public class ActivityModule {

    private final AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity(){
        return mActivity;
    }

    @Provides
    @PerActivity
    GenericUseCase provideGetGeneralUseCase(Repository repository, PostExecutionThread postExecutionThread, ThreadExecutor threadExecutor){
        return new GenericUseCase(repository, postExecutionThread, threadExecutor);
    }

    @Provides
    @PerActivity
    Navigator provideNavigator(){
        return new Navigator();
    }
}
