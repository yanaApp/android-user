package com.icaboalo.yana.presentation.screens.action_plan.main;

import android.content.Context;
import android.content.Intent;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseActivity;

import butterknife.ButterKnife;

/**
 * @author icaboalo on 13/08/16.
 */
public class MainActivity extends BaseActivity {

    @Override
    public void initialize() {

    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public static Intent getCallingIntent(Context context){
        return new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
