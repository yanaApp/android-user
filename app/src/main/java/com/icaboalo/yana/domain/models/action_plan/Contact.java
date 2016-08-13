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
}
