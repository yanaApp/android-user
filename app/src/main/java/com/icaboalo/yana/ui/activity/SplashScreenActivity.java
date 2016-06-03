package com.icaboalo.yana.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.icaboalo.yana.R;
import com.icaboalo.yana.io.ApiClient;
import com.icaboalo.yana.io.model.ActivityApiModel;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.util.VUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        if (VUtil.getToken(this).isEmpty()){
            Intent goToLogin = new Intent(this, LoginActivity.class);
            startActivity(goToLogin);
            finish();
        } else {
            getActivitiesAPI();
        }
    }

    void getActivitiesAPI(){
        Call<ArrayList<ActivityApiModel>> call = ApiClient.getApiService().getActivities(VUtil.getToken(this));
        call.enqueue(new Callback<ArrayList<ActivityApiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ActivityApiModel>> call, Response<ArrayList<ActivityApiModel>> response) {
                if (response.isSuccessful()){
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    for (ActivityApiModel activityApi: response.body()){
                        ActivityModel activity = new ActivityModel();
                        activity.setId(activityApi.getmId());
                        activity.setTitle(activityApi.getTitle());
                        activity.setDescription(activityApi.getDescription());
                        activity.setAnswer(activityApi.getAnswer());
                        realm.copyToRealmOrUpdate(activity);
                    }
                    realm.commitTransaction();

                    Intent goToMain = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(goToMain);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ActivityApiModel>> call, Throwable t) {

            }
        });

    }
}
