package com.icaboalo.yana.presentation.screens.main.progress.chart;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.view.LineChartView;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.GenericListView;
import com.icaboalo.yana.presentation.screens.main.progress.plan_breakdown.PlanBreakdownPresenter;
import com.icaboalo.yana.presentation.screens.main.view_model.ActionPlanViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by icaboalo on 01/10/16.
 */

public class ChartFragment extends BaseFragment implements GenericListView<ActionPlanViewModel, RecyclerView.ViewHolder> {

    LineChartView lineChartView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        lineChartView = new LineChartView(getActivity());
        return lineChartView;
    }

    @Override
    public void initialize() {
        getComponent(UserComponent.class).inject(this);
//        mPlanBreakdownPresenter.setView(this);
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
