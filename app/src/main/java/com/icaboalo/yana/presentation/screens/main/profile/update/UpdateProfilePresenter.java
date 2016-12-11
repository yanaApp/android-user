package com.icaboalo.yana.presentation.screens.main.profile.update;

import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.User;
import com.icaboalo.yana.presentation.screens.GenericPostPresenter;
import com.icaboalo.yana.presentation.screens.view_model.UserViewModel;
import com.icaboalo.yana.util.Constants;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * @author icaboalo on 25/08/16.
 */
public class UpdateProfilePresenter extends GenericPostPresenter<UserViewModel> {

    private int userId;

    @Inject
    public UpdateProfilePresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void post(HashMap<String, Object> postBundle) {
        showViewLoading();
        getGenericUseCase().executeDynamicPatchObject(new PostSubscriber(), Constants.API_BASE_URL + "user/" + userId + "/", postBundle,
                User.class, UserRealmModel.class, UserViewModel.class, true);
    }

    public void setId(int id) {
        userId = id;
    }
}
