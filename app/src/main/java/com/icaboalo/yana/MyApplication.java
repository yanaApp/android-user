package com.icaboalo.yana;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by icaboalo on 01/06/16.
 */
public class MyApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // The Realm file will be located in Context.getFilesDir() with name "default.realm"
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("db.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);
//        Log.d("REALM", Realm.getDefaultInstance().getPath());
    }
}
