package com.icaboalo.yana.presentation.screens.settings.food_notifications;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodNotificationsActivity extends BaseActivity {

    public static final int REQUEST_CODE = 11;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void initialize() {
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_food_notifications);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public static Intent getCallingIntent(Context context, boolean foodNotificationActive, boolean breakfastNotificationActive,
                                          boolean lunchNotificationActive, boolean dinnerNotificationActive){
        return new Intent(context, FoodNotificationsActivity.class).putExtra("foodActive", foodNotificationActive)
                .putExtra("breakfastActive", breakfastNotificationActive).putExtra("lunchActive", lunchNotificationActive)
                .putExtra("dinnerActive", dinnerNotificationActive);
    }
}
