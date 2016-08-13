package com.icaboalo.yana.presentation.screens.action_plan.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.old.ui.fragment.ActionPlanFragment;
import com.icaboalo.yana.old.ui.fragment.ContactsFragment;
import com.icaboalo.yana.old.ui.fragment.HelpFragment;
import com.icaboalo.yana.old.ui.fragment.ProfileFragment;
import com.icaboalo.yana.old.ui.fragment.ProgressFragment;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.UserViewModel;
import com.icaboalo.yana.util.RealmUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * @author icaboalo on 13/08/16.
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, GenericDetailView<UserViewModel>{

    @Inject
    MainPresenter mMainPresenter;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigationView)
    NavigationView navigationView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    @Bind(R.id.rlRetry)
    RelativeLayout rlRetry;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mMainPresenter.setView(this);
        mMainPresenter.initialize(6);
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.action_plan_title));
        replaceFragment(new ActionPlanFragment());
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
        Fragment fragment = null;
        switch (item.getItemId()){
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
    public void renderItem(UserViewModel item) {
        setHeaderInfo(item.getEmail(), item.getFullName());
    }

    @Override
    public void showLoading() {
        if (rlProgress != null)
            rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (rlProgress != null)
            rlProgress.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
        if (rlRetry != null)
            rlRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        if (rlRetry != null)
            rlRetry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }


    public static Intent getCallingIntent(Context context){
        return new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    private void setHeaderInfo(String email, String fullName){
        if (navigationView !=null){
            View header = navigationView.getHeaderView(0);
            TextView tvFullName = (TextView) header.findViewById(R.id.tvFullName);
            TextView tvEmail = (TextView) header.findViewById(R.id.tvEmail);

            tvFullName.setText(fullName);
            tvEmail.setText(email);
        }
    }

    private void showLogOutConfirmationDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_exit_to_app_black_24dp)
                .setTitle("Are you sure you want to exit?")
                .setMessage("")
                .setCancelable(false)
                .setPositiveButton("Sure", (dialog, which) -> {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.deleteAll();
                    realm.commitTransaction();
                    SharedPreferences sharedPreferences = getSharedPreferences(PrefConstants.authFile, MODE_PRIVATE);
                    sharedPreferences.edit().clear().apply();
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create().show();
    }

}
