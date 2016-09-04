package com.icaboalo.yana.old.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class DayModel extends RealmObject{


    @PrimaryKey
    private int id;

    private String date;

    private int number;

    private ActionPlanModel actionPlan;

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
