package com.icaboalo.yana.presentation.screens.schedule;

import android.content.Context;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericPostPresenter;
import com.icaboalo.yana.presentation.screens.schedule.view_model.ScheduleViewModel;
import com.icaboalo.yana.util.Constants;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * @author icaboalo on 07/09/16.
 */
public class SchedulePresenter extends GenericPostPresenter<ScheduleViewModel> {

    private int mId;
    public static String wakeUpNotification = "wakeUpNotificationTime", sleepNotification = "sleepNotificationTime",
            breakfastNotification = "breakfastNotificationTime", lunchNotification = "lunchNotificationTime",
            dinnerNotification = "dinnerNotificationTime";

    @Inject
    public SchedulePresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void post(HashMap<String, Object> postBundle) {
        showViewLoading();
        getGenericUseCase().executeDynamicPatchObject(new PostSubscriber(), Constants.API_BASE_URL + "schedule/" + mId + "/", postBundle,
                Object.class, Object.class, ScheduleViewModel.class, true);
    }

    public void setId(int id) {
        mId = id;
    }

    public void attemptSaveNotificationTime(String type, String hour){
        saveNotificationTime(type, hour);
    }

    private void saveNotificationTime(String type, String hour){
        getGenericPostView().getApplicationContext().getSharedPreferences("notifications", Context.MODE_PRIVATE)
                .edit()
                .putString(type, hour)
                .apply();
    }
}
