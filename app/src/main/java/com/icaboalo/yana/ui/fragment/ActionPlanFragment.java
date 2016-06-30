package com.icaboalo.yana.ui.fragment;

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
import android.widget.TextView;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.io.ApiClient;
import com.icaboalo.yana.io.model.ActivityApiModel;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.realm.DayModel;
import com.icaboalo.yana.ui.adapter.ActivityRecyclerAdapter;
import com.icaboalo.yana.util.DividerItemDecorator;
import com.icaboalo.yana.util.OnEmotionSelected;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.RealmUtils;
import com.icaboalo.yana.util.VUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActionPlanFragment extends Fragment {

    RecyclerView mActivityRecycler;
    TextView mActivityDate;
    ActivityRecyclerAdapter mActivityRecyclerAdapter;

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
        mActivityRecycler = (RecyclerView) view.findViewById(R.id.activity_recycler);
        mActivityDate = (TextView) view.findViewById(R.id.activity_date);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivityDate.setText(Html.fromHtml("<b>Día " + RealmUtils.getCurrentDayFromRealm().getNumber() + "</b>  |  " + RealmUtils.getCurrentDayFromRealm().getDate()));
        setUpActivityRecycler(RealmUtils.getActivitiesFromRealm(RealmUtils.getCurrentDayFromRealm()));
    }

    void setUpActivityRecycler(final ArrayList<ActivityModel> activityList){
        mActivityRecyclerAdapter = new ActivityRecyclerAdapter(getActivity(), activityList, new OnEmotionSelected() {
            @Override
            public void onSelect(final ActivityModel activity, final int previousAnswer, int newAnswer) {
                Log.d("SELECTED", activity.toString());
                showSnackBar(activity, previousAnswer, newAnswer);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mActivityRecycler.setAdapter(mActivityRecyclerAdapter);
        mActivityRecycler.setLayoutManager(linearLayoutManager);
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
        Snackbar.make(getView(), "Changed emotion from " + previousAnswer + " to " + newAnswer, Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
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
