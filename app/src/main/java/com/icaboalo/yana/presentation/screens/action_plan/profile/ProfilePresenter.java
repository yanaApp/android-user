package com.icaboalo.yana.presentation.screens.action_plan.profile;

import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.User;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.UserViewModel;

import javax.inject.Inject;

/**
 * @author icaboalo on 24/08/16.
 */
public class ProfilePresenter extends GenericDetailPresenter<UserViewModel> {

    @Inject
    public ProfilePresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemDetails() {
        getGenericUseCase().executeDynamicGetObject(new ItemDetailSubscriber(), "email", "", mItemId, User.class, UserRealmModel.class,
                UserViewModel.class, false);
    }
}
