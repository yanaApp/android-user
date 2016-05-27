package com.icaboalo.yana.realm;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class UserModel extends RealmObject {
    @PrimaryKey
    private long id;
    private String user_name;
    private String email;
    private String phone_number;
    private String password;

}
