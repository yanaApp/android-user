package com.icaboalo.yana.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.realm.ActionPlanModel;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.realm.DayModel;
import com.icaboalo.yana.ui.adapter.DayProgressRecyclerAdapter;
import com.icaboalo.yana.util.DividerItemDecorator;
import com.icaboalo.yana.util.RealmUtils;
import com.icaboalo.yana.util.VUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by icaboalo on 08/06/16.
 */
public class ProgressFragment extends Fragment {

    TextView mCompleted, mIncomplete;
    Spinner mActionPlanSpinner;
    ImageView mEmotionImage;
    ProgressBar mCompletedProgress;
    RecyclerView mDayProgressRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCompleted = (TextView) view.findViewById(R.id.completed_text);
        mIncomplete = (TextView) view.findViewById(R.id.incomplete_text);
        mActionPlanSpinner = (Spinner) view.findViewById(R.id.action_plan_spinner);
        mEmotionImage = (ImageView) view.findViewById(R.id.emotion_average_image);
        mCompletedProgress = (ProgressBar) view.findViewById(R.id.completed_progress_bar);
        mDayProgressRecycler = (RecyclerView) view.findViewById(R.id.day_progress_recycler);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCompleted.setText("" + RealmUtils.getCompletedActivitiesFromRealm(null));
        mIncomplete.setText("" + RealmUtils.getIncompleteActivitiesFromRealm(null));

        mCompletedProgress.setMax(RealmUtils.getCompletedActivitiesFromRealm(null) + RealmUtils.getIncompleteActivitiesFromRealm(null));
        mCompletedProgress.setProgress(RealmUtils.getCompletedActivitiesFromRealm(null));
        VUtil.setEmotionImage(getActivity(), RealmUtils.getEmotionAverageFromRealm(null), mEmotionImage);

        setupActionPlanSpinner(RealmUtils.getActionPlansFromRealm());
        RealmUtils.getEmotionAverageFromRealm(null);

    }

    void setupActionPlanSpinner(final ArrayList<ActionPlanModel> actionPlanList){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item);
        for (ActionPlanModel actionPlan: actionPlanList){
            arrayAdapter.add(actionPlan.getInitialDate() + " - " + actionPlan.getFinalDate());
        }
        mActionPlanSpinner.setAdapter(arrayAdapter);
        mActionPlanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupDayProcessRecycler(RealmUtils.getDaysFromRealm(actionPlanList.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    void setupDayProcessRecycler(ArrayList<DayModel> dayList){
        ArrayList<Integer> completedActivities = new ArrayList<>();
        ArrayList<Integer> incompleteActivities = new ArrayList<>();
        ArrayList<Integer> emotionAverage = new ArrayList<>();
        for (DayModel day: dayList){
            completedActivities.add(RealmUtils.getCompletedActivitiesFromRealm(day));
            incompleteActivities.add(RealmUtils.getIncompleteActivitiesFromRealm(day));
            emotionAverage.add(RealmUtils.getEmotionAverageFromRealm(day));

        }

        DayProgressRecyclerAdapter dayProgressRecyclerAdapter = new DayProgressRecyclerAdapter(getActivity(), dayList, completedActivities, incompleteActivities, emotionAverage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mDayProgressRecycler.setAdapter(dayProgressRecyclerAdapter);
        mDayProgressRecycler.setLayoutManager(linearLayoutManager);
        mDayProgressRecycler.addItemDecoration(new DividerItemDecorator(getActivity()));
    }
}
