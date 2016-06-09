package com.icaboalo.yana.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.icaboalo.yana.R;
import com.icaboalo.yana.io.ApiClient;
import com.icaboalo.yana.io.model.ActivityApiModel;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.ui.adapter.ActivityRecyclerAdapter;
import com.icaboalo.yana.util.DividerItemDecorator;
import com.icaboalo.yana.util.OnEmotionSelected;
import com.icaboalo.yana.util.OnViewHolderClick;
import com.icaboalo.yana.util.VUtil;

import java.io.IOException;
import java.util.ArrayList;

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpActivityRecycler(getActivitiesFromRealm());
    }

    void setUpActivityRecycler(final ArrayList<ActivityModel> activityList){
        final ActivityRecyclerAdapter activityRecyclerAdapter = new ActivityRecyclerAdapter(getActivity(), activityList, new OnEmotionSelected() {
            @Override
            public void onSelect(int emotionId) {
                updateActivity(getActivityFromRealm(emotionId), emotionId);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mActivityRecycler.setAdapter(activityRecyclerAdapter);
        mActivityRecycler.setLayoutManager(linearLayoutManager);
        mActivityRecycler.addItemDecoration(new DividerItemDecorator(getActivity()));
    }

    ArrayList<ActivityModel> getActivitiesFromRealm(){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ActivityModel> query = realm.where(ActivityModel.class);

        RealmResults<ActivityModel> results = query.findAll();

        Log.d("REALM_RESULTS", results.toString());

        ArrayList<ActivityModel> activities = new ArrayList<>();
        for (ActivityModel activity: results){
            activities.add(activity);
        }
        return activities;
    }

    ActivityModel getActivityFromRealm(int activityId){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ActivityModel> query = realm.where(ActivityModel.class);
        ActivityModel result = query.equalTo("id", activityId).findAll().get(0);
        ActivityModel activity = new ActivityModel();
        activity.setId(result.getId());
        activity.setTitle(result.getTitle());
        activity.setDescription(result.getDescription());
        activity.setAnswer(result.getAnswer());
        return activity;
    }

    void updateActivity(ActivityModel activity, int activityId){
        Call<ActivityModel> call = ApiClient.getApiService().putActivity(VUtil.getToken(getActivity()), activity, activityId);
        call.enqueue(new Callback<ActivityModel>() {
            @Override
            public void onResponse(Call<ActivityModel> call, Response<ActivityModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Log.d("RETROFIT_ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ActivityModel> call, Throwable t) {

            }
        });
    }

}
