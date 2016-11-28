package com.icaboalo.yana.presentation.screens.settings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.settings.food_notifications.FoodNotificationsActivity;

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
    @BindView(R.id.swFoodNotifications)
    SwitchCompat swFoodNotification;
    @BindView(R.id.swDayNotification)
    SwitchCompat swDayNotification;
    @BindView(R.id.swNightNotification)
    SwitchCompat swNightNotification;
    boolean foodNotificationActive, breakfastNotificationActive, lunchNotificationActive, dinnerNotificationActive,
            dayNotificationActive, nightNotificationActive;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mSettingsPresenter.setView(this);
        mSettingsPresenter.initialize(" ");
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

        foodNotificationActive = item.getBoolean(PrefConstants.FOOD_NOTIFICATION_ACTIVE);
        breakfastNotificationActive = item.getBoolean(PrefConstants.BREAKFAST_NOTIFICATION_ACTIVE);
        lunchNotificationActive = item.getBoolean(PrefConstants.LUNCH_NOTIFICATION_ACTIVE);
        dinnerNotificationActive = item.getBoolean(PrefConstants.DINNER_NOTIFICATION_ACTIVE);
        dayNotificationActive = item.getBoolean(PrefConstants.DAY_NOTIFICATION_ACTIVE);
        nightNotificationActive = item.getBoolean(PrefConstants.NIGHT_NOTIFICATION_ACTIVE);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FoodNotificationsActivity.REQUEST_CODE:
                if (data != null) {
                    foodNotificationActive = data.getBooleanExtra("foodNotificationActive", false);
                    breakfastNotificationActive = data.getBooleanExtra("breakfastNotificationActive", false);
                    lunchNotificationActive = data.getBooleanExtra("lunchNotificationActive", false);
                    dinnerNotificationActive = data.getBooleanExtra("dinnerNotificationActive", false);
                    swFoodNotification.setChecked(foodNotificationActive);

                    if (foodNotificationActive) {
                        mSettingsPresenter.attemptUpdateNotificationSetting(PrefConstants.BREAKFAST_NOTIFICATION_ACTIVE,
                                breakfastNotificationActive);
                        mSettingsPresenter.attemptUpdateNotificationSetting(PrefConstants.LUNCH_NOTIFICATION_ACTIVE,
                                lunchNotificationActive);
                        mSettingsPresenter.attemptUpdateNotificationSetting(PrefConstants.DINNER_NOTIFICATION_ACTIVE,
                                dinnerNotificationActive);
                    }
                }
                break;
        }
    }

    @Override
    public void notificationUpdated(String type) {
        Snackbar snackbar = Snackbar.make(rlFoodNotification, "", Snackbar.LENGTH_LONG);
        switch (type) {
            case PrefConstants.FOOD_NOTIFICATION_ACTIVE:
                snackbar.setText("Se actualizaron las notificaciones de comida.");
                break;
            case PrefConstants.DAY_NOTIFICATION_ACTIVE:
                snackbar.setText("Se actualizaron las notificaciones del día.");
                break;
            case PrefConstants.NIGHT_NOTIFICATION_ACTIVE:
                snackbar.setText("Se actualizaron las notificaciones de la noche.");
                break;
        }
        snackbar.show();
    }

    @Override
    public void finalizePlanSuccessful() {
        showError("Plan finalizado con exito");
    }

    @OnCheckedChanged({R.id.swFoodNotifications, R.id.swDayNotification, R.id.swNightNotification})
    void onCheckChanged(CompoundButton button, boolean isChecked) {
        if (button.isPressed())
            switch (button.getId()) {
                case R.id.swFoodNotifications:
                    mSettingsPresenter.attemptUpdateNotificationSetting(PrefConstants.FOOD_NOTIFICATION_ACTIVE, isChecked);
                    foodNotificationActive = isChecked;
                    break;

                case R.id.swDayNotification:
                    mSettingsPresenter.attemptUpdateNotificationSetting(PrefConstants.DAY_NOTIFICATION_ACTIVE, isChecked);
                    dayNotificationActive = isChecked;
                    break;

                case R.id.swNightNotification:
                    mSettingsPresenter.attemptUpdateNotificationSetting(PrefConstants.NIGHT_NOTIFICATION_ACTIVE, isChecked);
                    nightNotificationActive = isChecked;
                    break;
            }
    }

    @OnClick(R.id.tvFinishPlan)
    void showFinishDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Finish plan")
                .setMessage("Mensaje")
                .setPositiveButton("Si", (dialog, which) -> mSettingsPresenter.attemptFinalizePlan())
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss()).create();

        alertDialog.show();
    }

    @OnClick({R.id.rlFoodNotification, R.id.rlDayNotification, R.id.rlNightNotification, R.id.tvFaq, R.id.tvAboutUs, R.id.tvRateUs,
                R.id.tvPrivacyPolicy, R.id.tvFeedback, R.id.tvContact})
    void goToNotificationSettings(View view) {
        switch (view.getId()) {
            case R.id.rlFoodNotification:
                navigator.navigateToForResult(this, FoodNotificationsActivity.getCallingIntent(getApplicationContext(), foodNotificationActive,
                        breakfastNotificationActive, lunchNotificationActive, dinnerNotificationActive),
                        FoodNotificationsActivity.REQUEST_CODE);
                break;

            case R.id.rlDayNotification:
                break;

            case R.id.rlNightNotification:
                break;

            case R.id.tvFaq:
                navigator.navigateTo(getApplicationContext(), new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yana-app.com/faq")));
                break;

            case R.id.tvAboutUs:
                navigator.navigateTo(getApplicationContext(), new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yana-app.com/about")));
                break;

            case R.id.tvRateUs:
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    navigator.navigateTo(getApplicationContext(), new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException notFound) {
                    navigator.navigateTo(getApplicationContext(), new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;

            case R.id.tvPrivacyPolicy:
                navigator.navigateTo(getApplicationContext(), new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yana-app.com/privacy")));
                break;

            case R.id.tvFeedback:
                navigator.navigateTo(getApplicationContext(), new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yana-app.com/feedback")));
                break;

            case R.id.tvContact:
                navigator.navigateTo(getApplicationContext(), new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yana-app.com/contact")));
                break;
        }
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
