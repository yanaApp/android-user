package com.icaboalo.yana.domain.models.action_plan;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * @author icaboalo on 12/08/16.
 */
public class ActionPlan {

    private int id;

    private String category;

    @SerializedName("is_active")
    private boolean isActive;

    @SerializedName("initial_date")
    private Date initialDate;
    @SerializedName("final_date")
    private Date finalDate;

    @SerializedName("days")
    private List<Day> dayList;

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public boolean isActive() {
        return isActive;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public List<Day> getDayList() {
        return dayList;
    }
}
