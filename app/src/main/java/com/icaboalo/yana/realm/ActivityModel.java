package com.icaboalo.yana.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class ActivityModel extends RealmObject{
    @PrimaryKey
    private int id;
    private int activityNumber;
    private String activityName;
    private String activityDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityNumber() {
        return activityNumber;
    }

    public void setActivityNumber(int activityNumber) {
        this.activityNumber = activityNumber;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }
}
