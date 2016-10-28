package com.icaboalo.yana.other;

import com.icaboalo.yana.PrefConstants;

/**
 * Created by icaboalo on 28/10/16.
 */

public class YanaPreferences {

    public static PreferenceKey TOKEN = new PreferenceKey(PrefConstants.tokenPref, "");

//    NOTIFICATIONS
    public static PreferenceKey FOOD_NOTIFICATION_ACTIVE = new PreferenceKey(PrefConstants.FOOD_NOTIFICATION_ACTIVE, false);
    public static PreferenceKey BREAKFAST_NOTIFICATION_ACTIVE = new PreferenceKey(PrefConstants.BREAKFAST_NOTIFICATION_ACTIVE, false);
    public static PreferenceKey BREAKFAST_NOTIFICATIN_TIME = new PreferenceKey(PrefConstants.BREAKFAST_NOTIFICATION_TIME, "10:00 AM");
    public static PreferenceKey LUNCH_NOTIFICATION_ACTIVE = new PreferenceKey(PrefConstants.LUNCH_NOTIFICATION_ACTIVE, false);
    public static PreferenceKey LUNCH_NOTIFICATIN_TIME = new PreferenceKey(PrefConstants.LUNCH_NOTIFICATION_TIME, "3:00 PM");
    public static PreferenceKey DINNER_NOTIFICATION_ACTIVE = new PreferenceKey(PrefConstants.DINNER_NOTIFICATION_ACTIVE, false);
    public static PreferenceKey DINNER_NOTIFICATIN_TIME = new PreferenceKey(PrefConstants.DINNER_NOTIFICATION_TIME, "9:00 PM");
    public static PreferenceKey DAY_NOTIFICATION_ACTIVE = new PreferenceKey(PrefConstants.DAY_NOTIFICATION_ACTIVE, false);
    public static PreferenceKey DAY_NOTIFICATIN_TIME = new PreferenceKey(PrefConstants.DAY_NOTIFICATION_TIME, "8:00 AM");
    public static PreferenceKey NIGHT_NOTIFICATION_ACTIVE = new PreferenceKey(PrefConstants.NIGHT_NOTIFICATION_ACTIVE, false);
    public static PreferenceKey NIGHT_NOTIFICATIN_TIME = new PreferenceKey(PrefConstants.NIGHT_NOTIFICATION_TIME, "10:00 PM");

//    RETAKE TEST
    public static PreferenceKey FIRST_TEST_TAKEN = new PreferenceKey(FilePreference.TEST, PrefConstants.FIRST_TEST_TAKEN, false);
    public static PreferenceKey SECOND_TEST_TAKEN = new PreferenceKey(FilePreference.TEST, PrefConstants.SECOND_TEST_TAKEN, false);
    public static PreferenceKey THIRD_TEST_TAKEN = new PreferenceKey(FilePreference.TEST, PrefConstants.THIRD_TEST_TAKEN, false);
    public static PreferenceKey FOURTH_TEST_TAKEN = new PreferenceKey(FilePreference.TEST, PrefConstants.FOURTH_TEST_TAKEN, false);
    public static PreferenceKey NEXT_TEST_DAY = new PreferenceKey(FilePreference.TEST, PrefConstants.NEXT_DAY_TEST, 0);
    public static PreferenceKey CURRENT_DAY = new PreferenceKey(FilePreference.TEST, PrefConstants.CURRENT_DAY, 0);
}
