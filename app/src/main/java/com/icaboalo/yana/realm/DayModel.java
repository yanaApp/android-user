package com.icaboalo.yana.realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class DayModel extends RealmObject{


    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private String date;

    @SerializedName("day_number")
    private int number;

    @SerializedName("action_plan")
    private ActionPlanModel actionPlan;

    @SerializedName("answer")
    private int answer = 0;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ActionPlanModel getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(ActionPlanModel actionPlan) {
        this.actionPlan = actionPlan;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
