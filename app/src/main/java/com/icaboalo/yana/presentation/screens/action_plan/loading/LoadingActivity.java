package com.icaboalo.yana.presentation.screens.action_plan.loading;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.LoadingViewModel;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 10/08/16.
 */
public class LoadingActivity extends BaseActivity implements GenericDetailView<LoadingViewModel> {

    @Inject
    LoadingPresenter mLoadingPresenter;
    @Bind(R.id.rlLoadComplete)
    RelativeLayout rlLoadComplete;
    @Bind(R.id.rlLoading)
    RelativeLayout rlLoading;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mLoadingPresenter.setView(this);
        mLoadingPresenter.initialize(-1);
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
    }

    @Override
    public void renderItem(LoadingViewModel item) {
        showError(item.toString());
        rlLoadComplete.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        if (rlLoading != null)
            rlLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (rlLoading != null)
            rlLoading.setVisibility(View.GONE);
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

    public static Intent getCallingContext(Context context){
        return new Intent(context, LoadingActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
