package com.icaboalo.yana.presentation.screens.main.settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.presentation.screens.main.MainActivity;

import javax.inject.Inject;

import static com.icaboalo.yana.presentation.notification.DinnerReceiver.id;

/**
 * Created by icaboalo on 12/10/16.
 */

public class SettingsPresenter extends GenericDetailPresenter<Bundle> {

    public final static String foodNotification = "foodNotificationActive", dayNotification = "dayNotificationActive",
            nightNotification = "nightNotificationActive";

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
            case foodNotification:
                Intent intent = new Intent(getGenericDetailView().getApplicationContext(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getGenericDetailView().getApplicationContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                break;
            case dayNotification:

                break;
            case nightNotification:

                break;
        }
        getGenericDetailView().getApplicationContext()
                .getSharedPreferences("notification", Context.MODE_PRIVATE)
                .edit()
                .putBoolean(type, active)
                .apply();
        ((SettingsView) getGenericDetailView()).notificationUpdated(type);
        hideViewLoading();
    }
}
