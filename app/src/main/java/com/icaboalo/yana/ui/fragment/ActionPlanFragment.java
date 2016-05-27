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

import com.icaboalo.yana.R;
import com.icaboalo.yana.io.model.ActivityApiModel;
import com.icaboalo.yana.ui.adapter.ActivityRecyclerAdapter;
import com.icaboalo.yana.ui.adapter.OnDialogButtonClick;
import com.icaboalo.yana.ui.adapter.OnViewHolderClick;

import java.util.ArrayList;

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
        ActivityRecyclerAdapter activityRecyclerAdapter = new ActivityRecyclerAdapter(getActivity(), activityList, new OnViewHolderClick() {
            @Override
            public void onClick(View view, int position) {
                ActivityApiModel activity = createList().get(position);
                showDialog(activity);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mActivityRecycler.setAdapter(activityRecyclerAdapter);
        mActivityRecycler.setLayoutManager(linearLayoutManager);
    }

    void showDialog(final ActivityApiModel activity){
        AlertDialog.Builder activityDetailDialog = new AlertDialog.Builder(getActivity());
        activityDetailDialog.setTitle(activity.getName());
        activityDetailDialog.setMessage(activity.getDescription());
        activityDetailDialog.setCancelable(false);
        activityDetailDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialogButtonClick.onPositiveClick(dialog, activity, 1);
            }
        });
        activityDetailDialog.setNeutralButton("COMPLETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialogButtonClick.onNeutralClick(dialog, activity, label_activity_complete);
            }
        });
        activityDetailDialog.show();
    }
}
