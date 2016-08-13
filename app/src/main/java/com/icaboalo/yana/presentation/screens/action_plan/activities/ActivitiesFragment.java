package com.icaboalo.yana.presentation.screens.action_plan.activities;

import android.content.Context;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.DayViewModel;

import javax.inject.Inject;

/**
 * @author icaboalo on 13/08/16.
 */
public class ActivitiesFragment extends BaseFragment implements GenericDetailView<DayViewModel> {

    @Inject
    ActivitiesPresenter mActivitiesPresenter;

    @Override
    public void initialize() {

    }

    @Override
    public void renderItem(DayViewModel item) {

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

    }

    @Override
    public Context getApplicationContext() {
        return MyApplication.getInstance().getApplicationContext();
    }
}
