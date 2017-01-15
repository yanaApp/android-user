package com.icaboalo.yana.presentation.screens.main.progress;

import android.support.v7.widget.RecyclerView;

import com.icaboalo.yana.data.entities.realm_models.action_plan.ActionPlanRealmModel;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.ActionPlan;
import com.icaboalo.yana.presentation.screens.GenericListPresenter;
import com.icaboalo.yana.presentation.component.adapter.ItemInfo;
import com.icaboalo.yana.presentation.screens.view_model.ActionPlanViewModel;
import com.icaboalo.yana.presentation.screens.view_model.ActivityViewModel;
import com.icaboalo.yana.presentation.screens.view_model.DayViewModel;
import com.icaboalo.yana.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by icaboalo on 01/10/16.
 */

public class ProgressPresenter extends GenericListPresenter<ActionPlanViewModel, RecyclerView.ViewHolder> {

    @Inject
    public ProgressPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemList() {
            getGenericUseCase().executeDynamicGetList(new ActionPlanListSubscriber(), "", ActionPlan.class, ActionPlanRealmModel.class,
                    ActionPlanViewModel.class, false);
    }

    public void attemptGetDayInfoList(List<DayViewModel> dayList){
        Collections.sort(dayList, (lhs, rhs) ->
                lhs.getId() - rhs.getId());
        getDayInfoList(dayList);
    }

    private void getDayInfoList(List<DayViewModel> dayViewModelList){
        List<ItemInfo> itemList = new ArrayList<>();
        for (DayViewModel day : dayViewModelList){
            if (day.getDate().equals(Utils.getCurrentDate()))
                break;
            itemList.add(new ItemInfo<>(day, ItemInfo.SECTION_ITEM));
        }
        Collections.sort(itemList, (itemInfo, t1) -> ((DayViewModel) t1.getData()).getId() - ((DayViewModel) itemInfo.getData()).getId());
        ((ProgressView) getGenericListView()).setDayInfoList(itemList);
    }

    public void attemptSendInfoToBreakdown(List<DayViewModel> dayList){
        Collections.sort(dayList, (lhs, rhs) -> lhs.getDayNumber() - rhs.getDayNumber());
        sendInfoToBreakdown(dayList);
    }

    private void sendInfoToBreakdown(List<DayViewModel> dayViewModelList){
        float completedCount = 0, incompleteCount = 0, totalCount, notDoneCount = 0;
        int completedAverage, incompleteAverage, notDoneAverage;
        for (DayViewModel day : dayViewModelList){
            for (ActivityViewModel activity : day.getActivityList()){
                if (day.getDate().equals(Utils.getCurrentDate()))
                    break;
                if (activity.getAnswer() > 0){
                    completedCount ++;
                }
                else if (activity.getAnswer() == 0)
                    incompleteCount ++;
                else
                    notDoneCount ++;
            }
        }
        totalCount = completedCount + incompleteCount + notDoneCount;
        completedAverage = Math.round((completedCount / totalCount) * 100);
        incompleteAverage = Math.round((incompleteCount / totalCount) * 100);
        notDoneAverage = Math.round((notDoneCount / totalCount) * 100);
        ((ProgressView) getGenericListView()).sendInfoToBreakdownSuccessful(completedAverage, incompleteAverage, notDoneAverage);
    }

    public void attemptSendDataToChart(List<DayViewModel> dayViewModelList) {
        sendDataToChart(dayViewModelList);
    }

    private void sendDataToChart(List<DayViewModel> dayViewModelList) {
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
        for (int i = 0; i < averages.size(); i++) {
            averageArray[i] = averages.get(i);
        }

        String[] dayArray = new String[count];
        ((ProgressView) getGenericListView()).sendDataToChartSuccessful(days.toArray(dayArray), averageArray);
    }

    private class ActionPlanListSubscriber extends DefaultSubscriber<List<ActionPlanViewModel>> {
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }

        @Override
        public void onNext(List<ActionPlanViewModel> actionPlanViewModels) {
            getGenericListView().renderItemList(actionPlanViewModels);
        }
    }
}
