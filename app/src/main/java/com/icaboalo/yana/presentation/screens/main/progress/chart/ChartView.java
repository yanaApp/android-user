package com.icaboalo.yana.presentation.screens.main.progress.chart;

import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.main.view_model.ActionPlanViewModel;

import java.util.ArrayList;

/**
 * Created by icaboalo on 02/10/16.
 */

public interface ChartView extends GenericDetailView<ActionPlanViewModel> {

    void getActionPlan(ActionPlanViewModel actionPlanViewModel);

    void getInfoLists(String[] dayList, float [] averageEmotions);
}
