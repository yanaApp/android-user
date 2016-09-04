package com.icaboalo.yana.old.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.old.ui.fragment.ActionPlanFragment;
import com.icaboalo.yana.old.ui.fragment.ContactsFragment;
import com.icaboalo.yana.old.ui.fragment.HelpFragment;
import com.icaboalo.yana.old.ui.fragment.ProfileFragment;
import com.icaboalo.yana.old.ui.fragment.ProgressFragment;
import com.icaboalo.yana.presentation.screens.login.*;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.RealmUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigationView)
    NavigationView navigationView;

    Realm mRealmInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealmInstance = Realm.getDefaultInstance();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (PrefUtils.getToken(this).equals("TOKEN") || PrefUtils.getToken(this).isEmpty()){
            Intent goToLogin = new Intent(this, com.icaboalo.yana.presentation.screens.login.LoginActivity.class);
            startActivity(goToLogin);
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setHeaderInfo();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle(R.string.action_plan_title);
        replaceFragment(new ActionPlanFragment());

        if (!PrefUtils.isProfileComplete(this) && PrefUtils.isActionPlanTourComplete(this)){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Perfil no completo");
            alertDialog.setMessage("Por favor ayudanos a completar tu perfil para obtener más información");
            alertDialog.setPositiveButton("VAMOS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    replaceFragment(new ProfileFragment());
                }
            });
            alertDialog.setNeutralButton("MÁS TARDE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setNegativeButton("NO VOLVER A MOSTRAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PrefUtils.setProfileComplete(MainActivity.this, true);
                }
            });
            alertDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
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
                getSupportActionBar().setTitle(R.string.action_plan_title);
                fragment = new ActionPlanFragment();
                break;
            case R.id.nav_contacts:
                getSupportActionBar().setTitle(R.string.contacts_title);
                fragment = new ContactsFragment();
                break;
            case R.id.nav_progress:
                getSupportActionBar().setTitle(R.string.progress_title);
                fragment = new ProgressFragment();
                break;
            case R.id.nav_profile:
                getSupportActionBar().setTitle(R.string.profile_title);
                fragment = new ProfileFragment();
                break;
            case R.id.nav_log_out:
                showLogOutConfirmationDialog();
                break;
            case R.id.nav_help:
                getSupportActionBar().setTitle(R.string.help_title);
                fragment = new HelpFragment();
                break;

        }

        if (fragment != null){
            replaceFragment(fragment);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRealmInstance.close();
    }

    private void showLogOutConfirmationDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_exit_to_app_black_24dp)
                .setTitle("Are you sure you want to exit?")
                .setMessage("")
                .setCancelable(false)
                .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.deleteAll();
                        realm.commitTransaction();
                        SharedPreferences sharedPreferences = getSharedPreferences(PrefConstants.authFile, MODE_PRIVATE);
                        sharedPreferences.edit().clear().apply();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    private void setHeaderInfo(){
        if (navigationView !=null){
            View header = navigationView.getHeaderView(0);
            TextView tvFullName = (TextView) header.findViewById(R.id.tvFullName);
            TextView tvEmail = (TextView) header.findViewById(R.id.tvEmail);

            tvFullName.setText(RealmUtils.getUser(mRealmInstance).getFullName());
            tvEmail.setText(RealmUtils.getUser(mRealmInstance).getEmail());
        }
    }
}
