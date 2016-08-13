package com.icaboalo.yana.data.entities.realm_models.action_plan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author icaboalo on 12/08/16.
 */
public class DayRealmModel extends RealmObject{

    @PrimaryKey
    private int id;

    @SerializedName("day_number")
    private int dayNumber;

    @SerializedName("activities")
    private RealmList<ActivityRealmModel> activityList;

    public int getId() {
        return id;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public RealmList<ActivityRealmModel> getActivityList() {
        return activityList;
    }
}
