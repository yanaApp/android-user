package com.icaboalo.yana.presentation.screens.main.activities;

import com.icaboalo.yana.data.entities.realm_models.action_plan.ActivityRealmModel;
import com.icaboalo.yana.data.entities.realm_models.action_plan.DayRealmModel;
import com.icaboalo.yana.domain.exception.DefaultErrorBundle;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.Activity;
import com.icaboalo.yana.domain.models.action_plan.Day;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.presentation.view_model.ActivityViewModel;
import com.icaboalo.yana.presentation.view_model.DayViewModel;
import com.icaboalo.yana.util.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.inject.Inject;

/**
 * @author icaboalo on 13/08/16.
 */
public class ActivitiesPresenter extends GenericDetailPresenter<DayViewModel> {

    @Inject
    public ActivitiesPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemDetails() {
        Calendar calendar = Calendar.getInstance();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
        getGenericUseCase().executeDynamicGetObject(new ItemDetailSubscriber(), "date", "", currentDate, Day.class,
                DayRealmModel.class, DayViewModel.class, false);
    }

    public void attemptSaveEmotion(ActivityViewModel activityViewModel, int answer){
        showViewLoading();
        HashMap<String, Object> emotionBundle = new HashMap<>();
        emotionBundle.put("answer", answer);
        saveEmotion(emotionBundle, activityViewModel.getId());
    }

    private void saveEmotion(HashMap<String, Object> emotionBundle, int activityId){
        getGenericUseCase().executeDynamicPatchObject(new SaveEmotionSubscriber(),
                Constants.API_BASE_URL + "activity/" + activityId + "/", emotionBundle, Activity.class, ActivityRealmModel.class,
                ActivityViewModel.class, true);
    }

    private void saveSuccess(ActivityViewModel activityViewModel){
        ((ActivityView) getGenericDetailView()).saveEmotionSuccess(activityViewModel);
    }

    private class SaveEmotionSubscriber extends DefaultSubscriber<ActivityViewModel>{

        @Override
        public void onCompleted() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            hideViewLoading();
            showErrorMessage(new DefaultErrorBundle((Exception) e));
            ((ActivityView) getGenericDetailView()).saveEmotionError();
        }

        @Override
        public void onNext(ActivityViewModel activityViewModel) {
            saveSuccess(activityViewModel);
        }
    }
}
