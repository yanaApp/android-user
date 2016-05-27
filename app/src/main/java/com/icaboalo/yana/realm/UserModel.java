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

    public String getnPassword() {
        return nPassword;
    }

    public void setnPassword(String nPassword) {
        this.nPassword = nPassword;
    }

    private String nPassword;

}
