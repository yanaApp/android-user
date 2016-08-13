package com.icaboalo.yana.domain.models.action_plan;

import com.google.gson.annotations.SerializedName;

/**
 * @author icaboalo on 12/08/16.
 */
public class Contact {

    private int id;

    private String name;

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
}
