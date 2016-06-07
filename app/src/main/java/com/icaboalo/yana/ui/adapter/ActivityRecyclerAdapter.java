package com.icaboalo.yana.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.util.OnEmotionSelected;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActivityRecyclerAdapter extends RecyclerView.Adapter<ActivityRecyclerAdapter.ActivityViewHolder> {

    Context mContext;
    ArrayList<ActivityModel> mActivityList;
    LayoutInflater mInflater;
    OnEmotionSelected mOnEmotionSelected;
    private int emotionExpandedPosition = -1, descriptionExpandedPosition = -1;


    public ActivityRecyclerAdapter(Context context, ArrayList<ActivityModel> activityList, OnEmotionSelected onEmotionSelected) {
        this.mContext = context;
        this.mActivityList = activityList;
        this.mOnEmotionSelected = onEmotionSelected;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_action_plan_adapter, parent, false);
        return new ActivityViewHolder(view, mOnEmotionSelected);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        ActivityModel activity = mActivityList.get(position);

        if (position == emotionExpandedPosition) {
            holder.mEmotionLayout.setVisibility(View.VISIBLE);
            holder.mDescriptionLayout.setVisibility(View.GONE);
        } else if (position == descriptionExpandedPosition) {
            holder.mDescriptionLayout.setVisibility(View.VISIBLE);
            holder.mEmotionLayout.setVisibility(View.GONE);
        } else {
            holder.mEmotionLayout.setVisibility(View.GONE);
            holder.mDescriptionLayout.setVisibility(View.GONE);
        }

        holder.setTitle(activity.getTitle());
        holder.setDescription(activity.getDescription());
        holder.setEmotionImage(activity.getAnswer());

        if (activity.getTitle().equals("Baño")) {
            holder.setActivityColor(mContext.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return mActivityList.size();
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mActivityTitle, mActivityDescription;
        View mActivityColor;
        RelativeLayout mNormalLayout, mDescriptionLayout, mEmotionLayout;
        ImageView mEmotionImage, mDescriptionImage, mVerySadImage, mSadImage, mNormalImage, mHappyImage, mVeryHappyImage;
        OnEmotionSelected mEmotionSelected;

        public ActivityViewHolder(View itemView, OnEmotionSelected onEmotionSelected) {
            super(itemView);
            this.mNormalLayout = (RelativeLayout) itemView.findViewById(R.id.normal_layout);
            this.mDescriptionLayout = (RelativeLayout) itemView.findViewById(R.id.description_layout);
            this.mEmotionLayout = (RelativeLayout) itemView.findViewById(R.id.emotion_layout);
            this.mVerySadImage = (ImageView) itemView.findViewById(R.id.very_sad);
            this.mSadImage = (ImageView) itemView.findViewById(R.id.sad);
            this.mNormalImage = (ImageView) itemView.findViewById(R.id.normal);
            this.mHappyImage = (ImageView) itemView.findViewById(R.id.happy);
            this.mVeryHappyImage = (ImageView) itemView.findViewById(R.id.very_happy);
            this.mEmotionImage = (ImageView) itemView.findViewById(R.id.image_emotion);
            this.mDescriptionImage = (ImageView) itemView.findViewById(R.id.image_description);
            mVerySadImage.setOnClickListener(this);
            mSadImage.setOnClickListener(this);
            mNormalImage.setOnClickListener(this);
            mHappyImage.setOnClickListener(this);
            mVeryHappyImage.setOnClickListener(this);
            this.mEmotionSelected = onEmotionSelected;
            this.mEmotionImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emotionExpand();
                }
            });
            this.mDescriptionImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    descriptionExpand();
                }
            });
        }

        void setEmotionImage(int answer) {
            if (answer > 0) {
                switch (answer) {
                    case 1:
                        Picasso.with(mContext).load(R.drawable.very_sad_32).into(mEmotionImage);
                        break;

                    case 2:
                        Picasso.with(mContext).load(R.drawable.sad_32).into(mEmotionImage);
                        break;

                    case 3:
                        Picasso.with(mContext).load(R.drawable.normal_32).into(mEmotionImage);
                        break;

                    case 4:
                        Picasso.with(mContext).load(R.drawable.happy_32).into(mEmotionImage);
                        break;

                    case 5:
                        Picasso.with(mContext).load(R.drawable.very_happy_32).into(mEmotionImage);
                        break;

                }
            } else {
                mEmotionImage.setImageDrawable(null);
            }

        }

        void setTitle(String text) {
            mActivityTitle = (TextView) itemView.findViewById(R.id.activity_title);
            mActivityTitle.setText(text);
        }

        void setDescription(String description) {
            mActivityDescription = (TextView) itemView.findViewById(R.id.activity_description);
            if (description.isEmpty() || description == null) {
                mActivityDescription.setVisibility(View.GONE);
            } else {
                mActivityDescription.setVisibility(View.VISIBLE);
                mActivityDescription.setText(description);
            }
        }

        void setActivityColor(int color) {
            int[] activityColorIds = {R.id.activity_color, R.id.activity_color_description, R.id.activity_color_emotion, R.id.description_divider, R.id.emotion_divider};
            for (int id : activityColorIds) {
                mActivityColor = itemView.findViewById(id);
                mActivityColor.setBackgroundColor(color);

            }
        }

        @Override
        public void onClick(View v) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            ActivityModel activity = mActivityList.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.very_sad:
                    activity.setAnswer(1);
                    setEmotionImage(1);
                    mEmotionSelected.onSelect(activity.getId());
                    notifyItemChanged(getAdapterPosition());
                    emotionExpand();
                    break;

                case R.id.sad:
                    activity.setAnswer(2);
                    setEmotionImage(2);
                    mEmotionSelected.onSelect(activity.getId());
                    notifyItemChanged(getAdapterPosition());
                    emotionExpand();
                    break;

                case R.id.normal:
                    activity.setAnswer(3);
                    setEmotionImage(3);
                    mEmotionSelected.onSelect(activity.getId());
                    notifyItemChanged(getAdapterPosition());
                    emotionExpand();
                    break;

                case R.id.happy:
                    activity.setAnswer(4);
                    setEmotionImage(4);
                    mEmotionSelected.onSelect(activity.getId());
                    notifyItemChanged(getAdapterPosition());
                    emotionExpand();
                    break;

                case R.id.very_happy:
                    activity.setAnswer(5);
                    setEmotionImage(5);
                    mEmotionSelected.onSelect(activity.getId());
                    notifyItemChanged(getAdapterPosition());
                    emotionExpand();
                    break;
            }
            realm.copyToRealmOrUpdate(activity);
            realm.commitTransaction();

        }

        void emotionExpand() {
            // Check for an expanded view, collapse if you find one
            if (emotionExpandedPosition >= 0) {
                int prev = emotionExpandedPosition;
                notifyItemChanged(prev);
            }

            if (emotionExpandedPosition == getAdapterPosition()) {
                emotionExpandedPosition = -1;
                notifyItemChanged(getAdapterPosition());
            } else {
                // Set the current position to "expanded"
                emotionExpandedPosition = getAdapterPosition();
                notifyItemChanged(emotionExpandedPosition);
            }
        }

        void descriptionExpand() {
            // Check for an expanded view, collapse if you find one
            if (descriptionExpandedPosition >= 0) {
                int prev = descriptionExpandedPosition;
                notifyItemChanged(prev);
            }

            if (descriptionExpandedPosition == getAdapterPosition()) {
                descriptionExpandedPosition = -1;
                notifyItemChanged(getAdapterPosition());
            } else {
                // Set the current position to "expanded"
                descriptionExpandedPosition = getAdapterPosition();
                notifyItemChanged(descriptionExpandedPosition);
            }
        }
    }
}
