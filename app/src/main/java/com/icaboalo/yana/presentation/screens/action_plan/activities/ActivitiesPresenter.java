package com.icaboalo.yana.presentation.screens.action_plan.activities;

import com.icaboalo.yana.data.entities.realm_models.action_plan.DayRealmModel;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.Day;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.DayViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;

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
//        getGenericUseCase().executeSearchRealmObject(new ItemDetailSubscriber(), mRealmQuery, Day.class, DayRealmModel.class,
//                DayViewModel.class);
        getGenericUseCase().executeDynamicGetObject(new ItemDetailSubscriber(), "date", "", currentDate, Day.class,
                DayRealmModel.class, DayViewModel.class, false);
    }
}
