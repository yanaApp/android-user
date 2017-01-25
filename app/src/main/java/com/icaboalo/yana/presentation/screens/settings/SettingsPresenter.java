package com.icaboalo.yana.presentation.screens.settings;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.data.entities.realm_models.action_plan.ActionPlanRealmModel;
import com.icaboalo.yana.domain.interactors.DefaultSubscriber;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.ActionPlan;
import com.icaboalo.yana.presentation.notification.BreakfastReceiver;
import com.icaboalo.yana.presentation.notification.DinnerReceiver;
import com.icaboalo.yana.presentation.notification.LunchReceiver;
import com.icaboalo.yana.presentation.notification.SleepReceiver;
import com.icaboalo.yana.presentation.notification.WakeUpReceiver;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.presentation.view_model.ActionPlanViewModel;
import com.icaboalo.yana.util.Constants;
import com.icaboalo.yana.util.Utils;

import java.util.HashMap;

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
        switch (type) {
            case PrefConstants.FOOD_NOTIFICATION_ACTIVE:
                if (active) {
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.BREAKFAST_NOTIFICATION_TIME), BreakfastReceiver.class,
                            BreakfastReceiver.id, AlarmManager.INTERVAL_DAY);
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.LUNCH_NOTIFICATION_TIME), LunchReceiver.class,
                            LunchReceiver.id, AlarmManager.INTERVAL_DAY);
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.DINNER_NOTIFICATION_TIME), DinnerReceiver.class,
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
                            getNotificationTime(PrefConstants.DAY_NOTIFICATION_TIME), WakeUpReceiver.class,
                            WakeUpReceiver.id, AlarmManager.INTERVAL_DAY);
                else
                    Utils.deleteNotification(getGenericDetailView().getApplicationContext(), WakeUpReceiver.class, WakeUpReceiver.id);
                break;

            case PrefConstants.NIGHT_NOTIFICATION_ACTIVE:
                if (active)
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.NIGHT_NOTIFICATION_TIME), SleepReceiver.class,
                            SleepReceiver.id, AlarmManager.INTERVAL_DAY);
                else
                    Utils.deleteNotification(getGenericDetailView().getApplicationContext(), SleepReceiver.class, SleepReceiver.id);
                break;

            case PrefConstants.BREAKFAST_NOTIFICATION_ACTIVE:
                if (active)
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.BREAKFAST_NOTIFICATION_TIME), BreakfastReceiver.class, BreakfastReceiver.id,
                            AlarmManager.INTERVAL_DAY);
                else
                    Utils.deleteNotification(getGenericDetailView().getApplicationContext(), BreakfastReceiver.class,
                            BreakfastReceiver.id);
                break;

            case PrefConstants.LUNCH_NOTIFICATION_ACTIVE:
                if (active)
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.LUNCH_NOTIFICATION_TIME), LunchReceiver.class, LunchReceiver.id,
                            AlarmManager.INTERVAL_DAY);
                else
                    Utils.deleteNotification(getGenericDetailView().getApplicationContext(), LunchReceiver.class,
                            LunchReceiver.id);
                break;

            case PrefConstants.DINNER_NOTIFICATION_ACTIVE:
                if (active)
                    Utils.createNotification(getGenericDetailView().getApplicationContext(),
                            getNotificationTime(PrefConstants.DINNER_NOTIFICATION_TIME), DinnerReceiver.class, DinnerReceiver.id,
                            AlarmManager.INTERVAL_DAY);
                else
                    Utils.deleteNotification(getGenericDetailView().getApplicationContext(), DinnerReceiver.class,
                            DinnerReceiver.id);
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

    public void attemptFinalizePlan() {
        finalizePlan();
    }

    private void finalizePlan() {
        hideViewRetry();
        showViewLoading();
        getGenericUseCase().executeDynamicPostObject(new FinalizePlanSubscriber(), Constants.API_BASE_URL + "plan/finalize/",
                new HashMap<>(), ActionPlan.class, ActionPlanRealmModel.class, ActionPlanViewModel.class, true);
    }

    private String getNotificationTime(String prefName) {
        return getGenericDetailView().getApplicationContext().getSharedPreferences(PrefConstants.NOTIFICATIONS_FILE, Context.MODE_PRIVATE)
                .getString(prefName, "");
    }

    class FinalizePlanSubscriber extends DefaultSubscriber<ActionPlanViewModel> {
        @Override
        public void onCompleted() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            showViewRetry();
            hideViewLoading();
            getGenericDetailView().showError(e.getMessage());
        }

        @Override
        public void onNext(ActionPlanViewModel actionPlanViewModel) {
            ((SettingsView) getGenericDetailView()).finalizePlanSuccessful();
        }
    }
}
