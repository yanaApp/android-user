package com.icaboalo.yana.presentation.screens.action_plan.activities;

import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.ActivityViewModel;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.DayViewModel;

/**
 * @author icaboalo on 19/08/16.
 */
public interface ActivityView extends GenericDetailView<DayViewModel> {

    void saveEmotionSuccess(ActivityViewModel activityViewModel);
}
