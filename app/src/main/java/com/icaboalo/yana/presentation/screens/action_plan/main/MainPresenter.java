package com.icaboalo.yana.presentation.screens.action_plan.main;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.UserViewModel;

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

    }
}
