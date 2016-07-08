package com.icaboalo.yana.ui.fragment;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.icaboalo.yana.R;
import com.icaboalo.yana.realm.ActionPlanModel;
import com.icaboalo.yana.realm.DayModel;
import com.icaboalo.yana.ui.adapter.DayProgressRecyclerAdapter;
import com.icaboalo.yana.util.DividerItemDecorator;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.RealmUtils;
import com.icaboalo.yana.util.VUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by icaboalo on 08/06/16.
 */
public class ProgressFragment extends Fragment {

    TextView mCompleted, mIncomplete;
    Spinner mActionPlanSpinner;
    ImageView mEmotionImage;
    ProgressBar mCompletedProgress;
    RecyclerView mDayProgressRecycler;

    int mTutorialCount = 0;


    @Bind(R.id.llcompleted)
    LinearLayout llCompleted;
    @Bind(R.id.llIncomplete)
    LinearLayout llIncomplete;
    @Bind(R.id.llAverage)
    LinearLayout llAverage;

    private Realm mRealmInstance;

    @Override
    public void onStart() {
        super.onStart();
        mRealmInstance = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
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
        setupActionPlanSpinner(RealmUtils.getActionPlansFromRealm(mRealmInstance));
        if (!PrefUtils.isProgressTourComplete(getActivity())){
            startTutorial();
        }
        if (PrefUtils.isProgressFirstTime(getActivity()) && PrefUtils.isProgressTourComplete(getActivity())){
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

    @Override
    public void onStop() {
        super.onStop();
        mRealmInstance.close();
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
        ArrayList<DayModel> mCompletedDayList = RealmUtils.getDaysFromRealm(mRealmInstance, actionPlan, true);
        int completedActivities = RealmUtils.getCompletedActivitiesFromRealm(mRealmInstance, mCompletedDayList);
        int incompleteActivities = RealmUtils.getIncompleteActivitiesFromRealm(mRealmInstance, mCompletedDayList);

        mCompleted.setText("" + completedActivities);
        mIncomplete.setText("" + incompleteActivities);

        setupDayProcessRecycler(mCompletedDayList);

        mCompletedProgress.setMax(completedActivities + incompleteActivities);
        mCompletedProgress.setProgress(completedActivities);
        VUtil.setEmotionImage(getActivity(), RealmUtils.getEmotionAverageFromRealm(mRealmInstance, actionPlan), mEmotionImage);
    }


    void setupDayProcessRecycler(ArrayList<DayModel> dayList){
        ArrayList<Integer> completedActivities = new ArrayList<>();
        ArrayList<Integer> incompleteActivities = new ArrayList<>();
        ArrayList<Integer> emotionAverage = new ArrayList<>();
        for (DayModel day: dayList){
            completedActivities.add(RealmUtils.getCompletedActivitiesFromRealm(mRealmInstance, day));
            incompleteActivities.add(RealmUtils.getIncompleteActivitiesFromRealm(mRealmInstance, day));
            emotionAverage.add(RealmUtils.getEmotionAverageFromRealm(mRealmInstance, day));

        }

        DayProgressRecyclerAdapter dayProgressRecyclerAdapter = new DayProgressRecyclerAdapter(getActivity(), dayList, completedActivities, incompleteActivities, emotionAverage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mDayProgressRecycler.setAdapter(dayProgressRecyclerAdapter);
        mDayProgressRecycler.setLayoutManager(linearLayoutManager);
        mDayProgressRecycler.addItemDecoration(new DividerItemDecorator(getActivity()));
    }

    void startTutorial(){
        final ShowcaseView showcaseView = new ShowcaseView.Builder(getActivity())
                .setTarget(Target.NONE)
                .singleShot(98)
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcase)
                .build();
        showcaseView.setContentTitle("Welcome to your Progress!");
        showcaseView.setContentText("In here you can check your progress in the application");
        showcaseView.overrideButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mTutorialCount){
                    case 0:
                        showcaseView.setShowcase(new ViewTarget(llCompleted), true);
                        showcaseView.setContentTitle("TITLE");
                        showcaseView.setContentText(getActivity().getString(R.string.cupcake_ipsum));
                        break;

                    case 1:
                        showcaseView.setShowcase(new ViewTarget(llIncomplete), true);
                        showcaseView.setContentTitle("TITLE");
                        showcaseView.setContentText(getActivity().getString(R.string.cupcake_ipsum));
                        break;

                    case 2:
                        showcaseView.setShowcase(new ViewTarget(llAverage), true);
                        showcaseView.setContentTitle("TITLE");
                        showcaseView.setContentText(getActivity().getString(R.string.cupcake_ipsum));
                        break;

                    case 3:
                        showcaseView.setShowcase(new ViewTarget(mDayProgressRecycler.getChildAt(0)), true);
                        showcaseView.setContentTitle("TITLE");
                        showcaseView.setContentText(getActivity().getString(R.string.cupcake_ipsum));
                        break;

                    case 4:
                        showcaseView.hide();
                        break;
                }
                mTutorialCount++;
            }
        });
    }
}
