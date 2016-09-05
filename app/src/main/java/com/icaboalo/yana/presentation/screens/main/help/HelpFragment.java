package com.icaboalo.yana.presentation.screens.main.help;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.BaseFragment;

/**
 * Created by icaboalo on 10/07/16.
 */
public class HelpFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void initialize() {

    }
}
