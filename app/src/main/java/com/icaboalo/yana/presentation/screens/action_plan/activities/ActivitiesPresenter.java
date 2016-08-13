package com.icaboalo.yana.presentation.screens.action_plan.activities;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.DayViewModel;

import javax.inject.Inject;

/**
 * @author icaboalo on 13/08/16.
 */
public class ActivitiesPresenter extends GenericDetailPresenter<DayViewModel> {

    @Inject
    public ActivitiesPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemDetails() {

    }
}
