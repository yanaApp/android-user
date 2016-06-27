package com.icaboalo.yana.ui.fragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.icaboalo.yana.realm.DayModel;
import com.icaboalo.yana.ui.adapter.DayProgressRecyclerAdapter;
import com.icaboalo.yana.util.DividerItemDecorator;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.RealmUtils;
import com.icaboalo.yana.util.VUtil;

import java.util.ArrayList;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by icaboalo on 08/06/16.
 */
public class ProgressFragment extends Fragment implements ScreenShotable{

    TextView mCompleted, mIncomplete;
    Spinner mActionPlanSpinner;
    ImageView mEmotionImage;
    ProgressBar mCompletedProgress;
    RecyclerView mDayProgressRecycler;

    View containerView;
    Bitmap bitmap;


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

        containerView = view.findViewById(R.id.container_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupActionPlanSpinner(RealmUtils.getActionPlansFromRealm());
        if (PrefUtils.isProgressFirstTime(getActivity())){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setMessage(getActivity().getString(R.string.cupcake_ipsum));
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    PrefUtils.setProgressFirstTime(getActivity(), false);
                    dialog.dismiss();
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
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
                setInformation(actionPlanList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void setInformation(ActionPlanModel actionPlan){
        ArrayList<DayModel> mCompletedDayList = RealmUtils.getDaysFromRealm(actionPlan, true);
        int completedActivities = RealmUtils.getCompletedActivitiesFromRealm(mCompletedDayList);
        int incompleteActivities = RealmUtils.getIncompleteActivitiesFromRealm(mCompletedDayList);

        mCompleted.setText("" + completedActivities);
        mIncomplete.setText("" + incompleteActivities);

        setupDayProcessRecycler(mCompletedDayList);

        mCompletedProgress.setMax(completedActivities + incompleteActivities);
        mCompletedProgress.setProgress(completedActivities);
        VUtil.setEmotionImage(getActivity(), RealmUtils.getEmotionAverageFromRealm(null), mEmotionImage);
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

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                ProgressFragment.this.bitmap = bitmap;
            }
        };

        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
//        return null;
        return bitmap;
    }
}
