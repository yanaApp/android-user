package com.icaboalo.yana.presentation.screens.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.data.entities.LoginEntity;
import com.icaboalo.yana.data.entities.RecoverPasswordEntity;
import com.icaboalo.yana.domain.exception.DefaultErrorBundle;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.Login;
import com.icaboalo.yana.domain.models.RecoverPassword;
import com.icaboalo.yana.presentation.screens.GenericPostPresenter;
import com.icaboalo.yana.presentation.screens.view_model.LoginViewModel;
import com.icaboalo.yana.presentation.screens.view_model.RecoverPasswordViewModel;
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
        super.postSuccess(model);
    }

    private void saveResponseToPrefs(LoginViewModel loginViewModel){
        Observable.defer(() -> Observable.just(saveResponseToPrefsHelper(loginViewModel))
                .subscribeOn(Schedulers.io()))
                .subscribe();
    }

    private boolean saveResponseToPrefsHelper(LoginViewModel loginViewModel){
        Constants.ACCESS_TOKEN = loginViewModel.getToken();
        SharedPreferences.Editor nEditor = getGenericPostView().getApplicationContext().getSharedPreferences(PrefConstants.authFile, Context.MODE_PRIVATE).edit();
        nEditor.putString(PrefConstants.tokenPref, loginViewModel.getToken());
        nEditor.apply();
        return true;
    }

    public void attemptRecoverPassword(String email){
        showViewLoading();
        recoverPassword(email);
    }

    private void recoverPassword(String email){
        HashMap<String, Object> recoverBundle = new HashMap<>(1);
        recoverBundle.put("email", email);
        getGenericUseCase().executeDynamicPostObject(new RecoverPasswordSubscriber(), Constants.API_BASE_URL + "user/restore/",
                recoverBundle, RecoverPassword.class, RecoverPasswordEntity.class, RecoverPasswordViewModel.class, false);
    }

    private void recoverPasswordSuccess(RecoverPasswordViewModel recoverPasswordViewModel){
        ((LoginView) getGenericPostView()).recoverPasswordSuccess(recoverPasswordViewModel);
    }

    private class RecoverPasswordSubscriber extends DefaultSubscriber<RecoverPasswordViewModel>{
        @Override
        public void onCompleted() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
            showViewRetry();
            showErrorMessage(new DefaultErrorBundle((Exception) e));
            e.printStackTrace();
        }

        @Override
        public void onNext(RecoverPasswordViewModel recoverPasswordViewModel) {
            super.onNext(recoverPasswordViewModel);
            recoverPasswordSuccess(recoverPasswordViewModel);
        }
    }
}
