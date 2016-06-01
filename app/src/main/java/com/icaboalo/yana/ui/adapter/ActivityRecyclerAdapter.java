package com.icaboalo.yana.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    OnViewHolderClick viewHolderClick, mEmotionImageClick;
    private int nExpandedPosition = -1;


    public ActivityRecyclerAdapter(Context context, ArrayList<ActivityApiModel> activityList, OnViewHolderClick onViewHolderClick, OnViewHolderClick emotionImageClick) {
        this.mContext = context;
        this.mActivityList = activityList;
        this.viewHolderClick = onViewHolderClick;
        this.mEmotionImageClick = emotionImageClick;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_action_plan_adapter, parent, false);
        return new ActivityViewHolder(view, viewHolderClick, mEmotionImageClick);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        ActivityApiModel activity = mActivityList.get(position);

        if (position == nExpandedPosition) {
            holder.mExpandedLayout.setVisibility(View.VISIBLE);
            holder.mNormalLayout.setVisibility(View.GONE);
        } else {
            holder.mExpandedLayout.setVisibility(View.GONE);
            holder.mNormalLayout.setVisibility(View.VISIBLE);
        }

        holder.setTitle(activity.getName());
        holder.setDescription(activity.getDescription());
        holder.setEmotionImage(activity.getAnswer());
    }

    @Override
    public int getItemCount() {
        return mActivityList.size();
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mActivityTitle, mActivityDescription;
        RelativeLayout mNormalLayout, mExpandedLayout;
        CircleImageView mActivityImage;
        ImageView mEmotionImage;
        OnViewHolderClick viewHolderClick, mEmotionImageClick;

        public ActivityViewHolder(View itemView, OnViewHolderClick onViewHolderClick, OnViewHolderClick imageClick) {
            super(itemView);
            this.mNormalLayout = (RelativeLayout) itemView.findViewById(R.id.normal_layout);
            this.mExpandedLayout = (RelativeLayout) itemView.findViewById(R.id.expanded_layout);
            this.viewHolderClick = onViewHolderClick;
            this.mEmotionImageClick = imageClick;
            itemView.setOnClickListener(this);
        }

        void setActivityImage(String url){
            int[] imageIds = {R.id.activity_image, R.id.activity_image_expanded};
            for (int id: imageIds){
                mActivityImage = (CircleImageView) itemView.findViewById(id);
                if (!url.isEmpty()){
                    Picasso.with(mContext).load(url).into(mActivityImage);
                }
                else{
                    mActivityImage.setImageDrawable(null);
                }
            }
        }

        void setEmotionImage(int answer){
            int[] imageIds = {R.id.image_emotion, R.id.image_emotion_expanded};
            for (int id: imageIds){
                mEmotionImage = (ImageView) itemView.findViewById(id);
                if (answer > 0){
                    switch (answer){
                        case 1:
                            Picasso.with(mContext).load(R.mipmap.ic_launcher).into(mEmotionImage);
                            break;
                        case 2:
                            Picasso.with(mContext).load(answer).into(mEmotionImage);
                            break;
                        case 3:
                            Picasso.with(mContext).load(answer).into(mEmotionImage);
                            break;
                        case 4:
                            Picasso.with(mContext).load(answer).into(mEmotionImage);
                            break;
                        case 5:
                            Picasso.with(mContext).load(answer).into(mEmotionImage);
                            break;
                    }
                }
                else{
                    mEmotionImage.setImageDrawable(null);
                }
            }
            mEmotionImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEmotionImageClick.onClick(v, getAdapterPosition());
                }
            });
        }

        void setTitle(String text){
            int[] titleIds = {R.id.activity_title, R.id.activity_title_expanded};
            for (int id: titleIds){
                mActivityTitle = (TextView) itemView.findViewById(id);
                mActivityTitle.setText(text);
            }
        }

        void setDescription(String description){
            mActivityDescription = (TextView) itemView.findViewById(R.id.activity_description_expanded);
            mActivityDescription.setText(description);
        }

        @Override
        public void onClick(View v) {
            viewHolderClick.onClick(v, getAdapterPosition());

            // Check for an expanded view, collapse if you find one
            if (nExpandedPosition >= 0) {
                int prev = nExpandedPosition;
                notifyItemChanged(prev);
            }

            if (nExpandedPosition == getAdapterPosition()){
                nExpandedPosition = -1;
                notifyItemChanged(getAdapterPosition());
            }
            else{
                // Set the current position to "expanded"
                nExpandedPosition = getAdapterPosition();
                notifyItemChanged(nExpandedPosition);
            }

        }
    }
}
