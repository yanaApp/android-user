package com.icaboalo.yana.io.model;

import java.io.Serializable;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActionPlan implements Serializable{

//  CONSTRUCTORS
    public ActionPlan(String name, String description) {
        this.mName = name;
        this.mDescription = description;
    }

//  PROPERTIES
    String mName;
    String mDescription;
    boolean mCompleted = false;


//  GETTERS
    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

//  SETTERS
    public void setName(String mName) {
        this.mName = mName;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setCompleted(boolean mCompleted) {
        this.mCompleted = mCompleted;
    }
}
