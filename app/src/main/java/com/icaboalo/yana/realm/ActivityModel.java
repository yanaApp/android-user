package com.icaboalo.yana.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class ActivityModel extends RealmObject{
    @PrimaryKey
    private int nId;

    public String getnActivityName() {
        return nActivityName;
    }

    public void setnActivityName(String nActivityName) {
        this.nActivityName = nActivityName;
    }

    private String nActivityName;
    private String nActivityDescription;
    private boolean nActivityStatus;
}
