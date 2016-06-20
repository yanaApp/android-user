package com.icaboalo.yana.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.ui.fragment.ActionPlanFragment;
import com.icaboalo.yana.ui.fragment.ContactsFragment;
import com.icaboalo.yana.ui.fragment.ProfileFragment;
import com.icaboalo.yana.ui.fragment.ProgressFragment;
import com.icaboalo.yana.util.VUtil;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

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


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new ActionPlanFragment());
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id){
            case R.id.nav_action_plan:
                fragment = new ActionPlanFragment();
                break;
            case R.id.nav_contacts:
                fragment = new ContactsFragment();
                break;
            case R.id.nav_progress:
                fragment = new ProgressFragment();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.nav_log_out:
                SharedPreferences sharedPreferences = getSharedPreferences(PrefConstants.authFile, MODE_PRIVATE);
                sharedPreferences.edit().clear().apply();
                break;

        }

        replaceFragment(fragment);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
