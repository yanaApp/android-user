package com.icaboalo.yana.io.model;

import java.io.Serializable;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActivityApiModel implements Serializable{

//  CONSTRUCTORS
    public ActivityApiModel(String name, String description) {
        this.mName = name;
        this.mDescription = description;
    }

//  PROPERTIES
    int mId;
    String mName;
    String mImage = "";
    String mDescription;
    int mAnswer = 0;


//  GETTERS
    public int getmId() {
        return mId;
    }

    public String getName() {
        return mName;
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

//  SETTERS
    public void setName(String mName) {
        this.mName = mName;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setanswer(int answer) {
        this.mAnswer = answer;
    }
}
