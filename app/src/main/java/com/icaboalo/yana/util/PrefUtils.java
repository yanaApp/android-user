package com.icaboalo.yana.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.icaboalo.yana.PrefConstants;

/**
 * Created by icaboalo on 25/06/16.
 */
public class PrefUtils {

    public static String getToken(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(PrefConstants.authFile, Context.MODE_PRIVATE);
        return sharedPrefs.getString(PrefConstants.tokenPref, "");
    }

    public static boolean isTutorialCompleted(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(PrefConstants.tutorialFile, Context.MODE_PRIVATE);
        return sharedPrefs.getBoolean(PrefConstants.isTutorialCompleted, false);
    }

    public static void setTutorialComplete(Context context, boolean isComplete){
        SharedPreferences sharedPrefs = context.getSharedPreferences(PrefConstants.tutorialFile, Context.MODE_PRIVATE);
        sharedPrefs.edit().putBoolean(PrefConstants.isTutorialCompleted, isComplete).apply();
    }

    public static boolean isProfileComplete(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(PrefConstants.profileFile, Context.MODE_PRIVATE);
        return sharedPrefs.getBoolean(PrefConstants.isProfileCompletedPref, false);
    }

    public static void setProfileComplete(Context context, boolean isComplete){
        SharedPreferences sharedPrefs = context.getSharedPreferences(PrefConstants.profileFile, Context.MODE_PRIVATE);
        sharedPrefs.edit().putBoolean(PrefConstants.isProfileCompletedPref, isComplete).apply();
    }

    public static boolean isContactsFirstTime(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(PrefConstants.firstTimeFile, Context.MODE_PRIVATE);
        return sharedPrefs.getBoolean(PrefConstants.contactPref, true);
    }

    public static void setContactsFirstTime(Context context, boolean isFirstTime){
        SharedPreferences sharedPrefs = context.getSharedPreferences(PrefConstants.firstTimeFile, Context.MODE_PRIVATE);
        sharedPrefs.edit().putBoolean(PrefConstants.contactPref, isFirstTime).apply();
    }

    public static boolean isProgressFirstTime(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(PrefConstants.firstTimeFile, Context.MODE_PRIVATE);
        return sharedPrefs.getBoolean(PrefConstants.progressPref, true);
    }

    public static void setProgressFirstTime(Context context, boolean isFirstTime){
        SharedPreferences sharedPrefs = context.getSharedPreferences(PrefConstants.firstTimeFile, Context.MODE_PRIVATE);
        sharedPrefs.edit().putBoolean(PrefConstants.progressPref, isFirstTime).apply();
    }
}
