package com.icaboalo.yana.presentation.screens.main.progress.plan_breakdown;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.animation.Animation;
import com.db.chart.animation.easing.ExpoEase;
import com.db.chart.model.BarSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.HorizontalStackBarChartView;
import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.main.view_model.ActionPlanViewModel;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 22/08/16.
 */
public class PlanBreakdownFragment extends BaseFragment implements PlanBreakdownView {

    @Bind(R.id.rlRetry)
    RelativeLayout rlRetry;
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    @Bind(R.id.tvCompleted)
    TextView tvCompleted;
    @Bind(R.id.tvIncomplete)
    TextView tvIncomplete;
    @Bind(R.id.tvNotDone)
    TextView tvNotDone;
    @Bind(R.id.pbCompleted)
    HorizontalStackBarChartView pbCompleted;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan_breakdown, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void renderItem(ActionPlanViewModel item) {

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

//    private void setupSpinner(List<ActionPlanViewModel> actionPlanViewModelList) {
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
//                android.R.layout.simple_spinner_dropdown_item);
//        for (ActionPlanViewModel actionPlan : actionPlanViewModelList) {
//            if (actionPlan.isActive())
//                arrayAdapter.insert("Current Plan", 0);
//            else
//                arrayAdapter.add(actionPlan.getInitialDate() + " - " + actionPlan.getFinalDate());
//        }
//        spActionPlan.setAdapter(arrayAdapter);
//        Log.d("Count", actionPlanViewModelList.size() + "");
//        spActionPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("Position", position + "");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }

    private void setProgressInfo(float completed, float incomplete, float notDone) {
        pbCompleted.reset();
        float[][] values = {{completed}, {incomplete}, {notDone}};
        pbCompleted.addData(new BarSet(new String[]{""}, values[0]).setColor(getResources().getColor(R.color.yana_green)));
        pbCompleted.addData(new BarSet(new String[]{""}, values[1]).setColor(getResources().getColor(R.color.yana_pink)));
        pbCompleted.addData(new BarSet(new String[]{""}, values[2]).setColor(getResources().getColor(R.color.yana_orange)));
        pbCompleted.setRoundCorners(Tools.fromDpToPx(5));
        pbCompleted.setYLabels(AxisRenderer.LabelPosition.NONE)
                .setXLabels(AxisRenderer.LabelPosition.NONE)
                .setAxisBorderValues(-100, 100, 1)
                .setYAxis(false)
                .setXAxis(false)
                .show(new Animation()
                        .setDuration(2500)
                        .setEasing(new ExpoEase()));
    }
}
