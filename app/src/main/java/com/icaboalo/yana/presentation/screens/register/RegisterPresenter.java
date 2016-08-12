package com.icaboalo.yana.presentation.screens.register;

import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.User;
import com.icaboalo.yana.presentation.screens.GenericPostPresenter;
import com.icaboalo.yana.presentation.screens.register.view_model.RegisterViewModel;
import com.icaboalo.yana.util.Constants;

import java.util.HashMap;

import javax.inject.Inject;

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
        getGenericUseCase().executeDynamicPostObject(new PostSubscriber(), Constants.API_BASE_URL + "user/register/", postBundle,
                User.class, UserRealmModel.class, RegisterViewModel.class, true);
    }
}
