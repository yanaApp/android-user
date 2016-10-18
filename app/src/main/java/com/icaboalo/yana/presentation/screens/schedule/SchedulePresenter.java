package com.icaboalo.yana.presentation.screens.schedule;

import android.content.Context;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericPostPresenter;
import com.icaboalo.yana.presentation.screens.schedule.view_model.ScheduleViewModel;
import com.icaboalo.yana.util.Constants;

import java.util.HashMap;

import javax.inject.Inject;

import static com.icaboalo.yana.PrefConstants.NOTIFICATIONS_FILE;

/**
 * @author icaboalo on 07/09/16.
 */
public class SchedulePresenter extends GenericPostPresenter<ScheduleViewModel> {

    private int mId;

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

    private void saveNotificationTime(String prefName, String hour){
        getGenericPostView().getApplicationContext().getSharedPreferences(NOTIFICATIONS_FILE, Context.MODE_PRIVATE)
                .edit()
                .putString(prefName, hour)
                .apply();
    }
}
