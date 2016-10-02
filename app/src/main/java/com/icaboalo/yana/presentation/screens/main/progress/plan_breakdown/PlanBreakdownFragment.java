package com.icaboalo.yana.presentation.screens.main.progress.plan_breakdown;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.model.BarSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.HorizontalStackBarChartView;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.ExpoEase;
import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemInfo;
import com.icaboalo.yana.presentation.screens.main.progress.ProgressView;
import com.icaboalo.yana.presentation.screens.main.progress.view_holder.DayInfoViewHolder;
import com.icaboalo.yana.presentation.screens.main.view_model.ActionPlanViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 22/08/16.
 */
public class PlanBreakdownFragment extends BaseFragment implements ProgressView {

    @Inject
    PlanBreakdownPresenter mPlanBreakdownPresenter;
    @Bind(R.id.rlRetry)
    RelativeLayout rlRetry;
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    @Bind(R.id.spActionPlan)
    Spinner spActionPlan;
    @Bind(R.id.tvCompleted)
    TextView tvCompleted;
    @Bind(R.id.tvIncomplete)
    TextView tvIncomplete;
    @Bind(R.id.tvNotDone)
    TextView tvNotDone;
    @Bind(R.id.pbCompleted)
    HorizontalStackBarChartView pbCompleted;
    @Bind(R.id.rvDayProgress)
    RecyclerView rvDayProgress;
    GenericRecyclerViewAdapter<DayInfoViewHolder> mDayInfoRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan_breakdown, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        pbCompleted.addData(new BarSet(new String[]{""}, new float[]{0}).setColor(getResources().getColor(R.color.yana_green)));
        pbCompleted.addData(new BarSet(new String[]{""}, new float[]{0}).setColor(getResources().getColor(R.color.yana_pink)));
        pbCompleted.addData(new BarSet(new String[]{""}, new float[]{0}).setColor(getResources().getColor(R.color.yana_orange)));
        pbCompleted.setYLabels(AxisController.LabelPosition.NONE)
                .setXLabels(AxisController.LabelPosition.NONE)
                .setYAxis(false)
                .setXAxis(false).show();
    }

    @Override
    public void initialize() {
        getComponent(UserComponent.class).inject(this);
        mPlanBreakdownPresenter.setView(this);
        mPlanBreakdownPresenter.initialize();
        setupDayInfoRecyclerView();
    }

    @Override
    public void renderItemList(List<ActionPlanViewModel> itemList) {
        setupSpinner(itemList);
    }

    @Override
    public void viewItemDetail(ActionPlanViewModel viewModel, RecyclerView.ViewHolder viewHolder) {
    }

    @Override
    public void setActivitiesAverage(int completedActivitiesAverage, int incompleteActivitiesAverage, int notDoneActivitiesAverage) {
        tvCompleted.setText(String.format("%s%%", completedActivitiesAverage));
        tvIncomplete.setText(String.format("%s%%", incompleteActivitiesAverage));
        tvNotDone.setText(String.format("%s%%", notDoneActivitiesAverage));
//        pbCompleted.setMax(completedActivitiesAverage + incompleteActivitiesAverage);
//        pbCompleted.setProgress(completedActivitiesAverage);
        setProgressInfo(completedActivitiesAverage, incompleteActivitiesAverage, notDoneActivitiesAverage);
    }

    @Override
    public void setDayInfoList(List<ItemInfo> dayItemInfoList) {
        mDayInfoRecyclerViewAdapter.setDataList(dayItemInfoList);
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
    public Context getApplicationContext() {
        return MyApplication.getInstance().getApplicationContext();
    }

    private void setupSpinner(List<ActionPlanViewModel> actionPlanViewModelList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item);
        for (ActionPlanViewModel actionPlan : actionPlanViewModelList) {
            if (actionPlan.isActive())
                arrayAdapter.insert("Current Plan", 0);
            else
                arrayAdapter.add(actionPlan.getInitialDate() + " - " + actionPlan.getFinalDate());
        }
        spActionPlan.setAdapter(arrayAdapter);
        Log.d("Count", actionPlanViewModelList.size() + "");
        spActionPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Position", position + "");
                mPlanBreakdownPresenter.attemptGetActivitiesCount(actionPlanViewModelList.get(position).getDayList());
                mPlanBreakdownPresenter.attemptGetDayInfoList(actionPlanViewModelList.get(position).getDayList());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

    private void setProgressInfo(float completed, float incomplete, float notDone){
//        pbCompleted.dismiss();g
        float[] [] values = {{completed}, {incomplete}, {notDone}};
        pbCompleted.updateValues(0, values[0]);
        pbCompleted.updateValues(1, values[1]);
        pbCompleted.updateValues(2, values[2]);
        pbCompleted.notifyDataUpdate();
        pbCompleted.setRoundCorners(Tools.fromDpToPx(5));
        pbCompleted.setYLabels(AxisController.LabelPosition.NONE)
                .setXLabels(AxisController.LabelPosition.NONE)
                .setYAxis(false)
                .setXAxis(false)
                .show(new Animation()
                        .setDuration(2500)
                        .setEasing(new ExpoEase()));
    }
}
