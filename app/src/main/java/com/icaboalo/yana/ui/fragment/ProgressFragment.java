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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.realm.DayModel;
import com.icaboalo.yana.ui.adapter.DayProgressRecyclerAdapter;
import com.icaboalo.yana.util.DividerItemDecorator;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by icaboalo on 08/06/16.
 */
public class ProgressFragment extends Fragment {

    TextView mCompleted, mIncomplete;
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
        mEmotionImage = (ImageView) view.findViewById(R.id.emotion_average_image);
        mCompletedProgress = (ProgressBar) view.findViewById(R.id.completed_progress_bar);
        mDayProgressRecycler = (RecyclerView) view.findViewById(R.id.day_progress_recycler);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCompleted.setText("" + getCompletedActivitiesFromRealm(null));
        mIncomplete.setText("" + getIncompleteActivitiesFromRealm(null));

        mCompletedProgress.setMax(getCompletedActivitiesFromRealm(null) + getIncompleteActivitiesFromRealm(null));
        mCompletedProgress.setProgress(getCompletedActivitiesFromRealm(null));

        setupDayProcessRecycler(getDaysFromRealm());

    }

    int getCompletedActivitiesFromRealm(@Nullable DayModel day){
        if (day == null){
            Realm realm = Realm.getDefaultInstance();
            return realm.where(ActivityModel.class).greaterThan("answer", 0).findAll().size();
        } else {
            return 0;
        }
    }

    int getIncompleteActivitiesFromRealm(@Nullable DayModel day){
        if (day == null){
            Realm realm = Realm.getDefaultInstance();
            return realm.where(ActivityModel.class).equalTo("answer", 0).findAll().size();
        } else {
            return 0;
        }
    }

    ArrayList<DayModel> getDaysFromRealm(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<DayModel> results = realm.where(DayModel.class).findAll();
        Log.d("REALM_RESULTS", results.toString());
        ArrayList<DayModel> dayList = new ArrayList<>();
        for (DayModel day: results){
            dayList.add(day);
        }
        DayModel day1 = new DayModel();
        day1.setNumber(1);
        day1.setDate("7 de Junio 2016");
        dayList.add(day1);
        DayModel day2 = new DayModel();
        day2.setNumber(2);
        day2.setDate("8 de Junio 2016");
        dayList.add(day2);
        return dayList;
    }

    void setupDayProcessRecycler(ArrayList<DayModel> dayList){
        DayProgressRecyclerAdapter dayProgressRecyclerAdapter = new DayProgressRecyclerAdapter(getActivity(), dayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mDayProgressRecycler.setAdapter(dayProgressRecyclerAdapter);
        mDayProgressRecycler.setLayoutManager(linearLayoutManager);
        mDayProgressRecycler.addItemDecoration(new DividerItemDecorator(getActivity()));
    }
}
