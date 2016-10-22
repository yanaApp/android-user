package com.icaboalo.yana.domain.models.action_plan;

import com.google.gson.annotations.SerializedName;

/**
 * @author icaboalo on 12/08/16.
 */
public class Contact {

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRelationship() {
        return relationship;
    }

    public boolean isLiveTogether() {
        return liveTogether;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setLiveTogether(boolean liveTogether) {
        this.liveTogether = liveTogether;
    }
}
