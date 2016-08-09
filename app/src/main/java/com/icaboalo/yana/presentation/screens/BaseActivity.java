package com.icaboalo.yana.presentation.screens;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.presentation.di.HasComponent;
import com.icaboalo.yana.presentation.di.component.ApplicationComponent;
import com.icaboalo.yana.presentation.di.component.DaggerUserComponent;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.di.module.ActivityModule;
import com.icaboalo.yana.presentation.navigation.Navigator;

import javax.inject.Inject;

/**
 * @author icaboalo on 31/07/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements HasComponent<UserComponent>{

    @Inject
    Navigator navigator;
    private UserComponent mUserComponent;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        getApplicationComponent().inject(this);
        initializeInjector();
        initialize();
        setupUI();
    }

    @Override
    public UserComponent getComponent() {
        return mUserComponent;
    }

    public abstract void initialize();
    public abstract void setupUI();


    /**
            * Get the Main Application component for dependency injection.
            *
            * @return {@link com.icaboalo.yana.presentation.di.component.ApplicationComponent}
    */
    protected ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplicationContext()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link com.icaboalo.yana.presentation.di.module.ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    public void initializeInjector() {
        mUserComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) this).getComponent());
    }
}
