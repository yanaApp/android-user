package com.icaboalo.yana.presentation.screens.settings.food_notifications;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class FoodNotificationsActivity extends BaseActivity {

    public static final int REQUEST_CODE = 11;
    private static boolean foodActive, breakfastActive, lunchActive, dinnerActive;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swFoodNotifications)
    Switch swFoodNotifications;
    @BindView(R.id.swBreakfastNotification)
    SwitchCompat swBreakfastNotification;
    @BindView(R.id.swLunchNotification)
    SwitchCompat swLunchNotification;
    @BindView(R.id.swDinnerNotification)
    SwitchCompat swDinnerNotification;
    @BindView(R.id.tvFoodNotifications)
    TextView tvFoodNotifications;
    @BindView(R.id.tvBreakfast)
    TextView tvBreakfast;
    @BindView(R.id.tvLunch)
    TextView tvLunch;
    @BindView(R.id.tvDinner)
    TextView tvDinner;

    @Override
    public void initialize() {
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_food_notifications);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swFoodNotifications.setChecked(foodActive);
        swBreakfastNotification.setChecked(breakfastActive);
        swLunchNotification.setChecked(lunchActive);
        swDinnerNotification.setChecked(dinnerActive);
        setEnabled(foodActive);

        if (foodActive)
            tvFoodNotifications.setText("On");
        else
            tvFoodNotifications.setText("Off");
    }

    @Override
    public void onBackPressed() {
        Intent resultData = new Intent();
        resultData.putExtra("foodNotificationActive", foodActive);
        resultData.putExtra("breakfastNotificationActive", breakfastActive);
        resultData.putExtra("lunchNotificationActive", lunchActive);
        resultData.putExtra("dinnerNotificationActive", dinnerActive);

        setResult(REQUEST_CODE, resultData);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent resultData = new Intent();
                resultData.putExtra("foodNotificationActive", foodActive);
                resultData.putExtra("breakfastNotificationActive", breakfastActive);
                resultData.putExtra("lunchNotificationActive", lunchActive);
                resultData.putExtra("dinnerNotificationActive", dinnerActive);

                setResult(REQUEST_CODE, resultData);
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnCheckedChanged({R.id.swFoodNotifications, R.id.swBreakfastNotification, R.id.swLunchNotification, R.id.swDinnerNotification})
    void onCheckChanged(CompoundButton compoundButton, boolean checked) {
        switch (compoundButton.getId()) {
            case R.id.swFoodNotifications:
                foodActive = checked;
                setEnabled(checked);
                if (checked)
                    tvFoodNotifications.setText("On");
                else
                    tvFoodNotifications.setText("Off");
                break;
            case R.id.swBreakfastNotification:
                breakfastActive = checked;
                break;
            case R.id.swLunchNotification:
                lunchActive = checked;
                break;
            case R.id.swDinnerNotification:
                dinnerActive = checked;
                break;
        }
    }

    public static Intent getCallingIntent(Context context, boolean foodNotificationActive, boolean breakfastNotificationActive,
                                          boolean lunchNotificationActive, boolean dinnerNotificationActive) {
        foodActive = foodNotificationActive;
        breakfastActive = breakfastNotificationActive;
        lunchActive = lunchNotificationActive;
        dinnerActive = dinnerNotificationActive;
        return new Intent(context, FoodNotificationsActivity.class);
    }

    private void setEnabled(boolean enabled){
        swBreakfastNotification.setEnabled(enabled);
        swLunchNotification.setEnabled(enabled);
        swDinnerNotification.setEnabled(enabled);

        int textColor = enabled ? getResources().getColor(R.color.primary_text) : getResources().getColor(R.color.secondary_text);
        tvBreakfast.setTextColor(textColor);
        tvLunch.setTextColor(textColor);
        tvDinner.setTextColor(textColor);
    }
}
