package com.icaboalo.yana.presentation.screens.action_plan.activities;

import android.content.Context;
import android.util.Log;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.data.entities.realm_models.action_plan.DayRealmModel;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.DayViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * @author icaboalo on 13/08/16.
 */
public class ActivitiesFragment extends BaseFragment implements GenericDetailView<DayViewModel> {

    @Inject
    ActivitiesPresenter mActivitiesPresenter;

    @Override
    public void initialize() {
        getComponent(UserComponent.class).inject(this);
        mActivitiesPresenter.setView(this);
        mActivitiesPresenter.initialize("");
    }

    @Override
    public void renderItem(DayViewModel item) {
        Calendar calendar = Calendar.getInstance();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
        DayRealmModel day =  Realm.getDefaultInstance().where(DayRealmModel.class).equalTo("date", currentDate).findFirst();
        Log.d("ITEM", day.getDate());
        showError(item.getDate());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    @Override
    public Context getApplicationContext() {
        return MyApplication.getInstance().getApplicationContext();
    }
}
