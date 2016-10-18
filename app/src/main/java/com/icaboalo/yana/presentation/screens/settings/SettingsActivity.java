package com.icaboalo.yana.presentation.screens.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements SettingsView {

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
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void renderItem(Bundle item) {
        hideLoading();
        hideRetry();
        swFoodNotification.setChecked(item.getBoolean(PrefConstants.FOOD_NOTIFICATION_ACTIVE));
        swDayNotification.setChecked(item.getBoolean(PrefConstants.DAY_NOTIFICATION_ACTIVE));
        swNightNotification.setChecked(item.getBoolean(PrefConstants.NIGHT_NOTIFICATION_ACTIVE));
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
    void onCheckChanged(CompoundButton button, boolean isChecked) {
        switch (button.getId()) {
            case R.id.swFoodNotification:
                mSettingsPresenter.attemptUpdateNotificationSetting(PrefConstants.FOOD_NOTIFICATION_ACTIVE, isChecked);
                break;
            case R.id.swDayNotification:
                mSettingsPresenter.attemptUpdateNotificationSetting(PrefConstants.DAY_NOTIFICATION_ACTIVE, isChecked);
                break;
            case R.id.swNightNotification:
                mSettingsPresenter.attemptUpdateNotificationSetting(PrefConstants.NIGHT_NOTIFICATION_ACTIVE, isChecked);
                break;
        }
    }

    @OnClick(R.id.tvFinishPlan)
    void showFinishDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Finish plan")
                .setMessage("")
                .setPositiveButton("Si", (dialog, which) -> {

                })
                .setNegativeButton("Cancelar", (dialog, which) -> {

                }).create();

        alertDialog.show();
    }

    @OnClick({R.id.rlFoodNotification, R.id.rlDayNotification, R.id.rlNightNotification})
    void goToNotificationSettings(View view){
        switch (view.getId()){
            case R.id.rlFoodNotification:
                break;
            case R.id.rlDayNotification:
                break;
            case R.id.rlNightNotification:
                break;
        }
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void notificationUpdated(String type) {

    }
}
