package com.icaboalo.yana.presentation.screens.action_plan.view_model;

import com.google.gson.annotations.SerializedName;
import com.icaboalo.yana.domain.models.action_plan.ActionPlan;
import com.icaboalo.yana.domain.models.action_plan.Contact;

import java.util.List;

/**
 * @author icaboalo on 10/08/16.
 */
public class UserViewModel {

    private int id;

    private String email, password, location, occupation, gender;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("action_plan")
    private List<ActionPlanViewModel> actionPlanList;

    @SerializedName("contacts")
    private List<ContactViewModel> contactList;

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

    public List<ActionPlanViewModel> getActionPlanList() {
        return actionPlanList;
    }

    public List<ContactViewModel> getContactList() {
        return contactList;
    }
}
