package com.icaboalo.yana.presentation.screens.view_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author icaboalo on 10/08/16.
 */
public class UserViewModel {

    private int id, gender;

    private String email, password, location, occupation;

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

    private ScheduleViewModel schedule;

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

    public int getGender() {
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

    public ScheduleViewModel getSchedule() {
        return schedule;
    }

    public boolean isEmpty() {
        return (this.email == null || this.email.isEmpty()) && (this.location == null || this.location.isEmpty())
                && (this.occupation == null || this.occupation.isEmpty()) && (this.fullName == null || this.fullName.isEmpty())
                && (this.phoneNumber == null || this.phoneNumber.isEmpty()) && (this.birthDate == null || this.birthDate.isEmpty())
                && (this.depressionMotive == null || this.depressionMotive.isEmpty())
                && (this.actionPlanList == null || this.actionPlanList.isEmpty())
                && (this.contactList == null || this.contactList.isEmpty()) && this.schedule.isEmpty();
    }
}
