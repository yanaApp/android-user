package com.icaboalo.yana.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class DayModel extends RealmObject{
    @PrimaryKey
    private long id;
    private String date;
    private RealmList<ActivityModel> activities;

    public DayModel(long id, String date){
        this.id = id;
        this.date = date;
    }
}
