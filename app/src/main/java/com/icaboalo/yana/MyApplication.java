package com.icaboalo.yana;

import com.icaboalo.yana.presentation.di.component.ApplicationComponent;
import com.icaboalo.yana.presentation.di.component.DaggerActivityComponent;
import com.icaboalo.yana.presentation.di.component.DaggerApplicationComponent;
import com.icaboalo.yana.presentation.di.module.ApplicationModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by icaboalo on 01/06/16.
 */
public class MyApplication extends android.app.Application {

    private ApplicationComponent mApplicationComponent;
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initializeRealm();
        initializeInjection();
    }

    private void initializeRealm(){
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("db.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private void initializeInjection(){
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(instance))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
