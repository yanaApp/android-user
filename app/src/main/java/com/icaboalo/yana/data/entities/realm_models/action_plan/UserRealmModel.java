package com.icaboalo.yana.data.entities.realm_models.action_plan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author icaboalo on 10/08/16.
 */
public class UserRealmModel extends RealmObject{

    @PrimaryKey
    private int id;

    private String email, location, occupation, gender;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("action_plan")
    private List<ActionPlanRealmModel> actionPlanList;

    @SerializedName("contacts")
    private List<ContactRealmModel> contactList;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
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

    public List<ActionPlanRealmModel> getActionPlanList() {
        return actionPlanList;
    }

    public List<ContactRealmModel> getContactList() {
        return contactList;
    }
}
