package com.icaboalo.yana.presentation.screens.action_plan.loading;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.action_plan.main.MainActivity;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.UserViewModel;
import com.icaboalo.yana.util.PrefUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author icaboalo on 10/08/16.
 */
public class LoadingActivity extends BaseActivity implements GenericDetailView<UserViewModel> {

    @Inject
    LoadingPresenter mLoadingPresenter;
    @Bind(R.id.rlLoadComplete)
    RelativeLayout rlLoadComplete;
    @Bind(R.id.rlLoading)
    RelativeLayout rlLoading;
    @Bind(R.id.tvTitle)
    TextView tvTitle;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mLoadingPresenter.setView(this);
        mLoadingPresenter.initialize("");
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
    }

    @Override
    public void renderItem(UserViewModel item) {
        tvTitle.setText(String.format("%s %s", getString(R.string.loading_complete), item.getFullName()));
        rlLoadComplete.setVisibility(View.VISIBLE);
        PrefUtils.setDownloadCompleted(getApplicationContext(), true);
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

    @OnClick(R.id.btContinue)
    void continueToMain(){
        navigator.navigateTo(getApplicationContext(), MainActivity.getCallingIntent(getApplicationContext()));
    }

    public static Intent getCallingIntent(Context context){
        return new Intent(context, LoadingActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
