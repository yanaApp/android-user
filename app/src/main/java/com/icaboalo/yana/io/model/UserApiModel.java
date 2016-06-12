package com.icaboalo.yana.io.model;

import com.google.gson.annotations.SerializedName;
import com.icaboalo.yana.realm.ActionPlanModel;

import java.util.ArrayList;

import io.realm.annotations.Ignore;

/**
 * Created by icaboalo on 09/06/16.
 */
public class UserApiModel {

    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String userName;

    @SerializedName("email")
    private String email;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("password")
    private String password;

    @SerializedName("token")
    private String token;

    @SerializedName("action_plan")
    private ArrayList<ActionPlanApiModel> actionPlanList;

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public ArrayList<ActionPlanApiModel> getActionPlanList() {
        return actionPlanList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setActionPlanList(ArrayList<ActionPlanApiModel> actionPlanList) {
        this.actionPlanList = actionPlanList;
    }
}
