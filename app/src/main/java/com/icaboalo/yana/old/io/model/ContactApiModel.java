package com.icaboalo.yana.old.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by icaboalo on 06/06/16.
 */
public class ContactApiModel {

    private int id;

    private String name;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("is_validated")
    private boolean isValidated = false;

    private String relation;

    @SerializedName("live_together")
    private boolean liveTogether;

    private UserApiModel user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public boolean isLiveTogether() {
        return liveTogether;
    }

    public void setLiveTogether(boolean liveTogether) {
        this.liveTogether = liveTogether;
    }

    public UserApiModel getUser() {
        return user;
    }

    public void setUser(UserApiModel user) {
        this.user = user;
    }
}
