package com.icaboalo.yana.presentation.screens.action_plan.profile.update;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericPostPresenter;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.UserViewModel;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * @author icaboalo on 25/08/16.
 */
public class UpdateProfilePresenter extends GenericPostPresenter<UserViewModel> {

    @Inject
    public UpdateProfilePresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void post(HashMap<String, Object> postBundle) {

    }
}
