package com.icaboalo.yana.presentation.view_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author icaboalo on 10/08/16.
 */
public class DayViewModel {

    private int id;

    @SerializedName("day_number")
    private int dayNumber;

    private String date;

    @SerializedName("activities")
    private List<ActivityViewModel> activityList;

    public int getId() {
        return id;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public List<ActivityViewModel> getActivityList() {
        return activityList;
    }

    public String getDate() {
        return date;
    }

    public boolean isEmpty() {
        return (this.date == null || this.date.isEmpty()) && (this.activityList == null || this.activityList.isEmpty());
    }
}
