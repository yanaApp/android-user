package com.icaboalo.yana.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.io.ApiClient;
import com.icaboalo.yana.io.model.ActivityApiModel;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.util.OnDialogButtonClick;
import com.icaboalo.yana.ui.fragment.ActionPlanFragment;
import com.icaboalo.yana.util.VUtil;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.icaboalo.yana.R.string.label_activity_complete;

public class MainActivity extends AppCompatActivity implements OnDialogButtonClick, NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FABToolbarLayout mFabToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (VUtil.getToken(this).equals("TOKEN") || VUtil.getToken(this).isEmpty()){
            Intent goToLogin = new Intent(this, LoginActivity.class);
            startActivity(goToLogin);
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFabToolbarLayout = (FABToolbarLayout) findViewById(R.id.fabtoolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabtoolbar_fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                mFabToolbarLayout.show();
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new ActionPlanFragment());
        getActivities();
    }

    void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    void getActivities(){
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
                        Log.d("ACTIVITY", activity.getTitle());
                    }
                    realm.commitTransaction();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ActivityApiModel>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (mFabToolbarLayout.isShown()){
            mFabToolbarLayout.hide();
        } else{
            super.onBackPressed();
        }
    }

    @Override
    public void onPositiveClick(DialogInterface dialog, @Nullable Object object, int labelResource) {
        dialog.dismiss();
    }

    @Override
    public void onNeutralClick(DialogInterface dialog, @Nullable Object object, int labelResource) {
        dialog.dismiss();
    }

    @Override
    public void onNegativeClick(DialogInterface dialog, @Nullable Object object, int labelResource) {
        dialog.dismiss();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id){
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_send:
                SharedPreferences sharedPreferences = getSharedPreferences(PrefConstants.authFile, MODE_PRIVATE);
                sharedPreferences.edit().clear().apply();
                break;

        }

//        replaceFragment(fragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
