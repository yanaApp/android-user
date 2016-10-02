package com.icaboalo.yana.presentation.screens.main.progress;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.icaboalo.yana.R;
import com.icaboalo.yana.old.domain.FragmentPagerModel;
import com.icaboalo.yana.old.ui.adapter.ViewPagerAdapter;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.GenericListView;
import com.icaboalo.yana.presentation.screens.main.progress.chart.ChartFragment;
import com.icaboalo.yana.presentation.screens.main.progress.plan_breakdown.PlanBreakdownFragment;
import com.icaboalo.yana.presentation.screens.main.view_model.ActionPlanViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icaboalo on 01/10/16.
 */

public class ProgressFragment extends BaseFragment implements GenericListView<ActionPlanViewModel, RecyclerView.ViewHolder> {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    Spinner spActionPlan;

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
        setHasOptionsMenu(true);
        ArrayList<FragmentPagerModel> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentPagerModel(new PlanBreakdownFragment(), "Breakdown"));
        fragmentList.add(new FragmentPagerModel(new ChartFragment(), "Charts"));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_progress, menu);
        MenuItem menuItem = menu.findItem(R.id.spActionPlan);
        spActionPlan = (Spinner) MenuItemCompat.getActionView(menuItem);
    }

    @Override
    public void renderItemList(List<ActionPlanViewModel> itemList) {

    }

    @Override
    public void viewItemDetail(ActionPlanViewModel viewModel, RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getApplicationContext() {
        return null;
    }
}
