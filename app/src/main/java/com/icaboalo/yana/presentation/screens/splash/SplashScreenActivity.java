package com.icaboalo.yana.presentation.screens.splash;

import android.content.SharedPreferences;
import android.view.Window;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.main.loading.LoadingActivity;
import com.icaboalo.yana.presentation.screens.main.MainActivity;
import com.icaboalo.yana.presentation.screens.tour.TourActivity;
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

                checkForNotifications();
                checkForToken();
            }
        };

        long SPLASH_SCREEN_DELAY = 1500;
        new Timer().schedule(task, SPLASH_SCREEN_DELAY);
    }

    private void checkForToken(){
        if (PrefUtils.getToken(getApplicationContext()).isEmpty() || PrefUtils.getToken(getApplicationContext()).equalsIgnoreCase("TOKEN")){
            navigator.navigateTo(getApplicationContext(), TourActivity.getCallingContext(getApplicationContext()));
            finish();
        } else {
            if (!PrefUtils.isDownloadCompleted(getApplicationContext())){
                navigator.navigateTo(getApplicationContext(), LoadingActivity.getCallingIntent(getApplicationContext()));
                finish();
            }
            else {
                navigator.navigateTo(getApplicationContext(), MainActivity.getCallingIntent(getApplicationContext()));
                finish();
            }
        }
    }

    private void checkForNotifications(){
        SharedPreferences sharedPreferences = getSharedPreferences(PrefConstants.NOTIFICATIONS_FILE, MODE_PRIVATE);
        sharedPreferences.getString(PrefConstants.DAY_NOTIFICATION_TIME, "9:00 AM");
        sharedPreferences.getString(PrefConstants.BREAKFAST_NOTIFICATION_TIME, "10:00 AM");
        sharedPreferences.getString(PrefConstants.LUNCH_NOTIFICATION_TIME, "15:00 PM");
        sharedPreferences.getString(PrefConstants.DINNER_NOTIFICATION_TIME, "20:00 PM");
        sharedPreferences.getString(PrefConstants.NIGHT_NOTIFICATION_TIME, "21:00 PM");
    }
}
