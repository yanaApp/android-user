package com.icaboalo.yana.presentation.screens.main.progress;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.icaboalo.yana.data.entities.realm_models.action_plan.ActionPlanRealmModel;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.ActionPlan;
import com.icaboalo.yana.presentation.screens.GenericListPresenter;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemInfo;
import com.icaboalo.yana.presentation.screens.main.view_model.ActionPlanViewModel;
import com.icaboalo.yana.presentation.screens.main.view_model.DayViewModel;
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
            itemList.add(new ItemInfo<>(day, ItemInfo.SECTION_ITEM));
            if (day.getDate().equals(Utils.getCurrentDate()))
                break;
            else {
                Log.d("id", String.valueOf(day.getId()));
                Log.d("date", day.getDate() + " -- " + Utils.getCurrentDate());
            }

        }
        Collections.sort(itemList, (itemInfo, t1) -> ((DayViewModel) t1.getData()).getId() - ((DayViewModel) itemInfo.getData()).getId());
        ((ProgressView) getGenericListView()).setDayInfoList(itemList);
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
