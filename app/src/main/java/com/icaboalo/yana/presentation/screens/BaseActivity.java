package com.icaboalo.yana.presentation.screens;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.presentation.di.HasComponent;
import com.icaboalo.yana.presentation.di.component.ApplicationComponent;
import com.icaboalo.yana.presentation.di.component.DaggerUserComponent;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.di.module.ActivityModule;
import com.icaboalo.yana.presentation.factories.SnackbarFactory;
import com.icaboalo.yana.presentation.navigation.Navigator;

import javax.inject.Inject;

/**
 * @author icaboalo on 31/07/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements HasComponent<UserComponent> {

    @Inject
    public Navigator navigator;
    private UserComponent mUserComponent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        initializeInjector();
        setupUI();
        initialize();
    }

    @Override
    public UserComponent getComponent() {
        return mUserComponent;
    }

    public abstract void initialize();

    public abstract void setupUI();


    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link com.icaboalo.yana.presentation.di.component.ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplicationContext()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link com.icaboalo.yana.presentation.di.module.ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    public void initializeInjector() {
        mUserComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) this).getComponent());
    }

    public void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showSnackbarMessage(@SnackbarFactory.SnackbarType String type, View view, String message, int duration) {
        SnackbarFactory.getSnackbar(type, view, message, duration).show();
    }

    public void showSnackbarMessage(@SnackbarFactory.SnackbarType String type, View view, @StringRes int stringId, int duration) {
        SnackbarFactory.getSnackbar(type, view, stringId, duration).show();
    }

    protected void showDialog(String title, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .create();

        if (onClickListener == null)
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog, which) -> {
                dialog.dismiss();
            });
        else alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", onClickListener);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
