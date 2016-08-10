package com.icaboalo.yana.presentation.screens.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.icaboalo.yana.data.entities.LoginEntity;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.Login;
import com.icaboalo.yana.presentation.screens.GenericPostPresenter;
import com.icaboalo.yana.presentation.screens.login.view_model.LoginViewModel;
import com.icaboalo.yana.util.Constants;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author icaboalo on 09/08/16.
 */
public class LoginPresenter extends GenericPostPresenter<LoginViewModel>{

    @Inject
    public LoginPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void post(HashMap<String, Object> postBundle) {
        hideViewRetry();
        showViewLoading();
        getGenericUseCase().executeDynamicPostObject(new PostSubscriber(), Constants.API_BASE_URL + "login/", postBundle, Login.class, LoginEntity.class,
                LoginViewModel.class, false);
    }

    @Override
    public void postSuccess(LoginViewModel model) {
        saveResponseToPrefs(model);
    }

    private void saveResponseToPrefs(LoginViewModel loginViewModel){
        Observable.defer(() -> Observable.just(saveResponseToPrefsHelper(loginViewModel))
                .subscribeOn(Schedulers.io()))
                .subscribe();
    }

    private boolean saveResponseToPrefsHelper(LoginViewModel loginViewModel){
        Constants.ACCESS_TOKEN = loginViewModel.getToken();
        SharedPreferences.Editor nEditor = getGenericPostView().getApplicationContext().getSharedPreferences("", Context.MODE_PRIVATE).edit();
        nEditor.putString(Constants.ACCESS_TOKEN, loginViewModel.getToken());
        nEditor.apply();
        return true;
    }
}
