package com.icaboalo.yana.io.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActivityApiModel implements Serializable{

//  CONSTRUCTORS
    public ActivityApiModel() {
    }

    public ActivityApiModel(String name, String description) {
        this.mTitle = name;
        this.mDescription = description;
    }

//  PROPERTIES
    @SerializedName("id")
    int mId;

    @SerializedName("title")
    String mTitle;

    @SerializedName("image")
    String mImage = "";

    @SerializedName("description")
    String mDescription;

    @SerializedName("answer")
    int mAnswer = 0;

    @SerializedName("day")
    int mDay;


//  GETTERS
    public int getmId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getmImage() {
        return mImage;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getAnswer() {
        return mAnswer;
    }

    public int getDay(){
        return mDay;
    }

//  SETTERS
    public void setTitle(String mName) {
        this.mTitle = mName;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setanswer(int answer) {
        this.mAnswer = answer;
    }

    public void setDay(int day){
        this.mDay = day;
    }
}
