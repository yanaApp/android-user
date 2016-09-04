package com.icaboalo.yana.presentation.screens.register;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.data.entities.RegisterEntity;
import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.Register;
import com.icaboalo.yana.domain.models.action_plan.User;
import com.icaboalo.yana.presentation.screens.GenericPostPresenter;
import com.icaboalo.yana.presentation.screens.register.view_model.RegisterViewModel;
import com.icaboalo.yana.presentation.screens.register.view_model.UserViewModel;
import com.icaboalo.yana.util.Constants;
import com.icaboalo.yana.util.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author icaboalo on 11/08/16.
 */
public class RegisterPresenter extends GenericPostPresenter<RegisterViewModel> {

    @Inject
    public RegisterPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void post(HashMap<String, Object> postBundle) {
        showViewLoading();
        postBundle.put("category", PrefUtils.getEvaluationResult(getGenericPostView().getApplicationContext()));
        getGenericUseCase().executeDynamicPostObject(new PostSubscriber(), Constants.API_BASE_URL + "user/register/", postBundle,
                Register.class, RegisterEntity.class, RegisterViewModel.class, false);
    }

    @Override
    public void postSuccess(RegisterViewModel model) {
        saveResponseToPrefs(model.getToken());
        saveUserOnRealm(model.getUserViewModel());
        super.postSuccess(model);
    }

    private void saveUserOnRealm(UserViewModel userViewModel){
        showViewLoading();
        Observable.defer(() -> Observable.just(saveUserOnRealmHelper(userViewModel)))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    private boolean saveUserOnRealmHelper(UserViewModel userViewModel){
        String jsonString = new Gson().toJson(userViewModel).replace("full_name", "fullName").replace("phone_number", "phoneNumber")
                .replace("birth_date", "birthDate");
        try {
            JSONObject json = new JSONObject(jsonString);
            getGenericUseCase().executeDynamicPostObject(new SaveSubscriber(), "", json, User.class, UserRealmModel.class,
                    UserViewModel.class, true);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void saveResponseToPrefs(String token){
        Observable.defer(() -> Observable.just(saveResponseToPrefsHelper(token))
                .subscribeOn(Schedulers.io()))
                .subscribe();
    }

    private boolean saveResponseToPrefsHelper(String token){
        Constants.ACCESS_TOKEN = token;
        SharedPreferences.Editor nEditor = getGenericPostView().getApplicationContext().getSharedPreferences(PrefConstants.authFile, Context.MODE_PRIVATE).edit();
        nEditor.putString(PrefConstants.tokenPref, token);
        nEditor.apply();
        return true;
    }

    private class SaveSubscriber extends DefaultSubscriber<UserViewModel>{
        @Override
        public void onCompleted() {
            hideViewLoading();
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
            showViewRetry();
            super.onError(e);
        }

        @Override
        public void onNext(UserViewModel userViewModel) {
        }
    }
}
