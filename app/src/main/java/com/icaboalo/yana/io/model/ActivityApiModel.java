package com.icaboalo.yana.io.model;

import com.google.gson.annotations.SerializedName;
import com.icaboalo.yana.realm.DayModel;

import java.io.Serializable;

import io.realm.annotations.PrimaryKey;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActivityApiModel implements Serializable{

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("answer")
    private int answer = 0;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getAnswer() {
        return answer;
    }
}
