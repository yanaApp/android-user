package com.icaboalo.yana.presentation.screens.settings;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.notification.BreakfastReceiver;
import com.icaboalo.yana.presentation.notification.DinnerReceiver;
import com.icaboalo.yana.presentation.notification.LunchReceiver;
import com.icaboalo.yana.presentation.notification.SleepReceiver;
import com.icaboalo.yana.presentation.notification.WakeUpReceiver;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.util.Utils;

import javax.inject.Inject;

/**
 * Created by icaboalo on 12/10/16.
 */

public class SettingsPresenter extends GenericDetailPresenter<Bundle> {

    @Inject
    public SettingsPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemDetails() {
        SharedPreferences sharedPreferences = getGenericDetailView().getApplicationContext().getSharedPreferences(PrefConstants.NOTIFICATIONS_FILE, Context.MODE_PRIVATE);
        Bundle bundle = new Bundle();
        bundle.putBoolean(PrefConstants.FOOD_NOTIFICATION_ACTIVE, sharedPreferences.getBoolean(PrefConstants.FOOD_NOTIFICATION_ACTIVE, true));
        bundle.putBoolean(PrefConstants.DAY_NOTIFICATION_ACTIVE, sharedPreferences.getBoolean(PrefConstants.DAY_NOTIFICATION_ACTIVE, true));
        bundle.putBoolean(PrefConstants.NIGHT_NOTIFICATION_ACTIVE, sharedPreferences.getBoolean(PrefConstants.NIGHT_NOTIFICATION_ACTIVE, true));
        bundle.putBoolean(PrefConstants.BREAKFAST_NOTIFICATION_ACTIVE, sharedPreferences.getBoolean(PrefConstants.BREAKFAST_NOTIFICATION_ACTIVE, true));
        bundle.putBoolean(PrefConstants.LUNCH_NOTIFICATION_ACTIVE, sharedPreferences.getBoolean(PrefConstants.LUNCH_NOTIFICATION_ACTIVE, true));
        bundle.putBoolean(PrefConstants.DINNER_NOTIFICATION_ACTIVE, sharedPreferences.getBoolean(PrefConstants.DINNER_NOTIFICATION_ACTIVE, true));
        getGenericDetailView().renderItem(bundle);
    }

    public void attemptUpdateNotificationSetting(String type, boolean active) {
        hideViewRetry();
        showViewLoading();
        updateNotificationSettings(type, active);
    }

    private void updateNotificationSettings(String type, boolean active) {
        switch (type){
            case PrefConstants.FOOD_NOTIFICATION_ACTIVE:
                if (active){
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.BREAKFAST_NOTIFICATION), BreakfastReceiver.class,
                            BreakfastReceiver.id, AlarmManager.INTERVAL_DAY);
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.LUNCH_NOTIFICATION), LunchReceiver.class,
                            LunchReceiver.id, AlarmManager.INTERVAL_DAY);
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.DINNER_NOTIFICATION), DinnerReceiver.class,
                            DinnerReceiver.id, AlarmManager.INTERVAL_DAY);
                } else {
                    Utils.deleteNotification(getGenericDetailView().getApplicationContext(),
                            BreakfastReceiver.class, BreakfastReceiver.id);
                    Utils.deleteNotification(getGenericDetailView().getApplicationContext(),
                            LunchReceiver.class, LunchReceiver.id);
                    Utils.deleteNotification(getGenericDetailView().getApplicationContext(),
                            DinnerReceiver.class, DinnerReceiver.id);
                }
                break;
            case PrefConstants.DAY_NOTIFICATION_ACTIVE:
                if (active)
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.WAKE_UP_NOTIFICATION), WakeUpReceiver.class,
                            WakeUpReceiver.id, AlarmManager.INTERVAL_DAY);
                else
                    Utils.deleteNotification(getGenericDetailView().getApplicationContext(), WakeUpReceiver.class, WakeUpReceiver.id);
                break;
            case PrefConstants.NIGHT_NOTIFICATION_ACTIVE:
                if (active)
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.SLEEP_NOTIFICATION), SleepReceiver.class,
                            SleepReceiver.id, AlarmManager.INTERVAL_DAY);
                else
                    Utils.deleteNotification(getGenericDetailView().getApplicationContext(), SleepReceiver.class, SleepReceiver.id);
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
