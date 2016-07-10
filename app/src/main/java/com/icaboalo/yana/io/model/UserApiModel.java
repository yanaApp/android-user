package com.icaboalo.yana.io.model;

import com.google.gson.annotations.SerializedName;
import com.icaboalo.yana.realm.ActionPlanModel;

import java.util.ArrayList;

import io.realm.annotations.Ignore;

/**
 * Created by icaboalo on 09/06/16.
 */
public class UserApiModel {

    @SerializedName("id")
    private int id;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("birth_date")
    private String birthDate;

    @SerializedName("email")
    private String email;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("password")
    private String password;

    @SerializedName("token")
    private String token;

    @SerializedName("category")
    private int category;

    @SerializedName("action_plan")
    private ArrayList<ActionPlanApiModel> actionPlanList;

    @SerializedName("gender")
    private String gender;

    @SerializedName("location")
    private String location;

    @SerializedName("occupation")
    private String occupation;

    @SerializedName("contacts")
    private ArrayList<ContactApiModel> contactList;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public ArrayList<ActionPlanApiModel> getActionPlanList() {
        return actionPlanList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setActionPlanList(ArrayList<ActionPlanApiModel> actionPlanList) {
        this.actionPlanList = actionPlanList;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public ArrayList<ContactApiModel> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<ContactApiModel> contactList) {
        this.contactList = contactList;
    }
}
