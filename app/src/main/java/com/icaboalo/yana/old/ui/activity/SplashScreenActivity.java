package com.icaboalo.yana.old.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.icaboalo.yana.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    void checkForToken(){
//        if (PrefUtils.getToken(this).equals("TOKEN") || PrefUtils.getToken(this).isEmpty()){
//            if (PrefUtils.isTutorialCompleted(this)){
//                Intent goToLogin = new Intent(this, com.icaboalo.yana.presentation.screens.login.LoginActivity.class);
//                startActivity(goToLogin);
//                Log.d("INTENT", "login");
//                finish();
//            }
//            else {
//                Intent goToTutorial = new Intent(this, TutorialActivity.class);
//                startActivity(goToTutorial);
//                Log.d("INTENT", "tutorial");
//                finish();
//            }
//        } else {
//            Intent goToMain = new Intent(SplashScreenActivity.this, MainActivity.class);
//            startActivity(goToMain);
//            Log.d("INTENT", "main");
//            finish();
//        }
    }
}
