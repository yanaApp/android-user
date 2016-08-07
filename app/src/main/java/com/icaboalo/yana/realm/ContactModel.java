package com.icaboalo.yana.realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by icaboalo on 06/06/16.
 */
public class ContactModel extends RealmObject {

    @PrimaryKey
    private int id;

    private String name;

    private String phoneNumber;

    private boolean isValidated = false;

    private String relation;

    private boolean liveTogether;

    private UserModel user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public boolean isLiveTogether() {
        return liveTogether;
    }

    public void setLiveTogether(boolean liveTogether) {
        this.liveTogether = liveTogether;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
