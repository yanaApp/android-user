package com.icaboalo.yana.data.entities.realm_models.action_plan;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author icaboalo on 12/08/16.
 */
public class ActionPlanRealmModel extends RealmObject {

    @PrimaryKey
    private int id;

    private String category;

    @SerializedName("is_active")
    private boolean isActive;

    @SerializedName("initial_date")
    private Date initialDate;
    @SerializedName("final_date")
    private Date finalDate;

    @SerializedName("days")
    private RealmList<DayRealmModel> dayList;

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public boolean isActive() {
        return isActive;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public RealmList<DayRealmModel> getDayList() {
        return dayList;
    }
}
