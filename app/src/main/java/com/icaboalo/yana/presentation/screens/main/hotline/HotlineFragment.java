package com.icaboalo.yana.presentation.screens.main.hotline;

import android.content.Context;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.GenericDetailView;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by icaboalo on 28/09/16.
 */

public class HotlineFragment extends BaseFragment implements GenericDetailView<Object> {

    @Inject
    HotlinePresenter mHotlinePresenter;

    @Override
    public void initialize() {
        getComponent(UserComponent.class).inject(this);
        mHotlinePresenter.setView(this);
    }


    @Override
    public void renderItem(Object item) {

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
