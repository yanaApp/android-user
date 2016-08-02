package com.icaboalo.yana.presentation.screens;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @author icaboalo on 31/07/16.
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initialize();
        setupUI();
    }


    public abstract void initialize();
    public abstract void setupUI();
}
