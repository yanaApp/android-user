package com.icaboalo.yana.di.component;

import android.content.Context;

import com.icaboalo.yana.di.module.ApplicationModule;
import com.icaboalo.yana.di.module.InteractorModule;
import com.icaboalo.yana.interactor.LoginInteractor;

import dagger.Component;

/**
 * Created by icaboalo on 22/07/16.
 */
@Component(modules = {ApplicationModule.class, InteractorModule.class})
public interface ApplicationComponent {

    Context getContext();
    LoginInteractor loginInteractor();

}
