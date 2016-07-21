package com.icaboalo.yana.io.model;

import com.google.gson.annotations.SerializedName;
import com.icaboalo.yana.realm.DayModel;
import com.icaboalo.yana.realm.UserModel;

import java.util.List;

/**
 * Created by icaboalo on 09/06/16.
 */
public class ActionPlanApiModel {

    @SerializedName("id")
    private int id;

    @SerializedName("category")
    private String category;

    @SerializedName("initial_date")
    private String initialDate;

    @SerializedName("final_date")
    private String finalDate;

    @SerializedName("is_activate")
    private boolean isActive;

    @SerializedName("days")
    private List<DayApiModel> dayList;

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<DayApiModel> getDayList() {
        return dayList;
    }
}
