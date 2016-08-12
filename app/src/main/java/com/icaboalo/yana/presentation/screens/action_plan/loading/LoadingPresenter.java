package com.icaboalo.yana.presentation.screens.action_plan.loading;

import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.User;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.UserViewModel;
import com.icaboalo.yana.util.Constants;

import javax.inject.Inject;

/**
 * @author icaboalo on 10/08/16.
 */
public class LoadingPresenter extends GenericDetailPresenter<UserViewModel> {

    @Inject
    public LoadingPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemDetails() {
        getGenericUseCase().executeDynamicGetObject(new ItemDetailSubscriber(), "", Constants.API_BASE_URL + "user/me/", -1,
                User.class, UserRealmModel.class, UserViewModel.class, true);
    }
}
