package com.icaboalo.yana.presentation.screens.main.progress;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.old.domain.FragmentPagerModel;
import com.icaboalo.yana.old.ui.adapter.ViewPagerAdapter;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.factories.SnackbarFactory;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.component.adapter.ItemInfo;
import com.icaboalo.yana.presentation.screens.main.progress.chart.ChartFragment;
import com.icaboalo.yana.presentation.screens.main.progress.chart.ChartView;
import com.icaboalo.yana.presentation.screens.main.progress.plan_breakdown.PlanBreakdownFragment;
import com.icaboalo.yana.presentation.screens.main.progress.plan_breakdown.PlanBreakdownView;
import com.icaboalo.yana.presentation.screens.main.progress.view_holder.DayInfoViewHolder;
import com.icaboalo.yana.presentation.view_model.ActionPlanViewModel;
import com.icaboalo.yana.util.Utils;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by icaboalo on 01/10/16.
 */

public class ProgressFragment extends BaseFragment implements ProgressView {

    @Inject
    ProgressPresenter mProgressPresenter;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.inkPageIndicator)
    InkPageIndicator inkPageIndicator;
    @BindView(R.id.rvDayProgress)
    RecyclerView rvDayProgress;
    @BindView(R.id.fl_no_info)
    FrameLayout flNoInfo;
    Spinner spActionPlan;
    PlanBreakdownView mPlanBreakdownView;
    GenericRecyclerViewAdapter<DayInfoViewHolder> mDayInfoRecyclerViewAdapter;
    ChartView mChartView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupDayInfoRecyclerView();
        setupViewPager();
    }

    @Override
    public void initialize() {
        setHasOptionsMenu(true);
        getComponent(UserComponent.class).inject(this);
        mProgressPresenter.setView(this);
        mProgressPresenter.initialize();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_progress, menu);
        MenuItem menuItem = menu.findItem(R.id.spActionPlan);
        spActionPlan = (Spinner) MenuItemCompat.getActionView(menuItem);
    }

    @Override
    public void renderItemList(List<ActionPlanViewModel> itemList) {
        if (itemList == null || itemList.isEmpty())
            flNoInfo.setVisibility(View.VISIBLE);
        else {
            setupSpinner(itemList);
            flNoInfo.setVisibility(View.GONE);
        }
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
        showSnackbarMessage(SnackbarFactory.TYPE_ERROR, viewPager, message, Snackbar.LENGTH_SHORT);
    }

    @Override
    public Context getApplicationContext() {
        return MyApplication.getInstance().getApplicationContext();
    }

    @Override
    public void setDayInfoList(List<ItemInfo> dayItemInfoList) {
        mDayInfoRecyclerViewAdapter.setDataList(dayItemInfoList);
    }

    @Override
    public void sendInfoToBreakdownSuccessful(int completedAverage, int incompleteAverage, int notDoneAverage) {
        mPlanBreakdownView.setActivitiesAverage(completedAverage, incompleteAverage, notDoneAverage);
    }

    @Override
    public void sendDataToChartSuccessful(String[] dayList, float[] averageEmotions) {
        mChartView.getInfoLists(dayList, averageEmotions);
    }

    private void setupDayInfoRecyclerView() {
        mDayInfoRecyclerViewAdapter = new GenericRecyclerViewAdapter<DayInfoViewHolder>(getActivity(), new ArrayList<>()) {
            @Override
            public DayInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType) {
                    default:
                        return new DayInfoViewHolder(mLayoutInflater.inflate(R.layout.item_day_plan_breakdown_adapter, parent, false));
                }
            }
        };
        rvDayProgress.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvDayProgress.setAdapter(mDayInfoRecyclerViewAdapter);
    }

    private void setupViewPager(){
        ArrayList<FragmentPagerModel> fragmentList = new ArrayList<>();
        PlanBreakdownFragment planBreakdownFragment = new PlanBreakdownFragment();
        ChartFragment chartFragment = new ChartFragment();
        mPlanBreakdownView = planBreakdownFragment;
        mChartView = chartFragment;
        fragmentList.add(new FragmentPagerModel(planBreakdownFragment, "BreakDown"));
        fragmentList.add(new FragmentPagerModel(chartFragment, "Charts"));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
        inkPageIndicator.setViewPager(viewPager);
    }

    private void setupSpinner(List<ActionPlanViewModel> actionPlanViewModelList) {
        Collections.sort(actionPlanViewModelList, (itemInfo, t1) -> t1.getId() - itemInfo.getId());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item);
        for (ActionPlanViewModel actionPlan : actionPlanViewModelList) {
            if (actionPlan.isActive())
                arrayAdapter.insert(getString(R.string.current_plan), 0);
            else {
                arrayAdapter.add("Plan de " + Utils.transformDateToText(actionPlan.getInitialDate(), "dd-MM-yyyy", "MMM dd")
                        + " a " +
                        Utils.transformDateToText(actionPlan.getFinalDate(), "dd-MM-yyyy", "MMM dd"));
            }
        }
        spActionPlan.setAdapter(arrayAdapter);
        spActionPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mProgressPresenter.attemptGetDayInfoList(actionPlanViewModelList.get(position).getDayList());
                mProgressPresenter.attemptSendInfoToBreakdown(actionPlanViewModelList.get(position).getDayList());
                mProgressPresenter.attemptSendDataToChart(actionPlanViewModelList.get(position).getDayList());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
