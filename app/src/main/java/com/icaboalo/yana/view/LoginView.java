package com.icaboalo.yana.view;

/**
 * Created by icaboalo on 21/07/16.
 */
public interface LoginView extends LoadDataView {

    void loginSuccessful();

    void loginError();
    void usernameError();
    void passwordError();
}
