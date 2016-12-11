package com.icaboalo.yana.presentation.screens.main.progress.plan_breakdown;

import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.view_model.ActionPlanViewModel;

/**
 * @author icaboalo on 23/08/16.
 */
public interface PlanBreakdownView extends GenericDetailView<ActionPlanViewModel> {

    void setActivitiesAverage(int completedActivitiesAverage, int incompleteActivitiesAverage, int notDoneActivitiesAverage);
}
