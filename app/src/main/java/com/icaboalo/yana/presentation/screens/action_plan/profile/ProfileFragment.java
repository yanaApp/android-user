package com.icaboalo.yana.presentation.screens.action_plan.profile;

import android.content.Context;

import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.UserViewModel;

/**
 * @author icaboalo on 24/08/16.
 */
public class ProfileFragment extends BaseFragment implements GenericDetailView<UserViewModel> {


    @Override
    public void initialize() {
        getComponent(UserComponent.class).inject(this);
    }

    @Override
    public void renderItem(UserViewModel item) {

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
        return null;
    }
}
