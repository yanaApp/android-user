package com.icaboalo.yana.realm;



import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class UserModel extends RealmObject {

    public UserModel() {
    }

    @PrimaryKey
    private int id;
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


