package com.icaboalo.yana.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.icaboalo.yana.PrefConstants;

/**
 * Created by icaboalo on 25/06/16.
 */
public class PrefUtils {

    private static final int privateMode = Context.MODE_PRIVATE;
    private static final String authFile = PrefConstants.authFile;
    public static final String settings = PrefConstants.settingsFile;

    public static String getToken(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(authFile, privateMode);
        return sharedPrefs.getString(PrefConstants.tokenPref, "");
    }

    public static boolean isProfileComplete(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(settings, privateMode);
        return sharedPrefs.getBoolean(PrefConstants.isProfileCompletedPref, false);
    }

    public static void setProfileComplete(Context context, boolean isComplete){
        SharedPreferences sharedPrefs = context.getSharedPreferences(settings, privateMode);
        sharedPrefs.edit().putBoolean(PrefConstants.isProfileCompletedPref, isComplete).apply();
    }

    public static boolean isActionPlanTourComplete(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(settings, privateMode);
        return sharedPreferences.getBoolean(PrefConstants.isActionPlanTourComplete, false);
    }

    public static void setActionPlanTourComplete(Context context, boolean isComplete){
        SharedPreferences sharedPreferences = context.getSharedPreferences(settings, privateMode);
        sharedPreferences.edit().putBoolean(PrefConstants.isActionPlanTourComplete, isComplete).apply();
    }

    public static boolean isProgressTourComplete(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(settings, privateMode);
        return sharedPreferences.getBoolean(PrefConstants.isProgressTourComplete, false);
    }

    public static void setProgressTourComplete(Context context, boolean isComplete){
        SharedPreferences sharedPreferences = context.getSharedPreferences(settings, privateMode);
        sharedPreferences.edit().putBoolean(PrefConstants.isProgressTourComplete, isComplete).apply();
    }

    public static boolean isDownloadCompleted(Context context){
        SharedPreferences nSharedPreferences = context.getSharedPreferences(settings, Context.MODE_PRIVATE);
        return nSharedPreferences.getBoolean(PrefConstants.isDownloadCompletedPref, false);
    }

    public static void setDownloadCompleted(Context context, boolean completed){
        SharedPreferences.Editor nEditor = context.getSharedPreferences(settings, Context.MODE_PRIVATE).edit();
        nEditor.putBoolean(PrefConstants.isDownloadCompletedPref, completed);
        nEditor.apply();
    }

    public static int getEvaluationResult(Context context){
        return context.getSharedPreferences(PrefConstants.evaluationFile, Context.MODE_PRIVATE).getInt(PrefConstants.evaluationPref, 0);
    }

    public static void setUserId(Context context, int userId){
        SharedPreferences.Editor editor = context.getSharedPreferences(PrefConstants.settingsFile, Context.MODE_PRIVATE).edit();
        editor.putInt(PrefConstants.userId, userId);
        editor.apply();
    }

    public static int getUserId(Context context){
        return context.getSharedPreferences(PrefConstants.settingsFile, Context.MODE_PRIVATE).getInt(PrefConstants.userId, 0);
    }

    public static void setUserEmail(Context context, String userEmail){
        SharedPreferences.Editor editor = context.getSharedPreferences(PrefConstants.settingsFile, Context.MODE_PRIVATE).edit();
        editor.putString(PrefConstants.userEmail, userEmail);
        editor.apply();
    }

    public static String getUserEmail(Context context){
        return context.getSharedPreferences(PrefConstants.settingsFile, Context.MODE_PRIVATE).getString(PrefConstants.userEmail, "");
    }
}
