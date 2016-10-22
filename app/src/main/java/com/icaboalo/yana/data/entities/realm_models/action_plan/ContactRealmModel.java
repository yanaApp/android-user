package com.icaboalo.yana.data.entities.realm_models.action_plan;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author icaboalo on 12/08/16.
 */
public class ContactRealmModel extends RealmObject {

    @PrimaryKey
    private int id;

    private String name, relationship;

    @SerializedName("live_together")
    boolean liveTogether;

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

    public String getRelationship() {
        return relationship;
    }

    public boolean isLiveTogether() {
        return liveTogether;
    }
}
