package com.icaboalo.yana.presentation.di.component;

import android.support.v7.app.AppCompatActivity;

import com.icaboalo.yana.presentation.di.PerActivity;
import com.icaboalo.yana.presentation.di.module.ActivityModule;

import dagger.Component;

/**
 * @author icaboalo on 09/08/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

//    Exposed to sub-graphs
    AppCompatActivity activity();
}
