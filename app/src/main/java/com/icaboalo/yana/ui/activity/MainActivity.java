package com.icaboalo.yana.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.icaboalo.yana.R;
import com.icaboalo.yana.util.VUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VUtil.checkForToken(this);
    }
}
