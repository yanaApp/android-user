package com.icaboalo.yana.presentation.screens.main;

import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.User;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.presentation.screens.main.view_model.UserViewModel;

import javax.inject.Inject;

/**
 * @author icaboalo on 13/08/16.
 */
public class MainPresenter extends GenericDetailPresenter<UserViewModel> {

    @Inject
    public MainPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemDetails() {
        getGenericUseCase().executeDynamicGetObject(new ItemDetailSubscriber(), "id", "", mItemId, User.class,
                UserRealmModel.class, UserViewModel.class, false);
    }
}
