package com.icaboalo.yana.presentation.screens.main.progress;

import android.support.v7.widget.RecyclerView;

import com.icaboalo.yana.data.entities.realm_models.action_plan.ActionPlanRealmModel;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.ActionPlan;
import com.icaboalo.yana.presentation.screens.GenericListPresenter;
import com.icaboalo.yana.presentation.screens.main.view_model.ActionPlanViewModel;

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
