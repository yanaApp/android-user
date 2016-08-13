package com.icaboalo.yana.domain.models.action_plan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author icaboalo on 10/08/16.
 */
public class User {

    private int id;

    private String email, password, location, occupation, gender;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("action_plan")
    private List<ActionPlan> actionPlanList;

    @SerializedName("contacts")
    private List<Contact> contactList;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getGender() {
        return gender;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<ActionPlan> getActionPlanList() {
        return actionPlanList;
    }

    public List<Contact> getContactList() {
        return contactList;
    }
}
