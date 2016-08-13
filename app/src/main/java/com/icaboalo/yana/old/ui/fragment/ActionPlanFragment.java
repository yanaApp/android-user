package com.icaboalo.yana.old.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.icaboalo.yana.R;
import com.icaboalo.yana.old.io.ApiClient;
import com.icaboalo.yana.old.io.model.ActivityApiModel;
import com.icaboalo.yana.old.realm.ActivityModel;
import com.icaboalo.yana.old.ui.adapter.ActivityRecyclerAdapter;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.RealmUtils;
import com.icaboalo.yana.util.VUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.icaboalo.yana.old.ui.adapter.ActivityRecyclerAdapter.*;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActionPlanFragment extends Fragment {

    @Bind(R.id.rvActivity)
    RecyclerView mActivityRecycler;
    @Bind(R.id.tvDate)
    TextView mActivityDate;
    @Bind(R.id.llContainer)
    LinearLayout llContainer;
    @Bind(R.id.llNoActionPlan)
    LinearLayout llNoActionPlan;
    ActivityRecyclerAdapter mActivityRecyclerAdapter;

    private Realm mRealmInstance;

    public ActionPlanFragment() {
        setHasOptionsMenu(true);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRealmInstance = Realm.getDefaultInstance();

        if (RealmUtils.getCurrentActionPlan(mRealmInstance) != null){
            llContainer.setVisibility(View.VISIBLE);
            llNoActionPlan.setVisibility(View.GONE);
            mActivityDate.setText(Html.fromHtml("<b>Día " + RealmUtils.getCurrentDayFromRealm(mRealmInstance).getNumber() + "</b>  |  " + RealmUtils.getCurrentDayFromRealm(mRealmInstance).getDate()));
            setUpActivityRecycler(RealmUtils.getActivitiesFromRealm(mRealmInstance, RealmUtils.getCurrentDayFromRealm(mRealmInstance)));
        } else {
            llContainer.setVisibility(View.GONE);
            llNoActionPlan.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "No action plan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mRealmInstance.close();
    }

    void setUpActivityRecycler(final ArrayList<ActivityModel> activityList){
        mActivityRecyclerAdapter = new ActivityRecyclerAdapter(getActivity(), activityList);
        mActivityRecyclerAdapter.setEmotionSelectedListener(new OnEmotionSelected() {
            @Override
            public void onSelect(ActivityModel activity, int previousAnswer, int newAnswer) {
                showSnackBar(activity, previousAnswer, newAnswer);
            }
        });
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mActivityRecycler.setLayoutManager(linearLayoutManager);
        mActivityRecyclerAdapter.setOnExpandListener(new OnExpandListener() {
            @Override
            public void onExpand(int position, boolean expanded) {
                if (expanded){
                    mActivityRecycler.smoothScrollToPosition(position);
                }
            }
        });
        mActivityRecycler.setAdapter(mActivityRecyclerAdapter);
//        mActivityRecycler.addItemDecoration(new DividerItemDecorator(getActivity()));
    }

    void updateActivity(String token, int answer, int activityId){
        HashMap<String, Integer> answerDict = new HashMap<>();
        answerDict.put("answer", answer);
        Call<ActivityApiModel> call = ApiClient.getApiService().updateActivity(token, answerDict, activityId);
        call.enqueue(new Callback<ActivityApiModel>() {
            @Override
            public void onResponse(Call<ActivityApiModel> call, Response<ActivityApiModel> response) {
                if (response.isSuccessful()){
                    Log.d("RETROFIT_SUCCESS", "success");
                } else {
                    try {
                        Log.d("RETROFIT_ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ActivityApiModel> call, Throwable t) {
                Log.d("RETROFIT_FAILURE", t.toString());
            }
        });
    }

    void showSnackBar(final ActivityModel activity, final int previousAnswer, final int newAnswer){
        Snackbar.make(getView(), "Changed emotion from " + VUtil.answerToText(previousAnswer) + " to " + VUtil.answerToText(newAnswer), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                switch (event){
                    case DISMISS_EVENT_ACTION:
                        Log.d("SNACKBAR", "clicked");
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        activity.setAnswer(previousAnswer);
                        mActivityRecyclerAdapter.notifyDataSetChanged();
                        realm.commitTransaction();
                        break;

                    case DISMISS_EVENT_TIMEOUT:
                        Log.d("SNACKBAR", "timeout");
                        updateActivity(PrefUtils.getToken(getActivity()), newAnswer, activity.getId());
                        break;
                }
            }
        }).show();
    }

}
