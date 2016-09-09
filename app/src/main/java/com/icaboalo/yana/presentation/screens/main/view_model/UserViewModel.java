package com.icaboalo.yana.presentation.screens.main.view_model;

import com.google.gson.annotations.SerializedName;

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

    @SerializedName("birth_date")
    private String birthDate;

    @SerializedName("depression_motive")
    private String depressionMotive;

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

    public String getBirthDate() {
        return birthDate;
    }

    public String getDepressionMotive() {
        return depressionMotive;
    }

    public List<ActionPlanViewModel> getActionPlanList() {
        return actionPlanList;
    }

    public List<ContactViewModel> getContactList() {
        return contactList;
    }
}
