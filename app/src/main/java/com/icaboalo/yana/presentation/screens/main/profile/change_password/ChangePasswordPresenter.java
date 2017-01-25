package com.icaboalo.yana.presentation.screens.main.profile.change_password;

import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.User;
import com.icaboalo.yana.presentation.screens.GenericPostPresenter;
import com.icaboalo.yana.presentation.view_model.UserViewModel;
import com.icaboalo.yana.util.Constants;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by icaboalo on 10/11/16.
 */

public class ChangePasswordPresenter extends GenericPostPresenter<UserViewModel> {

    @Inject
    public ChangePasswordPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void post(HashMap<String, Object> postBundle) {
        hideViewRetry();
        showViewLoading();
        getGenericUseCase().executeDynamicPostObject(new PostSubscriber(), Constants.API_BASE_URL + "user/change_password/", postBundle,
                User.class, UserRealmModel.class, UserViewModel.class, false);
    }
}
