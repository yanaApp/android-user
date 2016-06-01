package com.icaboalo.yana.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.ui.activity.LoginActivity;

/**
 * Created by icaboalo on 26/05/16.
 */
public class VUtil {

    public static void checkForToken(Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences(PrefConstants.authFile, Context.MODE_PRIVATE);
        String token = sharedPrefs.getString(PrefConstants.tokenPref, "");
        if (token.isEmpty()) {
            Intent goToLogin = new Intent(context, LoginActivity.class);
            context.startActivity(goToLogin);
        }
    }
}

