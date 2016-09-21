package com.icaboalo.yana.presentation.screens.schedule.view_model;

import com.google.gson.annotations.SerializedName;

/**
 * @author icaboalo on 07/09/16.
 */
public class ScheduleViewModel {

    private int id;
    private boolean study, work, workout;

    @SerializedName("study_time_from")
    private String studyTimeFrom;

    @SerializedName("study_time_to")
    private String studyTimeTo;

    @SerializedName("study_day_from")
    private String studyDayFrom;

    @SerializedName("study_day_to")
    private String studyDayTo;

    @SerializedName("work_time_from")
    private String workTimeFrom;

    @SerializedName("work_time_to")
    private String workTimeTo;

    @SerializedName("work_day_from")
    private String workDayFrom;

    @SerializedName("work_day_to")
    private String workDayTo;

    @SerializedName("breakfast_time")
    private String breakfastTime;

    @SerializedName("lunch_time")
    private String lunchTime;

    @SerializedName("dinner_time")
    private String dinnerTime;

    @SerializedName("wake_up_time")
    private String wakeUpTime;

    @SerializedName("sleep_time")
    private String sleepTime;

    public int getId() {
        return id;
    }

    public boolean isStudy() {
        return study;
    }

    public boolean isWork() {
        return work;
    }

    public boolean isWorkout() {
        return workout;
    }

    public String getStudyTimeFrom() {
        return studyTimeFrom;
    }

    public String getStudyTimeTo() {
        return studyTimeTo;
    }

    public String getStudyDayFrom() {
        return studyDayFrom;
    }

    public String getStudyDayTo() {
        return studyDayTo;
    }

    public String getWorkTimeFrom() {
        return workTimeFrom;
    }

    public String getWorkTimeTo() {
        return workTimeTo;
    }

    public String getWorkDayFrom() {
        return workDayFrom;
    }

    public String getWorkDayTo() {
        return workDayTo;
    }

    public String getBreakfastTime() {
        return breakfastTime;
    }

    public String getLunchTime() {
        return lunchTime;
    }

    public String getDinnerTime() {
        return dinnerTime;
    }

    public String getWakeUpTime() {
        return wakeUpTime;
    }

    public String getSleepTime() {
        return sleepTime;
    }
}
