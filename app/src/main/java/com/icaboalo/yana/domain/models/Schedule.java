package com.icaboalo.yana.domain.models;

import com.google.gson.annotations.SerializedName;

/**
 * @author icaboalo on 11/09/16.
 */
public class Schedule {

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

    public void setId(int id) {
        this.id = id;
    }

    public void setStudy(boolean study) {
        this.study = study;
    }

    public void setWork(boolean work) {
        this.work = work;
    }

    public void setWorkout(boolean workout) {
        this.workout = workout;
    }

    public void setStudyTimeFrom(String studyTimeFrom) {
        this.studyTimeFrom = studyTimeFrom;
    }

    public void setStudyTimeTo(String studyTimeTo) {
        this.studyTimeTo = studyTimeTo;
    }

    public void setStudyDayFrom(String studyDayFrom) {
        this.studyDayFrom = studyDayFrom;
    }

    public void setStudyDayTo(String studyDayTo) {
        this.studyDayTo = studyDayTo;
    }

    public void setWorkTimeFrom(String workTimeFrom) {
        this.workTimeFrom = workTimeFrom;
    }

    public void setWorkTimeTo(String workTimeTo) {
        this.workTimeTo = workTimeTo;
    }

    public void setWorkDayFrom(String workDayFrom) {
        this.workDayFrom = workDayFrom;
    }

    public void setWorkDayTo(String workDayTo) {
        this.workDayTo = workDayTo;
    }

    public void setBreakfastTime(String breakfastTime) {
        this.breakfastTime = breakfastTime;
    }

    public void setLunchTime(String lunchTime) {
        this.lunchTime = lunchTime;
    }

    public void setDinnerTime(String dinnerTime) {
        this.dinnerTime = dinnerTime;
    }

    public void setWakeUpTime(String wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public boolean isEmpty() {
        if ((this.studyTimeFrom == null || this.studyDayFrom.isEmpty()) && (this.studyTimeTo == null || this.studyTimeTo.isEmpty())
                && (this.studyDayFrom == null || this.studyDayFrom.isEmpty()) && (this.studyDayTo == null || this.studyDayTo.isEmpty())
                && (this.workTimeFrom == null || this.workTimeFrom.isEmpty()) && (this.workTimeTo == null || this.workTimeTo.isEmpty())
                && (this.workDayFrom == null || this.workDayFrom.isEmpty()) && (this.workDayTo == null || this.workDayTo.isEmpty())
                && (this.breakfastTime == null || this.breakfastTime.isEmpty()) && (this.lunchTime == null || this.lunchTime.isEmpty())
                && (this.dinnerTime == null || this.dinnerTime.isEmpty()) && (this.wakeUpTime == null || this.wakeUpTime.isEmpty())
                && (this.sleepTime == null || this.sleepTime.isEmpty()))
            return true;
        return false;
    }
}
