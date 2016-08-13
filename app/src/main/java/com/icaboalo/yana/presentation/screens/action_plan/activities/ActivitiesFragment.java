package com.icaboalo.yana.presentation.screens.action_plan.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.DayViewModel;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 13/08/16.
 */
public class ActivitiesFragment extends BaseFragment implements GenericDetailView<DayViewModel> {

    @Inject
    ActivitiesPresenter mActivitiesPresenter;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.rlProgress)
    RelativeLayout rlProgress;
    @Bind(R.id.rlRetry)
    RelativeLayout rlRetry;
    @Bind(R.id.rvActivity)
    RecyclerView rvActivity;

    @Override
    public void initialize() {
        getComponent(UserComponent.class).inject(this);
        mActivitiesPresenter.setView(this);
        mActivitiesPresenter.initialize("");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void renderItem(DayViewModel item) {
        showError(item.getDate());
        tvDate.setText(Html.fromHtml("<b>Día " + item.getDayNumber() + "</b>  |  " + item.getDate()));
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

    private void setupActivityRecycler(){

    }
}
