package com.icaboalo.yana.presentation.screens.evaluation;

import com.icaboalo.yana.data.entities.realm_models.action_plan.ActionPlanRealmModel;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.ActionPlan;
import com.icaboalo.yana.presentation.screens.GenericPostPresenter;
import com.icaboalo.yana.presentation.screens.view_model.ActionPlanViewModel;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by icaboalo on 08/11/16.
 */

public class EvaluationPresenter extends GenericPostPresenter<ActionPlanViewModel> {

    @Inject
    public EvaluationPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void post(HashMap<String, Object> postBundle) {
        hideViewRetry();
        showViewLoading();
        getGenericUseCase().executeDynamicPostObject(new PostSubscriber(), "plan/new/", postBundle, ActionPlan.class,
                ActionPlanRealmModel.class, ActionPlanViewModel.class, false);
    }
}
