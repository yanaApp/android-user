package com.icaboalo.yana.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.icaboalo.yana.PrefConstants;

/**
 * Created by icaboalo on 26/05/16.
 */
public class VUtil {

    public static String getToken(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(PrefConstants.authFile, Context.MODE_PRIVATE);
        String token = sharedPrefs.getString(PrefConstants.tokenPref, "");
        return token;
    }

    public static boolean isTutorialCompleted(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(PrefConstants.tutorialFile, Context.MODE_PRIVATE);
        return sharedPrefs.getBoolean(PrefConstants.isTutorialCompleted, false);
    }
}

