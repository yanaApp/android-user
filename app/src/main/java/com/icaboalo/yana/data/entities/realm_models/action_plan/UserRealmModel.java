package com.icaboalo.yana.data.entities.realm_models.action_plan;

import com.google.gson.annotations.SerializedName;
import com.icaboalo.yana.data.entities.realm_models.ScheduleRealmModel;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author icaboalo on 10/08/16.
 */
public class UserRealmModel extends RealmObject{

    @PrimaryKey
    private int id;

    private int gender;

    private String email, location, occupation;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("birth_date")
    private String birthDate;

    @SerializedName("depression_motive")
    private String depressionMotive;

    @SerializedName("action_plan")
    private RealmList<ActionPlanRealmModel> actionPlanList;

    @SerializedName("contacts")
    private RealmList<ContactRealmModel> contactList;

    private ScheduleRealmModel schedule;

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

    public RealmList<ActionPlanRealmModel> getActionPlanList() {
        return actionPlanList;
    }

    public RealmList<ContactRealmModel> getContactList() {
        return contactList;
    }

    public ScheduleRealmModel getSchedule() {
        return schedule;
    }
}
