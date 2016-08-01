package com.icaboalo.yana.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.icaboalo.yana.presentation.screens.BaseActivity;

/**
 * @author icaboalo on 31/07/16.
 */
public class Navigator {

    public Navigator() {
    }

    public void navigateTo(Context context, Intent intent){
        context.startActivity(intent);
    }

    public void navigateToForResult(BaseActivity activity, Intent intent, int requestCode){
        activity.startActivityForResult(intent, requestCode);
    }
}
