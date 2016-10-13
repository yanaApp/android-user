package com.icaboalo.yana.presentation.screens.main.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SettingsActivity extends BaseActivity implements SettingsView{

    @Inject
    SettingsPresenter mSettingsPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rlProgress)
    RelativeLayout rlProgress;
    @BindView(R.id.rlRetry)
    RelativeLayout rlRetry;
    @BindView(R.id.rlFoodNotification)
    RelativeLayout rlFoodNotification;
    @BindView(R.id.rlDayNotification)
    RelativeLayout rlDayNotification;
    @BindView(R.id.rlNightNotification)
    RelativeLayout rlNightNotification;
    @BindView(R.id.swFoodNotification)
    SwitchCompat swFoodNotification;
    @BindView(R.id.swDayNotification)
    SwitchCompat swDayNotification;
    @BindView(R.id.swNightNotification)
    SwitchCompat swNightNotification;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mSettingsPresenter.setView(this);
        mSettingsPresenter.initialize("");
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public void renderItem(Bundle item) {
        hideLoading();
        hideRetry();
        swFoodNotification.setChecked(item.getBoolean("foodNotificationEnabled", true));
        swDayNotification.setChecked(item.getBoolean("dayNotificationEnabled", true));
        swNightNotification.setChecked(item.getBoolean("nightNotificationEnabled", true));
    }

    @Override
    public void showLoading() {
        if (rlProgress != null)
            rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (rlProgress != null)
            rlProgress.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
        if (rlRetry != null)
            rlRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        if (rlRetry != null)
            rlRetry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    @OnCheckedChanged({R.id.swFoodNotification, R.id.swDayNotification, R.id.swNightNotification})
    void onCheckChanged(CompoundButton button, boolean isChecked){
        switch (button.getId()){
            case R.id.swFoodNotification:
                mSettingsPresenter.attemptUpdateNotificationSetting(SettingsPresenter.foodNotification, isChecked);
                break;
            case R.id.swDayNotification:
                mSettingsPresenter.attemptUpdateNotificationSetting(SettingsPresenter.dayNotification, isChecked);
                break;
            case R.id.swNightNotification:
                mSettingsPresenter.attemptUpdateNotificationSetting(SettingsPresenter.nightNotification, isChecked);
                break;
        }
    }


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
