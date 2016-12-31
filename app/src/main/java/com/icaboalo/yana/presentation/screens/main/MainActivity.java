package com.icaboalo.yana.presentation.screens.main;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.other.FilePreference;
import com.icaboalo.yana.other.ManagerPreference;
import com.icaboalo.yana.other.YanaPreferences;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.chat_bot.ChatBotActivity;
import com.icaboalo.yana.presentation.screens.evaluation.EvaluationActivity;
import com.icaboalo.yana.presentation.screens.main.activities.ActivitiesFragment;
import com.icaboalo.yana.presentation.screens.main.contact.ContactFragment;
import com.icaboalo.yana.presentation.screens.main.help.HelpFragment;
import com.icaboalo.yana.presentation.screens.main.hotline.HotlineFragment;
import com.icaboalo.yana.presentation.screens.main.profile.ProfileFragment;
import com.icaboalo.yana.presentation.screens.main.progress.ProgressFragment;
import com.icaboalo.yana.presentation.screens.settings.SettingsActivity;
import com.icaboalo.yana.presentation.screens.view_model.UserViewModel;
import com.icaboalo.yana.util.PrefUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 13/08/16.
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, GenericDetailView<UserViewModel>{

    @Inject
    MainPresenter mMainPresenter;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.rl_retry)
    RelativeLayout rlRetry;
    Fragment fragment;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mMainPresenter.setView(this);
        mMainPresenter.initialize(String.valueOf(PrefUtils.getUserId(getApplicationContext())));
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle(getString(R.string.action_plan_title));
        setSupportActionBar(toolbar);
        replaceFragment(new ActivitiesFragment());
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        checkForTest();
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
        if (fragment == null)
            fragment = new Fragment();
        switch (item.getItemId()){
            case R.id.nav_action_plan:
                getSupportActionBar().setTitle(R.string.action_plan_title);
                if (fragment instanceof ActivitiesFragment)
                    break;
                else{
                    fragment = new ActivitiesFragment();
                    replaceFragment(fragment);
                }
                break;
            case R.id.nav_contacts:
                getSupportActionBar().setTitle(R.string.contacts_title);
                if (fragment instanceof ContactFragment)
                    break;
                else{
                    fragment = new ContactFragment();
                    replaceFragment(fragment);
                }
                break;
            case R.id.nav_progress:
                getSupportActionBar().setTitle(R.string.progress_title);
                if (fragment instanceof ProgressFragment)
                    break;
                else{
                    fragment = new ProgressFragment();
                    replaceFragment(fragment);
                }
                break;
            case R.id.nav_profile:
                getSupportActionBar().setTitle(R.string.profile_title);
                if (fragment instanceof ProfileFragment)
                    break;
                else{
                    fragment = new ProfileFragment();
                    replaceFragment(fragment);
                }
                break;
            case R.id.nav_hotline:
                getSupportActionBar().setTitle(R.string.hotline_title);
                if (fragment instanceof HotlineFragment)
                    break;
                else{
                    fragment = new HotlineFragment();
                    replaceFragment(fragment);
                }
                break;
            case R.id.nav_settings:
                navigator.navigateTo(getApplicationContext(), SettingsActivity.getCallingIntent(getApplicationContext()));
                break;
            case R.id.nav_log_out:
                showLogOutConfirmationDialog();
                break;
            case R.id.nav_help:
                getSupportActionBar().setTitle(R.string.help_title);
                if (fragment instanceof HelpFragment)
                    break;
                else{
                    fragment = new HelpFragment();
                    replaceFragment(fragment);
                }
                break;
        }

//        if (fragment != null){
//            replaceFragment(fragment);
//        }
        drawerLayout.closeDrawer(GravityCompat.START);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case EvaluationActivity.REQUEST_CODE:
                    showError(data.getIntExtra("answer", 0) + "");
                    int answer = data.getIntExtra("answer", 0);
                    switch (data.getIntExtra("testNumber", 0)) {
                        case 1:
                            ManagerPreference.getInstance().set(YanaPreferences.FIRST_TEST_TAKEN, true);
                            showDialog("Puntuación -> " + answer, null, (dialog, which) -> dialog.dismiss());
                            break;
                        case 2:
                            ManagerPreference.getInstance().set(YanaPreferences.SECOND_TEST_TAKEN, true);
                            showDialog("Puntuación -> " + answer, null, (dialog, which) -> dialog.dismiss());
                            break;
                        case 3:
                            ManagerPreference.getInstance().set(YanaPreferences.THIRD_TEST_TAKEN, true);
                            showDialog("Puntuación -> " + answer, null, (dialog, which) -> dialog.dismiss());
                            break;
                        case 4:
                            ManagerPreference.getInstance().set(YanaPreferences.FOURTH_TEST_TAKEN, true);
                            showDialog("Puntuación -> " + answer, null, (dialog, which) -> dialog.dismiss());
                            break;
                    }
                    break;
            }
        }
    }

    public static Intent getCallingIntent(Context context){
        return new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    private void setHeaderInfo(String email, String fullName){
        if (navigationView != null){
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
                .setTitle(R.string.log_out_description)
                .setMessage("")
                .setCancelable(false)
                .setPositiveButton("Sure", (dialog, which) -> {
                    mMainPresenter.attemptLogOut();
                    ManagerPreference.getInstance().resetGroupPreference(FilePreference.DEFAULT_PREFERENCE);
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
//                    createNotification();
                    navigator.navigateTo(getApplicationContext(), ChatBotActivity.getCallingIntent(getApplicationContext()));
                    dialog.dismiss();
                })
                .create().show();
    }

    private void checkForTest() {
        if (ManagerPreference.getInstance().getInt(YanaPreferences.CURRENT_DAY) >= 8
                && !ManagerPreference.getInstance().getBoolean(YanaPreferences.FIRST_TEST_TAKEN)) {
            showDialog("Primer examen", "Ya completaste tu primera semana dentro de Yana! ahora necesitamos que tomes el examen de nuevo para obtener mejores datos sobre tu progreso",
                    (dialog, which) -> {
                        navigator.navigateToForResult(MainActivity.this, EvaluationActivity.getCallingIntent(getApplicationContext(), 1),
                                EvaluationActivity.REQUEST_CODE);
                        dialog.dismiss();
                    });
        }
        else if (ManagerPreference.getInstance().getInt(YanaPreferences.CURRENT_DAY) >= 16
                && !ManagerPreference.getInstance().getBoolean(YanaPreferences.SECOND_TEST_TAKEN)) {
            showDialog("Segundo examen", "Ya completaste tu primera semana dentro de Yana! ahora necesitamos que tomes el examen de nuevo para obtener mejores datos sobre tu progreso",
                    (dialog, which) -> {
                        navigator.navigateToForResult(MainActivity.this, EvaluationActivity.getCallingIntent(getApplicationContext(), 2),
                                EvaluationActivity.REQUEST_CODE);
                        dialog.dismiss();
                    });
        }

        else if (ManagerPreference.getInstance().getInt(YanaPreferences.CURRENT_DAY) >= 24
                && !ManagerPreference.getInstance().getBoolean(YanaPreferences.THIRD_TEST_TAKEN)) {
            showDialog("Tercer examen", "Ya completaste tu primera semana dentro de Yana! ahora necesitamos que tomes el examen de nuevo para obtener mejores datos sobre tu progreso",
                    (dialog, which) -> {
                        navigator.navigateToForResult(MainActivity.this, EvaluationActivity.getCallingIntent(getApplicationContext(), 3),
                                EvaluationActivity.REQUEST_CODE);
                        dialog.dismiss();
                    });
        }

        else if (ManagerPreference.getInstance().getInt(YanaPreferences.CURRENT_DAY) >= 30
                && !ManagerPreference.getInstance().getBoolean(YanaPreferences.FOURTH_TEST_TAKEN)) {
            showDialog("Ultimo examen", "Ya completaste tu primera semana dentro de Yana! ahora necesitamos que tomes el examen de nuevo para obtener mejores datos sobre tu progreso",
                    (dialog, which) -> {
                        navigator.navigateToForResult(MainActivity.this, EvaluationActivity.getCallingIntent(getApplicationContext(), 4),
                                EvaluationActivity.REQUEST_CODE);
                        dialog.dismiss();
                    });
        }
    }

}
