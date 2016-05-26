package com.icaboalo.yana.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.io.model.ActivityApiModel;

import java.util.ArrayList;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActivityRecyclerAdapter extends RecyclerView.Adapter<ActivityRecyclerAdapter.ActivityViewHolder> {

    Context mContext;
    ArrayList<ActivityApiModel> mActivityList;
    LayoutInflater mInflater;

    public ActivityRecyclerAdapter(Context context, ArrayList<ActivityApiModel> activityList) {
        this.mContext = context;
        this.mActivityList = activityList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_action_plan_adapter, parent, false);
        return new ActivityViewHolder(view, R.id.activity_title, R.id.activity_completed);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        ActivityApiModel activity = mActivityList.get(position);

        holder.mActivityTitle.setText(activity.getName());
        holder.mCompletedCheckbox.setChecked(activity.isCompleted());
    }

    @Override
    public int getItemCount() {
        return mActivityList.size();
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder{

        TextView mActivityTitle;
        CheckBox mCompletedCheckbox;
        public ActivityViewHolder(View itemView, int activityTitleId, int activityCompletedId) {
            super(itemView);
            mActivityTitle = (TextView) itemView.findViewById(activityTitleId);
            mCompletedCheckbox = (CheckBox) itemView.findViewById(activityCompletedId);
        }
    }
}
