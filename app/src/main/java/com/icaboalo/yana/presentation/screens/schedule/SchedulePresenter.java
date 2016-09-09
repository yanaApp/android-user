package com.icaboalo.yana.presentation.screens.schedule;

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

    @Inject
    public SchedulePresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void post(HashMap<String, Object> postBundle) {
        showViewLoading();
        getGenericUseCase().executeDynamicPostObject(new PostSubscriber(), Constants.API_BASE_URL + "schedule/", postBundle,
                Object.class, Object.class, ScheduleViewModel.class, true);
    }
}
