package com.icaboalo.yana.interactor;

import com.icaboalo.yana.io.ApiService;
import com.icaboalo.yana.io.callback.LoginServerCallback;

import java.util.HashMap;

/**
 * Created by icaboalo on 22/07/16.
 */
public class LoginInteractor {

    public LoginInteractor(ApiService apiService) {
    }

    public void login(String username, String password, LoginServerCallback loginServerCallback){
        HashMap<String, Object> loginBundle = new HashMap<>();
        loginBundle.put("username", username);
        loginBundle.put("password", password);
    }
}
