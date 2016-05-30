package com.icaboalo.yana.realm;



import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class UserModel extends RealmObject {
    @PrimaryKey
    private long id;
    private String nUser_name;
    private String nEmail;
    private String nPhone_number;
    private String nPassword;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getnUser_name() {
        return nUser_name;
    }

    public void setnUser_name(String nUser_name) {
        this.nUser_name = nUser_name;
    }

    public String getnEmail() {
        return nEmail;
    }

    public void setnEmail(String nEmail) {
        this.nEmail = nEmail;
    }

    public String getnPhone_number() {
        return nPhone_number;
    }

    public void setnPhone_number(String nPhone_number) {
        this.nPhone_number = nPhone_number;
    }

    public String getnPassword() {
        return nPassword;
    }

    public void setnPassword(String nPassword) {
        this.nPassword = nPassword;
    }
}
