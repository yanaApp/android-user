package com.icaboalo.yana.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.icaboalo.yana.R;
import com.icaboalo.yana.io.model.ActivityApiModel;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.ui.adapter.ActivityRecyclerAdapter;
import com.icaboalo.yana.util.OnDialogButtonClick;
import com.icaboalo.yana.util.OnViewHolderClick;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.icaboalo.yana.R.string.label_activity_complete;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActionPlanFragment extends Fragment {

    RecyclerView mActivityRecycler;
    OnDialogButtonClick mDialogButtonClick;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDialogButtonClick = (OnDialogButtonClick) context;
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
        setUpActivityRecycler(getFromRealm());
    }

    void setUpActivityRecycler(final ArrayList<ActivityModel> activityList){
        ActivityRecyclerAdapter activityRecyclerAdapter = new ActivityRecyclerAdapter(getActivity(), activityList, new OnViewHolderClick() {
            @Override
            public void onClick(View view, int position) {
                ActivityModel activity = activityList.get(position);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mActivityRecycler.setAdapter(activityRecyclerAdapter);
        mActivityRecycler.setLayoutManager(linearLayoutManager);
    }

    ArrayList<ActivityModel> getFromRealm(){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ActivityModel> query = realm.where(ActivityModel.class);

        RealmResults<ActivityModel> results = query.findAll();

        ArrayList<ActivityModel> activities = new ArrayList<>();
        for (ActivityModel activity: results){
            activities.add(activity);
        }
        return activities;
    }

}
