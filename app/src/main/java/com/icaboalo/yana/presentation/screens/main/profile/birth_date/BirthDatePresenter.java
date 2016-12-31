package com.icaboalo.yana.presentation.screens.main.profile.birth_date;

import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.User;
import com.icaboalo.yana.presentation.screens.GenericPostPresenter;
import com.icaboalo.yana.presentation.screens.view_model.UserViewModel;
import com.icaboalo.yana.util.Constants;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by icaboalo on 6/12/16.
 */
public class BirthDatePresenter extends GenericPostPresenter<UserViewModel> {

    private int userId;

    @Inject
    public BirthDatePresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void post(HashMap<String, Object> postBundle) {
        getGenericUseCase().executeDynamicPatchObject(new PostSubscriber(), Constants.API_BASE_URL + "user/" + userId + "/",
                postBundle, User.class, UserRealmModel.class, UserViewModel.class, true);
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
