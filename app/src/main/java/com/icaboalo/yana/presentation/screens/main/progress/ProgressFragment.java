package com.icaboalo.yana.presentation.screens.main.progress;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.main.progress.view_holder.DayInfoViewHolder;
import com.icaboalo.yana.presentation.screens.main.view_model.ActionPlanViewModel;
import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemInfo;
import com.icaboalo.yana.util.VUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 22/08/16.
 */
public class ProgressFragment extends BaseFragment implements ProgressView{

    @Inject
    ProgressPresenter mProgressPresenter;
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
    @Bind(R.id.ivEmotionAverage)
    ImageView ivEmotionAverage;
    @Bind(R.id.pbCompleted)
    ProgressBar pbCompleted;
    @Bind(R.id.rvDayProgress)
    RecyclerView rvDayProgress;
    GenericRecyclerViewAdapter<DayInfoViewHolder> mDayInfoRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initialize() {
        getComponent(UserComponent.class).inject(this);
        mProgressPresenter.setView(this);
        mProgressPresenter.initialize();
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
    public void setActivitiesAverage(int completedActivitiesAverage, int incompleteActivitiesAverage, int emotionAverage) {
        tvCompleted.setText(String.format("%s%%", completedActivitiesAverage));
        tvIncomplete.setText(String.format("%s%%", incompleteActivitiesAverage));
        VUtil.setEmotionImage(getApplicationContext(), emotionAverage, ivEmotionAverage);
        pbCompleted.setMax(completedActivitiesAverage + incompleteActivitiesAverage);
        pbCompleted.setProgress(completedActivitiesAverage);
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

    private void setupSpinner(List<ActionPlanViewModel> actionPlanViewModelList){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item);
        for (ActionPlanViewModel actionPlan : actionPlanViewModelList){
            if (actionPlan.isActive())
                arrayAdapter.add("Current Plan");
            else
                arrayAdapter.add(actionPlan.getInitialDate() + " - " + actionPlan.getFinalDate());
        }
        spActionPlan.setAdapter(arrayAdapter);
        spActionPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mProgressPresenter.attemptGetActivitiesCount(actionPlanViewModelList.get(position).getDayList());
                mProgressPresenter.attemptGetDayInfoList(actionPlanViewModelList.get(position).getDayList());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupDayInfoRecyclerView(){
        mDayInfoRecyclerViewAdapter = new GenericRecyclerViewAdapter<DayInfoViewHolder>(getApplicationContext(), new ArrayList<>()) {
            @Override
            public DayInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType){
                    default:
                        return new DayInfoViewHolder(mLayoutInflater.inflate(R.layout.item_day_progress_adapter, parent, false));
                }
            }
        };
        rvDayProgress.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvDayProgress.setAdapter(mDayInfoRecyclerViewAdapter);
    }
}
