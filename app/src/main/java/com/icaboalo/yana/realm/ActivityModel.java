package com.icaboalo.yana.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class ActivityModel extends RealmObject{
    @PrimaryKey
    private int nId;
    private int nActivityNumber;
    private String nActivityName;
    private String nActivityDescription;

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public int getnActivityNumber() {
        return nActivityNumber;
    }

    public void setnActivityNumber(int nActivityNumber) {
        this.nActivityNumber = nActivityNumber;
    }

    public String getnActivityName() {
        return nActivityName;
    }

    public void setnActivityName(String nActivityName) {
        this.nActivityName = nActivityName;
    }

    public String getnActivityDescription() {
        return nActivityDescription;
    }

    public void setnActivityDescription(String nActivityDescription) {
        this.nActivityDescription = nActivityDescription;
    }
}
