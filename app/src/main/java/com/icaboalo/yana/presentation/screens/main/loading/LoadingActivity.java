package com.icaboalo.yana.presentation.screens.main.loading;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.other.ManagerPreference;
import com.icaboalo.yana.other.YanaPreferences;
import com.icaboalo.yana.presentation.factories.SnackbarFactory;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.main.MainActivity;
import com.icaboalo.yana.presentation.view_model.UserViewModel;
import com.icaboalo.yana.util.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author icaboalo on 10/08/16.
 */
public class LoadingActivity extends BaseActivity implements GenericDetailView<UserViewModel> {

    @Inject
    LoadingPresenter mLoadingPresenter;
    @BindView(R.id.rlLoadComplete)
    RelativeLayout rlLoadComplete;
    @BindView(R.id.rlLoading)
    RelativeLayout rlLoading;
    @BindView(R.id.tvTitle)
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
        ManagerPreference.getInstance().set(YanaPreferences.LAST_UPDATE, new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
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
        showSnackbarMessage(SnackbarFactory.TYPE_ERROR, tvTitle, message, Snackbar.LENGTH_SHORT);
    }

    @OnClick(R.id.btContinue)
    void continueToMain(){
        navigator.navigateTo(getApplicationContext(), MainActivity.getCallingIntent(getApplicationContext()));
        finish();
    }

    public static Intent getCallingIntent(Context context){
        return new Intent(context, LoadingActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
