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

    @SerializedName("birth_date")
    private String birthDate;

    @SerializedName("depression_motive")
    private String depressionMotive;

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

    public String getBirthDate() {
        return birthDate;
    }

    public String getDepressionMotive() {
        return depressionMotive;
    }

    public List<ActionPlan> getActionPlanList() {
        return actionPlanList;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setDepressionMotive(String depressionMotive) {
        this.depressionMotive = depressionMotive;
    }

    public void setActionPlanList(List<ActionPlan> actionPlanList) {
        this.actionPlanList = actionPlanList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
}
