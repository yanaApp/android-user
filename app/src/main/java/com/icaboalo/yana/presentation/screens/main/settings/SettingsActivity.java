package com.icaboalo.yana.presentation.screens.main.settings;

import android.content.Context;
import android.content.Intent;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    public void initialize() {

    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_settings);
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
