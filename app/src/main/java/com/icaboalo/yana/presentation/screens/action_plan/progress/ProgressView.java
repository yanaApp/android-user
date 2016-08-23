package com.icaboalo.yana.presentation.screens.action_plan.progress;

import android.support.v7.widget.RecyclerView;

import com.icaboalo.yana.presentation.screens.GenericListView;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.ActionPlanViewModel;

/**
 * @author icaboalo on 23/08/16.
 */
public interface ProgressView extends GenericListView<ActionPlanViewModel, RecyclerView.ViewHolder> {

    void setActivitiesAverage(int completedActivitiesAverage, int incompleteActivitiesAverage, int emotionAverage);
}
