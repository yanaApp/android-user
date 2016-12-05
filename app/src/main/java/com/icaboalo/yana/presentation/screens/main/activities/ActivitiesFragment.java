package com.icaboalo.yana.presentation.screens.main.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.other.ManagerPreference;
import com.icaboalo.yana.other.YanaPreferences;
import com.icaboalo.yana.presentation.di.component.UserComponent;
import com.icaboalo.yana.presentation.screens.BaseFragment;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemInfo;
import com.icaboalo.yana.presentation.screens.evaluation.EvaluationActivity;
import com.icaboalo.yana.presentation.screens.main.activities.ActivitiesRecyclerAdapter.ActivitiesListener;
import com.icaboalo.yana.presentation.screens.view_model.ActivityViewModel;
import com.icaboalo.yana.presentation.screens.view_model.DayViewModel;
import com.icaboalo.yana.util.Utils;
import com.icaboalo.yana.util.VUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author icaboalo on 13/08/16.
 */
public class ActivitiesFragment extends BaseFragment implements ActivityView, ActivitiesListener {

    @Inject
    ActivitiesPresenter mActivitiesPresenter;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.rl_retry)
    RelativeLayout rlRetry;
    @BindView(R.id.llNoActionPlan)
    LinearLayout llNoActionPlan;
    @BindView(R.id.llContainer)
    LinearLayout llContainer;
    @BindView(R.id.rvActivity)
    RecyclerView rvActivity;
    private ActivitiesRecyclerAdapter mActivitiesRecyclerAdapter;
    int retryCount = 0;

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
        setupActivityRecycler();
    }

    @Override
    public void renderItem(DayViewModel item) {
        if (!item.isEmpty()){
            ManagerPreference.getInstance().set(YanaPreferences.CURRENT_DAY, item.getDayNumber());

            tvDate.setText(Html.fromHtml("<b>Día " + item.getDayNumber() + "</b>  |  " +
                    Utils.transformDateToText(item.getDate(), "dd-MM-yyyy", "MMMM dd, yyyy")));

            List<ItemInfo> itemList = new ArrayList<>();
            Collections.sort(item.getActivityList(), (lhs, rhs) ->
                    String.valueOf(lhs.getId()).compareToIgnoreCase(String.valueOf(rhs.getId())));
            for (ActivityViewModel activityViewModel : item.getActivityList()){
                itemList.add(new ItemInfo<>(activityViewModel, ItemInfo.SECTION_ITEM));
            }
            mActivitiesRecyclerAdapter.setDataList(itemList);
            if (retryCount < 2) {
                mActivitiesPresenter.getItemDetails();
                retryCount ++;
            }
        }
        else {
            llContainer.setVisibility(View.GONE);
            llNoActionPlan.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void saveEmotionSuccess(ActivityViewModel activityViewModel) {
//        mActivitiesPresenter.getItemDetails();
    }

    @Override
    public void saveEmotionError() {
        mActivitiesRecyclerAdapter.notifyDataSetChanged();
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

    @Override
    public void onExpand(int position, boolean expanded) {
        if (expanded){
            rvActivity.smoothScrollToPosition(position);
        }
    }

    @Override
    public void onSelect(ActivityViewModel activityViewModel, int answer) {
        showSnackBar(activityViewModel, answer);
    }

//    TODO -> set texts
    @OnClick(R.id.btCreateActionPlan)
    void createNewActionPlan(){
//        navigator.navigateTo(getApplicationContext(), new Intent(getApplicationContext(), EvaluationActivity.class)
//                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Bla bla bla...")
                .setMessage(R.string.cupcake_ipsum)
                .setPositiveButton("Ok", (dialog, which) -> navigator.navigateTo(getApplicationContext(), EvaluationActivity.getCallingIntent(getApplicationContext(), 0)))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create();
        alertDialog.show();
    }

    private void setupActivityRecycler(){
        mActivitiesRecyclerAdapter = new ActivitiesRecyclerAdapter(getActivity(), new ArrayList<>()){
            @Override
            public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType){
                    default:
                        return new ActivityViewHolder(mLayoutInflater.inflate(R.layout.item_activity_adapter, parent, false));
                }
            }
        };
        mActivitiesRecyclerAdapter.setOnEmotionSelectedListener(this);
        rvActivity.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvActivity.setAdapter(mActivitiesRecyclerAdapter);
    }

    private void showSnackBar(ActivityViewModel activityViewModel, int answer){
        Snackbar.make(rvActivity, "Changed emotion from " + VUtil.answerToText(activityViewModel.getAnswer()) + " to " +
                VUtil.answerToText(answer), Snackbar.LENGTH_SHORT).setAction("Undo", v -> {
                }).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                switch (event){
                    case DISMISS_EVENT_ACTION:
                        mActivitiesRecyclerAdapter.notifyDataSetChanged();
                        break;
                    default:
                        mActivitiesPresenter.attemptSaveEmotion(activityViewModel, answer);
                        break;
                }
            }
        }).show();
    }
}
