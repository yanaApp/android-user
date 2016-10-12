package com.icaboalo.yana.presentation.screens.main.settings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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

    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
