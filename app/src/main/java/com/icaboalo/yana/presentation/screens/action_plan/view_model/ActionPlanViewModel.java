package com.icaboalo.yana.presentation.screens.action_plan.view_model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * @author icaboalo on 10/08/16.
 */
public class ActionPlanViewModel {

    private int id;

    private String category;

    @SerializedName("is_active")
    private boolean isActive;

    @SerializedName("initial_date")
    private Date initialDate;
    @SerializedName("final_date")
    private Date finalDate;

    @SerializedName("days")
    private List<DayViewModel> dayList;

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

    public List<DayViewModel> getDayList() {
        return dayList;
    }
}
