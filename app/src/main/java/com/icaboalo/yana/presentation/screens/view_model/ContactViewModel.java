package com.icaboalo.yana.presentation.screens.view_model;

import com.google.gson.annotations.SerializedName;

/**
 * @author icaboalo on 12/08/16.
 */
public class ContactViewModel {

    private int id;

    private String name, relationship;

    @SerializedName("live_together")
    boolean liveTogether;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("is_validated")
    private boolean isValidated;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRelationship() {
        return relationship;
    }

    public boolean liveTogether() {
        return liveTogether;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public boolean isEmpty() {
        return (this.phoneNumber == null || this.phoneNumber.isEmpty()) && (this.name == null || this.name.isEmpty())
                && (this.relationship == null || this.relationship.isEmpty());
    }
}
