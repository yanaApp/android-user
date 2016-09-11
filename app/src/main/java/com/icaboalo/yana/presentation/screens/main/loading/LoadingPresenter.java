package com.icaboalo.yana.presentation.screens.main.loading;

import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.User;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.presentation.screens.main.view_model.UserViewModel;
import com.icaboalo.yana.util.Constants;
import com.icaboalo.yana.util.PrefUtils;

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
        getGenericUseCase().executeDynamicGetObject(new ItemDetailSubscriber(), "", Constants.API_BASE_URL + "user/me/", "",
                User.class, UserRealmModel.class, UserViewModel.class, true);
    }

    @Override
    public void getSuccessful(UserViewModel userViewModel) {
        PrefUtils.setUserId(getGenericDetailView().getApplicationContext(), userViewModel.getId());
        PrefUtils.setUserEmail(getGenericDetailView().getApplicationContext(), userViewModel.getEmail());
//        PrefUtils.setScheduleId(getGenericDetailView().getApplicationContext(), userViewModel.getSchedule.getId);
        super.getSuccessful(userViewModel);
    }
}
