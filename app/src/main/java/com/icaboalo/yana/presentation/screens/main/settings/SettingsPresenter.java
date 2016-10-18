package com.icaboalo.yana.presentation.screens.main.settings;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.notification.BreakfastReceiver;
import com.icaboalo.yana.presentation.notification.DinnerReceiver;
import com.icaboalo.yana.presentation.notification.LunchReceiver;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.util.Utils;

import javax.inject.Inject;

/**
 * Created by icaboalo on 12/10/16.
 */

public class SettingsPresenter extends GenericDetailPresenter<Bundle> {

    public final static String FOOD_NOTIFICATION = "foodNotificationActive", DAY_NOTIFICATION = "dayNotificationActive",
            NIGHT_NOTIFICATION = "nightNotificationActive";

    @Inject
    public SettingsPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemDetails() {
        getGenericDetailView().renderItem(new Bundle());
    }

    public void attemptUpdateNotificationSetting(String type, boolean active) {
        hideViewRetry();
        showViewLoading();
        updateNotificationSettings(type, active);
    }

    private void updateNotificationSettings(String type, boolean active) {
        switch (type){
            case FOOD_NOTIFICATION:
                if (active){
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.BREAKFAST_NOTIFICATION), BreakfastReceiver.class,
                            BreakfastReceiver.id, AlarmManager.INTERVAL_DAY);
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.LUNCH_NOTIFIATION), LunchReceiver.class,
                            LunchReceiver.id, AlarmManager.INTERVAL_DAY);
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.DINNER_NOTIFICATION), DinnerReceiver.class,
                            DinnerReceiver.id, AlarmManager.INTERVAL_DAY);
                }
                break;
            case DAY_NOTIFICATION:

                break;
            case NIGHT_NOTIFICATION:

                break;
        }
        getGenericDetailView().getApplicationContext()
                .getSharedPreferences(PrefConstants.NOTIFICATIONS_FILE, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(type, active)
                .apply();
        ((SettingsView) getGenericDetailView()).notificationUpdated(type);
        hideViewLoading();
    }

    private String getNotificationTime(String prefName){
        return getGenericDetailView().getApplicationContext().getSharedPreferences(PrefConstants.NOTIFICATIONS_FILE, Context.MODE_PRIVATE)
                .getString(prefName, "");
    }
}
