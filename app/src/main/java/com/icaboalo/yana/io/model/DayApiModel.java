package com.icaboalo.yana.io.model;

import com.google.gson.annotations.SerializedName;
import com.icaboalo.yana.realm.ActionPlanModel;
import com.icaboalo.yana.realm.ActivityModel;

import java.util.List;

import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by icaboalo on 09/06/16.
 */
public class DayApiModel {


    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private String date;

    @SerializedName("day_number")
    private int number;

    @SerializedName("answer")
    private int answer = 0;

    @SerializedName("activities")
    private List<ActivityApiModel> activityList;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getNumber() {
        return number;
    }

    public int getAnswer() {
        return answer;
    }

    public List<ActivityApiModel> getActivityList() {
        return activityList;
    }
}
