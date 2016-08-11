package com.icaboalo.yana.presentation.screens.splash;

import android.content.Intent;
import android.view.Window;

import com.icaboalo.yana.R;
import com.icaboalo.yana.old.ui.activity.MainActivity;
import com.icaboalo.yana.old.ui.activity.TutorialActivity;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.action_plan.loading.LoadingActivity;
import com.icaboalo.yana.util.PrefUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author icaboalo on 11/08/16.
 */
public class SplashScreenActivity extends BaseActivity {


    @Override
    public void initialize() {
    }

    @Override
    public void setupUI() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                checkForToken();
            }
        };

        long SPLASH_SCREEN_DELAY = 1500;
        new Timer().schedule(task, SPLASH_SCREEN_DELAY);
    }

    private void checkForToken(){
        if (PrefUtils.getToken(getApplicationContext()).isEmpty() || PrefUtils.getToken(getApplicationContext()).equals("TOKEN")){
            navigator.navigateTo(getApplicationContext(), new Intent(getApplicationContext(), TutorialActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        } else {
            if (!PrefUtils.isDownloadCompleted(getApplicationContext())){
                navigator.navigateTo(getApplicationContext(), new Intent(getApplicationContext(), LoadingActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
            else {
                navigator.navigateTo(getApplicationContext(), new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }
    }
}
