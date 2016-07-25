package com.icaboalo.yana.presenter;

import com.icaboalo.yana.interactor.LoginInteractor;
import com.icaboalo.yana.io.callback.LoginServerCallback;
import com.icaboalo.yana.view.LoginView;

/**
 * Created by icaboalo on 21/07/16.
 */
public class LoginPresenter extends BasePresenter implements LoginServerCallback{

    LoginView mLoginView;
    LoginInteractor mLoginInteractor;

    public LoginPresenter(LoginView loginView, LoginInteractor loginInteractor) {
        mLoginView = loginView;
        mLoginInteractor = loginInteractor;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void loginSuccessful() {
        mLoginView.loginSuccessful();
    }

    @Override
    public void serverError() {
        mLoginView.loginError();
    }

    public void attemptLogin(String username, String password){
        showViewLoading();
        mLoginInteractor.login(username, password, this);
    }

    public void showViewLoading(){
        mLoginView.showLoading();
    }

    public void hideViewLoading(){
        mLoginView.hideLoading();
    }

    public void showViewRetry(){
        mLoginView.showRetry();
    }

    public void hideViewRetry(){
        mLoginView.hideRetry();
    }

    public void showError(String error){
        mLoginView.showError(error);
    }
}
