package com.icaboalo.yana.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.util.OnEmotionSelected;
import com.icaboalo.yana.util.VUtil;
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
    public void onBindViewHolder(final ActivityViewHolder holder, final int position) {
        ActivityModel activity = mActivityList.get(position);

        if (position == emotionExpandedPosition) {
            holder.showEmotions(true);
            holder.showDescription(false);
            holder.mDescriptionExpand.setVisibility(View.GONE);
        } else if (position == descriptionExpandedPosition) {
            holder.showDescription(true);
            holder.showEmotions(false);
            holder.mEmotionImage.setVisibility(View.GONE);
            holder.mDescriptionExpand.setText("Ver menos");
            holder.mDescriptionExpand.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_keyboard_arrow_up_black_24dp, 0, 0, 0);
        } else {
            holder.showEmotions(false);
            holder.showDescription(false);
            holder.mDescriptionExpand.setVisibility(View.VISIBLE);
            holder.mEmotionImage.setVisibility(View.VISIBLE);
            holder.mDescriptionExpand.setText("Ver más");
            holder.mDescriptionExpand.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_keyboard_arrow_down_black_24dp, 0, 0, 0);
        }

        holder.mEmotionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionExpandedPosition = -1;
                if (emotionExpandedPosition == position) {
                    emotionExpandedPosition = -1;
                } else {
                    emotionExpandedPosition = position;
                }
                notifyDataSetChanged();
            }
        });

        holder.mDescriptionExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionExpandedPosition = -1;
                if (descriptionExpandedPosition == position) {
                    descriptionExpandedPosition = -1;
                } else {
                    descriptionExpandedPosition = position;
                }
                notifyDataSetChanged();
            }
        });


        holder.setTitle(activity.getTitle());
        holder.setDescription(activity.getDescription());
        VUtil.setEmotionImage(mContext, activity.getAnswer(), holder.mEmotionImage);

        holder.setActivityColor(activity.getCategory().getColor());

    }

    @Override
    public int getItemCount() {
        return mActivityList.size();
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mActivityTitle, mActivityDescription, mDescriptionExpand;
        View mActivityColor;
        RelativeLayout mDescriptionLayout, mEmotionLayout;
        ImageView mEmotionImage, mVerySadImage, mSadImage, mNormalImage, mHappyImage, mVeryHappyImage, mCancelImage;
        OnEmotionSelected mEmotionSelected;

        public ActivityViewHolder(View itemView, OnEmotionSelected onEmotionSelected) {
            super(itemView);
            this.mDescriptionLayout = (RelativeLayout) itemView.findViewById(R.id.description_layout);
            this.mEmotionLayout = (RelativeLayout) itemView.findViewById(R.id.emotion_layout);
            this.mVerySadImage = (ImageView) itemView.findViewById(R.id.very_sad);
            this.mSadImage = (ImageView) itemView.findViewById(R.id.sad);
            this.mNormalImage = (ImageView) itemView.findViewById(R.id.normal);
            this.mHappyImage = (ImageView) itemView.findViewById(R.id.happy);
            this.mVeryHappyImage = (ImageView) itemView.findViewById(R.id.very_happy);
            this.mEmotionImage = (ImageView) itemView.findViewById(R.id.image_emotion);
            this.mCancelImage = (ImageView) itemView.findViewById(R.id.cancel);
            this.mDescriptionExpand = (TextView) itemView.findViewById(R.id.image_description);
            mVerySadImage.setOnClickListener(this);
            mSadImage.setOnClickListener(this);
            mNormalImage.setOnClickListener(this);
            mHappyImage.setOnClickListener(this);
            mVeryHappyImage.setOnClickListener(this);
            mCancelImage.setOnClickListener(this);
            this.mEmotionSelected = onEmotionSelected;
        }

        void setEmotionImage(int answer) {
            if (answer >= 0) {
                switch (answer) {
                    case 0:
                        Picasso.with(mContext).load(R.drawable.cancel_outlined_circular_32).into(mEmotionImage);
                        break;

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

        void setActivityColor(String color) {
            int[] activityColorIds = {R.id.activity_color, R.id.activity_color_description, R.id.activity_color_emotion, R.id.emotion_divider};
            for (int id : activityColorIds) {
                mActivityColor = itemView.findViewById(id);
                mActivityColor.setBackgroundColor(Color.parseColor(color));

            }
        }

        @Override
        public void onClick(View v) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            ActivityModel activity = mActivityList.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.cancel:
                    mEmotionSelected.onSelect(activity, activity.getAnswer(), 0);
                    setEmotionImage(0);
                    activity.setAnswer(0);
                    notifyItemChanged(getAdapterPosition());
                    break;

                case R.id.very_sad:
                    mEmotionSelected.onSelect(activity, activity.getAnswer(), 1);
                    setEmotionImage(1);
                    activity.setAnswer(1);
                    notifyItemChanged(getAdapterPosition());
                    break;

                case R.id.sad:
                    mEmotionSelected.onSelect(activity, activity.getAnswer(), 2);
                    activity.setAnswer(2);
                    setEmotionImage(2);
                    notifyItemChanged(getAdapterPosition());
                    break;

                case R.id.normal:
                    mEmotionSelected.onSelect(activity, activity.getAnswer(), 3);
                    activity.setAnswer(3);
                    setEmotionImage(3);
                    notifyItemChanged(getAdapterPosition());
                    break;

                case R.id.happy:
                    mEmotionSelected.onSelect(activity, activity.getAnswer(), 4);
                    activity.setAnswer(4);
                    setEmotionImage(4);
                    notifyItemChanged(getAdapterPosition());
                    break;

                case R.id.very_happy:
                    mEmotionSelected.onSelect(activity, activity.getAnswer(), 5);
                    activity.setAnswer(5);
                    setEmotionImage(5);
                    notifyItemChanged(getAdapterPosition());
                    break;
            }
            realm.copyToRealmOrUpdate(activity);
            realm.commitTransaction();
            showEmotions(false);
            emotionExpandedPosition = -1;

        }

        public void showDescription(boolean show) {
            int visibility = show ? View.VISIBLE : View.GONE;
            mDescriptionLayout.setVisibility(visibility);
        }

        public void showEmotions(boolean show){
            int visibility = show ? View.VISIBLE : View.GONE;
            mEmotionLayout.setVisibility(visibility);
        }
    }
}
