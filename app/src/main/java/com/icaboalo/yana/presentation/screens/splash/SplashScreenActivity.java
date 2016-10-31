package com.icaboalo.yana.presentation.screens.splash;

import android.content.SharedPreferences;
import android.view.Window;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.other.ManagerPreference;
import com.icaboalo.yana.other.YanaPreferences;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.evaluation.EvaluationActivity;
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

                checkForToken();

            }
        };

        long SPLASH_SCREEN_DELAY = 1500;
        new Timer().schedule(task, SPLASH_SCREEN_DELAY);
    }

    private void checkForToken() {
        if (PrefUtils.getToken(getApplicationContext()).isEmpty() || PrefUtils.getToken(getApplicationContext()).equalsIgnoreCase("TOKEN")) {
            navigator.navigateTo(getApplicationContext(), TourActivity.getCallingContext(getApplicationContext()));
            finish();
        } else {
            if (!PrefUtils.isDownloadCompleted(getApplicationContext())) {
                navigator.navigateTo(getApplicationContext(), LoadingActivity.getCallingIntent(getApplicationContext()));
                finish();
            } else {
                navigator.navigateTo(getApplicationContext(), MainActivity.getCallingIntent(getApplicationContext()));
                finish();
            }
        }
    }
}
