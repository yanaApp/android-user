package com.icaboalo.yana.presentation.screens.main.settings;

import android.content.Context;
import android.os.Bundle;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;

import javax.inject.Inject;

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
        updateNotificationSettings(type, active);
    }

    private void updateNotificationSettings(String type, boolean active) {
        getGenericDetailView().getApplicationContext()
                .getSharedPreferences("", Context.MODE_PRIVATE)
                .edit()
                .putBoolean(type, active)
                .apply();
    }
}
