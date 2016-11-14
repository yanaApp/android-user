package com.icaboalo.yana.presentation.screens.splash;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.icaboalo.yana.R;
import com.icaboalo.yana.other.ManagerPreference;
import com.icaboalo.yana.other.YanaPreferences;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.main.loading.LoadingActivity;
import com.icaboalo.yana.presentation.screens.main.MainActivity;
import com.icaboalo.yana.presentation.screens.tour.TourActivity;
import com.icaboalo.yana.util.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                checkForToken();

            }
        };

        Log.d("DATE", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        Log.d("PAST", ManagerPreference.getInstance().getString(YanaPreferences.LAST_UPDATE));

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
            } else if (new Date(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))
                    .after(new Date(ManagerPreference.getInstance().getString(YanaPreferences.LAST_UPDATE)))) {
                navigator.navigateTo(getApplicationContext(), LoadingActivity.getCallingIntent(getApplicationContext()));
                finish();
            }
            else {
                navigator.navigateTo(getApplicationContext(), MainActivity.getCallingIntent(getApplicationContext()));
                finish();
            }
        }
    }
}
