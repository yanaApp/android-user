package com.icaboalo.yana.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.icaboalo.yana.PrefConstants;

/**
 * Created by icaboalo on 25/06/16.
 */
public class PrefUtils {

    private final int privateMode = Context.MODE_PRIVATE;
    private final String authFile = PrefConstants.authFile;
    private final String tutorialFile = PrefConstants.tutorialFile;
    private final String profileFile = PrefConstants.profileFile;
    private final String firstTimeFile = PrefConstants.firstTimeFile;
    private final String tourFile = PrefConstants.tourFile;

    public static String getToken(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(authFile, privateMode);
        return sharedPrefs.getString(PrefConstants.tokenPref, "");
    }

    public static boolean isTutorialCompleted(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(tutorialFile, privateMode);
        return sharedPrefs.getBoolean(PrefConstants.isTutorialCompleted, false);
    }

    public static void setTutorialComplete(Context context, boolean isComplete){
        SharedPreferences sharedPrefs = context.getSharedPreferences(tutorialFile, privateMode);
        sharedPrefs.edit().putBoolean(PrefConstants.isTutorialCompleted, isComplete).apply();
    }

    public static boolean isProfileComplete(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(profileFile, privateMode);
        return sharedPrefs.getBoolean(PrefConstants.isProfileCompletedPref, false);
    }

    public static void setProfileComplete(Context context, boolean isComplete){
        SharedPreferences sharedPrefs = context.getSharedPreferences(profileFile, privateMode);
        sharedPrefs.edit().putBoolean(PrefConstants.isProfileCompletedPref, isComplete).apply();
    }

    public static boolean isContactsFirstTime(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(firstTimeFile, privateMode);
        return sharedPrefs.getBoolean(PrefConstants.contactPref, true);
    }

    public static void setContactsFirstTime(Context context, boolean isFirstTime){
        SharedPreferences sharedPrefs = context.getSharedPreferences(firstTimeFile, privateMode);
        sharedPrefs.edit().putBoolean(PrefConstants.contactPref, isFirstTime).apply();
    }

    public static boolean isProgressFirstTime(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(firstTimeFile, privateMode);
        return sharedPrefs.getBoolean(PrefConstants.progressPref, true);
    }

    public static void setProgressFirstTime(Context context, boolean isFirstTime){
        SharedPreferences sharedPrefs = context.getSharedPreferences(firstTimeFile, privateMode);
        sharedPrefs.edit().putBoolean(PrefConstants.progressPref, isFirstTime).apply();
    }

    public static boolean isActionPlanTourComplete(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(tourFile, privateMode);
        return sharedPreferences.getBoolean(PrefConstants.isActionPlanTourComplete, false);
    }

    public static void setActionPlanTourComplete(Context context, boolean isComplete){
        SharedPreferences sharedPreferences = context.getSharedPreferences(tourFile, privateMode);
        sharedPreferences.edit().putBoolean(PrefConstants.isActionPlanTourComplete, isComplete).apply();
    }

    public static boolean isProfileTourComplete(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(tourFile, privateMode);
        return sharedPreferences.getBoolean(PrefConstants.isProfileTourComplete, false);
    }

    public static void setProfileTourComplete(Context context, boolean isComplete){
        SharedPreferences sharedPreferences = context.getSharedPreferences(tourFile, privateMode);
        sharedPreferences.edit().putBoolean(PrefConstants.isProfileTourComplete, isComplete).apply();
    }

    public static boolean isContactsTourComplete(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(tourFile, privateMode);
        return sharedPreferences.getBoolean(PrefConstants.isContactsTourComplete, false);
    }

    public static void setContactsTourComplete(Context context, boolean isComplete){
        SharedPreferences sharedPreferences = context.getSharedPreferences(tourFile, privateMode);
        sharedPreferences.edit().putBoolean(PrefConstants.isContactsTourComplete, isComplete).apply();
    }

    public static boolean isProgressTourComplete(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(tourFile, privateMode);
        return sharedPreferences.getBoolean(PrefConstants.isProgressTourComplete, false);
    }

    public static void setProgressTourComplete(Context context, boolean isComplete){
        SharedPreferences sharedPreferences = context.getSharedPreferences(tourFile, privateMode);
        sharedPreferences.edit().putBoolean(PrefConstants.isProgressTourComplete, isComplete).apply();
    }
}
