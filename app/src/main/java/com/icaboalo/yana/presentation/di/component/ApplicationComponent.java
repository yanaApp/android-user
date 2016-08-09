package com.icaboalo.yana.presentation.di.component;

import android.content.Context;

import com.google.gson.Gson;
import com.icaboalo.yana.domain.executors.PostExecutionThread;
import com.icaboalo.yana.domain.executors.ThreadExecutor;
import com.icaboalo.yana.domain.respository.Repository;
import com.icaboalo.yana.presentation.di.module.ApplicationModule;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.BaseFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author icaboalo on 09/08/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

//    Exposed to sub-graphs
    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    Repository repository();

    Gson gson();
}
