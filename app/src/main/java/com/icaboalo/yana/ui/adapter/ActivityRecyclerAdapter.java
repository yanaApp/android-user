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
import com.icaboalo.yana.util.OnViewHolderClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActivityRecyclerAdapter extends RecyclerView.Adapter<ActivityRecyclerAdapter.ActivityViewHolder> {

    Context mContext;
    ArrayList<ActivityApiModel> mActivityList;
    LayoutInflater mInflater;
    OnViewHolderClick viewHolderClick;

    public ActivityRecyclerAdapter(Context context, ArrayList<ActivityApiModel> activityList, OnViewHolderClick onViewHolderClick) {
        this.mContext = context;
        this.mActivityList = activityList;
        this.viewHolderClick = onViewHolderClick;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_action_plan_adapter, parent, false);
        return new ActivityViewHolder(view, R.id.activity_title, R.id.activity_image, R.id.activity_completed, viewHolderClick);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        ActivityApiModel activity = mActivityList.get(position);

        holder.mActivityTitle.setText(activity.getName());
        holder.mCompletedCheckbox.setChecked(activity.isCompleted());
        holder.setImage(activity.getmImage());
    }

    @Override
    public int getItemCount() {
        return mActivityList.size();
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mActivityTitle;
        CheckBox mCompletedCheckbox;
        CircleImageView mActivityImage;
        OnViewHolderClick viewHolderClick;


        public ActivityViewHolder(View itemView, int activityTitleId, int activityImageId, int activityCompletedId, OnViewHolderClick onViewHolderClick) {
            super(itemView);
            this.mActivityTitle = (TextView) itemView.findViewById(activityTitleId);
            this.mCompletedCheckbox = (CheckBox) itemView.findViewById(activityCompletedId);
            this.mActivityImage = (CircleImageView) itemView.findViewById(activityImageId);
            this.viewHolderClick = onViewHolderClick;
            itemView.setOnClickListener(this);
        }

        void setImage(String url){
            if (!url.isEmpty() || url != null){
                Picasso.with(mContext).load(url).into(mActivityImage);
            }
        }

        @Override
        public void onClick(View v) {
            viewHolderClick.onClick(v, getAdapterPosition());
        }
    }
}
