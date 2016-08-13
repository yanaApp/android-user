package com.icaboalo.yana.domain.models.action_plan;

import android.app.*;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author icaboalo on 12/08/16.
 */
public class Day {

    private int id;

    @SerializedName("day_number")
    private int dayNumber;

    private String date;

    @SerializedName("activities")
    private List<Activity> activityList;

    public int getId() {
        return id;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
