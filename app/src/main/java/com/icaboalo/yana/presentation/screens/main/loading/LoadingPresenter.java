package com.icaboalo.yana.presentation.screens.main.loading;

import android.app.AlarmManager;

import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.domain.models.action_plan.User;
import com.icaboalo.yana.other.ManagerPreference;
import com.icaboalo.yana.other.YanaPreferences;
import com.icaboalo.yana.presentation.notification.BreakfastReceiver;
import com.icaboalo.yana.presentation.notification.DinnerReceiver;
import com.icaboalo.yana.presentation.notification.LunchReceiver;
import com.icaboalo.yana.presentation.notification.SleepReceiver;
import com.icaboalo.yana.presentation.notification.WakeUpReceiver;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;
import com.icaboalo.yana.presentation.screens.view_model.UserViewModel;
import com.icaboalo.yana.presentation.screens.view_model.ScheduleViewModel;
import com.icaboalo.yana.util.Constants;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.Utils;

import javax.inject.Inject;

/**
 * @author icaboalo on 10/08/16.
 */
public class LoadingPresenter extends GenericDetailPresenter<UserViewModel> {

    @Inject
    public LoadingPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemDetails() {
        getGenericUseCase().executeDynamicGetObject(new ItemDetailSubscriber(), "", Constants.API_BASE_URL + "user/me/", "",
                User.class, UserRealmModel.class, UserViewModel.class, true);
    }

    @Override
    public void getSuccessful(UserViewModel userViewModel) {
        PrefUtils.setUserId(getGenericDetailView().getApplicationContext(), userViewModel.getId());
        PrefUtils.setUserEmail(getGenericDetailView().getApplicationContext(), userViewModel.getEmail());
//        PrefUtils.setScheduleId(getGenericDetailView().getApplicationContext(), userViewModel.getSchedule.getId);
        ScheduleViewModel schedule = userViewModel.getSchedule();

        if (ManagerPreference.getInstance().getBoolean(YanaPreferences.NOTIFICATIONS_SET)) {
            ManagerPreference.getInstance().set(YanaPreferences.BREAKFAST_NOTIFICATION_TIME, schedule.getBreakfastTime());
            ManagerPreference.getInstance().set(YanaPreferences.LUNCH_NOTIFICATION_TIME, schedule.getLunchTime());
            ManagerPreference.getInstance().set(YanaPreferences.DINNER_NOTIFICATION_TIME, schedule.getDinnerTime());

            ManagerPreference.getInstance().set(YanaPreferences.DAY_NOTIFICATION_TIME, schedule.getWakeUpTime());
            if (ManagerPreference.getInstance().getBoolean(YanaPreferences.DAY_NOTIFICATION_ACTIVE))
                createNotification(schedule.getWakeUpTime(), WakeUpReceiver.class, WakeUpReceiver.id);

            ManagerPreference.getInstance().set(YanaPreferences.NIGHT_NOTIFICATION_TIME, schedule.getSleepTime());
            if (ManagerPreference.getInstance().getBoolean(YanaPreferences.NIGHT_NOTIFICATION_ACTIVE))
                createNotification(schedule.getSleepTime(), SleepReceiver.class, SleepReceiver.id);


            if (ManagerPreference.getInstance().getBoolean(YanaPreferences.FOOD_NOTIFICATION_ACTIVE)) {
                if (ManagerPreference.getInstance().getBoolean(YanaPreferences.BREAKFAST_NOTIFICATION_ACTIVE))
                    createNotification(schedule.getBreakfastTime(), BreakfastReceiver.class, BreakfastReceiver.id);

                if (ManagerPreference.getInstance().getBoolean(YanaPreferences.LUNCH_NOTIFICATION_ACTIVE))
                    createNotification(schedule.getLunchTime(), LunchReceiver.class, LunchReceiver.id);

                if (ManagerPreference.getInstance().getBoolean(YanaPreferences.DINNER_NOTIFICATION_ACTIVE))
                    createNotification(schedule.getDinnerTime(), DinnerReceiver.class, DinnerReceiver.id);
            }

            ManagerPreference.getInstance().set(YanaPreferences.NOTIFICATIONS_SET, true);
        }

        super.getSuccessful(userViewModel);
    }

    private void createNotification(String time, Class broadcastReceiver, int id) {
        Utils.createNotification(getGenericDetailView().getApplicationContext(), time, broadcastReceiver, id, AlarmManager.INTERVAL_DAY);
    }
}
