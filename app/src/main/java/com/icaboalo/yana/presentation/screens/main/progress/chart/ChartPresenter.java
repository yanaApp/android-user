package com.icaboalo.yana.presentation.screens.main.progress.chart;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.presentation.screens.main.view_model.ActionPlanViewModel;
import com.icaboalo.yana.presentation.screens.main.view_model.ActivityViewModel;
import com.icaboalo.yana.presentation.screens.main.view_model.DayViewModel;
import com.icaboalo.yana.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icaboalo on 01/10/16.
 */

public class ChartPresenter extends GenericDetailPresenter<ActionPlanViewModel> {

    @Inject
    public ChartPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemDetails() {
        ((ChartView) getGenericDetailView()).getInfoLists(new String[]{"1", "2", "3"}, new float[]{1, 3, 1});
    }

    public void attemptGetChartData(List<DayViewModel> dayViewModelList) {
        getChartData(dayViewModelList);
    }

    private void getChartData(List<DayViewModel> dayViewModelList) {
        Collections.sort(dayViewModelList, (lhs, rhs) ->
                lhs.getId() - rhs.getId());
        ArrayList<String> days = new ArrayList<>();
        ArrayList<Float> averages = new ArrayList<>();
        int count = 0;
        for (DayViewModel day : dayViewModelList) {
            if (day.getDate().equals(Utils.getCurrentDate())) {
                break;
            } else {
                days.add(String.valueOf(day.getDayNumber()));
                int answerCount = 0;
                int answerTotal = 0;
                for (ActivityViewModel activity : day.getActivityList()) {
                    if (activity.getAnswer() >= 0) {
                        answerTotal += activity.getAnswer();
                        answerCount++;
                    }
                }
                if (answerTotal == 0 || answerCount == 0)
                    averages.add(0f);
                else
                    averages.add((float) answerTotal / (float) answerCount);
                count++;
            }
        }
        float[] averageArray = new float[averages.size()];
        String[] dayArray = new String[count];
        for (int i = 0; i < averages.size(); i++) {
            averageArray[i] = averages.get(i);
        }
        ((ChartView) getGenericDetailView()).getInfoLists(days.toArray(dayArray), averageArray);
    }

}
