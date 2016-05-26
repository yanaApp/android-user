package com.icaboalo.yana.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icaboalo.yana.R;
import com.icaboalo.yana.io.model.ActivityApiModel;
import com.icaboalo.yana.ui.adapter.ActivityRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActionPlanFragment extends Fragment {

    RecyclerView mActivityRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivityRecycler = (RecyclerView) view.findViewById(R.id.activity_recycler);
        setUpActivityRecycler(createList());
    }

    ArrayList<ActivityApiModel> createList(){
        ArrayList<ActivityApiModel> activityList = new ArrayList<>();
        activityList.add(new ActivityApiModel("Sonreír antes de levantarse", ""));
        activityList.add(new ActivityApiModel("Desayunar", ""));
        activityList.add(new ActivityApiModel("Bañarse", ""));
        activityList.add(new ActivityApiModel("Comer", ""));
        activityList.add(new ActivityApiModel("Lammarle a un ser querido", ""));
        activityList.add(new ActivityApiModel("Cenar", ""));
        return activityList;
    }

    void setUpActivityRecycler(ArrayList<ActivityApiModel> activityList){
        ActivityRecyclerAdapter activityRecyclerAdapter = new ActivityRecyclerAdapter(getActivity(), activityList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mActivityRecycler.setAdapter(activityRecyclerAdapter);
        mActivityRecycler.setLayoutManager(linearLayoutManager);
    }
}
