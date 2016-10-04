package com.icaboalo.yana.presentation.screens.main.progress.chart;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.Tools;
import com.db.chart.animation.Animation;
import com.db.chart.animation.easing.BounceEase;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.view.LineChartView;
import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.main.view_model.ActionPlanViewModel;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icaboalo on 01/10/16.
 */

public class ChartFragment extends BaseFragment implements ChartView {

    @Inject
    ChartPresenter mChartPresenter;
    @Bind(R.id.lineChartView)
    LineChartView lineChartView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initialize() {
        getComponent(UserComponent.class).inject(this);
        mChartPresenter.setView(this);
    }

    @Override
    public void renderItem(ActionPlanViewModel item) {
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
        showToastMessage(message);
    }

    @Override
    public Context getApplicationContext() {
        return MyApplication.getInstance().getApplicationContext();
    }

    @Override
    public void getActionPlan(ActionPlanViewModel actionPlanViewModel) {
        mChartPresenter.attemptGetChartData(actionPlanViewModel.getDayList());
    }

    @Override
    public void getInfoLists(String[] dayList, float[] averageEmotions) {
        lineChartView.reset();

        Tooltip tooltip = new Tooltip(getActivity(), R.layout.tooltip_progress_chart, R.id.value);
        tooltip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        tooltip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(25));

        tooltip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

        tooltip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

        tooltip.setPivotX(Tools.fromDpToPx(65) / 2);
        tooltip.setPivotY(Tools.fromDpToPx(25));

        lineChartView.setTooltips(tooltip);

        LineSet dataSet = new LineSet(dayList, averageEmotions);

        dataSet.setColor(getResources().getColor(R.color.yana_green))
                .setDotsColor(getResources().getColor(R.color.yana_pink))
                .setThickness(4)
                .setSmooth(true);
        lineChartView.addData(dataSet);

        lineChartView.setYLabels(AxisRenderer.LabelPosition.NONE)
                .setYAxis(false)
                .setBorderSpacing(Tools.fromDpToPx(10));

        lineChartView.show(new Animation().setEasing(new BounceEase()));
    }
}
