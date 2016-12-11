package com.icaboalo.yana.presentation.screens.main.activities;

import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.view_model.ActivityViewModel;
import com.icaboalo.yana.presentation.screens.view_model.DayViewModel;

/**
 * @author icaboalo on 19/08/16.
 */
public interface ActivityView extends GenericDetailView<DayViewModel> {

    void saveEmotionSuccess(ActivityViewModel activityViewModel);
    void saveEmotionError();
}
