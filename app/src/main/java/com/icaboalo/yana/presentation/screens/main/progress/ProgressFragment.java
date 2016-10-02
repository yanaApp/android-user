package com.icaboalo.yana.presentation.screens.main.progress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icaboalo.yana.R;
import com.icaboalo.yana.old.domain.FragmentPagerModel;
import com.icaboalo.yana.old.ui.adapter.ViewPagerAdapter;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.main.progress.plan_breakdown.PlanBreakdownFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icaboalo on 01/10/16.
 */

public class ProgressFragment extends BaseFragment {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void initialize() {
        ArrayList<FragmentPagerModel> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentPagerModel(new PlanBreakdownFragment(), "Breakdown"));
        fragmentList.add(new FragmentPagerModel(new Fragment(), "Charts"));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
