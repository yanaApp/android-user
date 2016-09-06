package com.icaboalo.yana.old.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.icaboalo.yana.R;
import com.icaboalo.yana.old.realm.ActivityModel;
import com.icaboalo.yana.presentation.screens.main.MainActivity;
import com.icaboalo.yana.util.PrefUtils;
import com.icaboalo.yana.util.VUtil;

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
    OnExpandListener mOnExpandListener;
    private int emotionExpandedPosition = -1, descriptionExpandedPosition = -1;

    public interface OnEmotionSelected {
        void onSelect(ActivityModel activity, int previousAnswer, int newAnswer);
    }

    public interface OnExpandListener {
        void onExpand(int position, boolean expanded);
    }

    public ActivityRecyclerAdapter(Context context, ArrayList<ActivityModel> activityList) {
        this.mContext = context;
        this.mActivityList = activityList;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setEmotionSelectedListener(OnEmotionSelected emotionSelectedListener){
        this.mOnEmotionSelected = emotionSelectedListener;
    }

    public void setOnExpandListener(OnExpandListener expandListener){
        this.mOnExpandListener = expandListener;
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_activity_adapter, parent, false);
        return new ActivityViewHolder(view, mOnEmotionSelected);
    }

    @Override
    public void onBindViewHolder(final ActivityViewHolder holder, final int position) {
        ActivityModel activity = mActivityList.get(position);

        if (position == emotionExpandedPosition) {
            holder.showEmotions(true);
            holder.showDescription(false);
            holder.showActivityColorBar(false);
            holder.mDescriptionExpand.setVisibility(View.GONE);
            mOnExpandListener.onExpand(position, true);

        } else if (position == descriptionExpandedPosition) {
            holder.showDescription(true);
            holder.showEmotions(false);
            holder.showActivityColorBar(false);
            mOnExpandListener.onExpand(position, true);

        } else {
            holder.showEmotions(false);
            holder.showDescription(false);
            holder.showActivityColorBar(true);
            mOnExpandListener.onExpand(position, false);
        }

        holder.mEmotionImage.setOnClickListener(v -> {
            descriptionExpandedPosition = -1;
            if (emotionExpandedPosition == position) {
                emotionExpandedPosition = -1;
            } else {
                emotionExpandedPosition = position;
            }
            notifyItemRangeChanged(position, getItemCount());
        });

        holder.mDescriptionExpand.setOnClickListener(v -> {
            emotionExpandedPosition = -1;
            if (descriptionExpandedPosition == position) {
                descriptionExpandedPosition = -1;
            } else {
                descriptionExpandedPosition = position;
            }
            notifyItemRangeChanged(position, getItemCount());
        });


        if (!PrefUtils.isActionPlanTourComplete(mContext) && position == mActivityList.size() -1){
            holder.startTour();
        }
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

        int mTourCount = 0;

        public ActivityViewHolder(View itemView, OnEmotionSelected onEmotionSelected) {
            super(itemView);
            this.mDescriptionLayout = (RelativeLayout) itemView.findViewById(R.id.rlDescription);
            this.mEmotionLayout = (RelativeLayout) itemView.findViewById(R.id.rlEmotion);
            this.mVerySadImage = (ImageView) itemView.findViewById(R.id.ivVerySad);
            this.mSadImage = (ImageView) itemView.findViewById(R.id.ivSad);
            this.mNormalImage = (ImageView) itemView.findViewById(R.id.ivNormal);
            this.mHappyImage = (ImageView) itemView.findViewById(R.id.ivHappy);
            this.mVeryHappyImage = (ImageView) itemView.findViewById(R.id.ivVeryHappy);
            this.mEmotionImage = (ImageView) itemView.findViewById(R.id.btEmotion);
            this.mCancelImage = (ImageView) itemView.findViewById(R.id.ivCancel);
            this.mDescriptionExpand = (TextView) itemView.findViewById(R.id.btDescription);
            mActivityColor = itemView.findViewById(R.id.activity_color);
            mVerySadImage.setOnClickListener(this);
            mSadImage.setOnClickListener(this);
            mNormalImage.setOnClickListener(this);
            mHappyImage.setOnClickListener(this);
            mVeryHappyImage.setOnClickListener(this);
            mCancelImage.setOnClickListener(this);
            this.mEmotionSelected = onEmotionSelected;
        }

        void setTitle(String text) {
            mActivityTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mActivityTitle.setText(text);
        }

        void setDescription(String description) {
            mActivityDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            if (description.isEmpty() || description == null) {
                mActivityDescription.setVisibility(View.GONE);
            } else {
                mActivityDescription.setVisibility(View.VISIBLE);
                mActivityDescription.setText(description);
            }
        }

        void setActivityColor(String color) {
            mActivityColor.setBackgroundColor(Color.parseColor(color));
        }

        @Override
        public void onClick(View v) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            ActivityModel activity = mActivityList.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.ivCancel:
                    mEmotionSelected.onSelect(activity, activity.getAnswer(), 0);
                    activity.setAnswer(0);
                    VUtil.setEmotionImage(mContext, 0, mEmotionImage);
                    notifyItemChanged(getAdapterPosition());
                    break;

                case R.id.ivVerySad:
                    mEmotionSelected.onSelect(activity, activity.getAnswer(), 1);
                    activity.setAnswer(1);
                    VUtil.setEmotionImage(mContext, 1, mEmotionImage);
                    notifyItemChanged(getAdapterPosition());
                    break;

                case R.id.ivSad:
                    mEmotionSelected.onSelect(activity, activity.getAnswer(), 2);
                    activity.setAnswer(2);
                    VUtil.setEmotionImage(mContext, 2, mEmotionImage);
                    notifyItemChanged(getAdapterPosition());
                    break;

                case R.id.ivNormal:
                    mEmotionSelected.onSelect(activity, activity.getAnswer(), 3);
                    activity.setAnswer(3);
                    VUtil.setEmotionImage(mContext, 3, mEmotionImage);
                    notifyItemChanged(getAdapterPosition());
                    break;

                case R.id.ivHappy:
                    mEmotionSelected.onSelect(activity, activity.getAnswer(), 4);
                    activity.setAnswer(4);
                    VUtil.setEmotionImage(mContext, 4, mEmotionImage);
                    notifyItemChanged(getAdapterPosition());
                    break;

                case R.id.ivVeryHappy:
                    mEmotionSelected.onSelect(activity, activity.getAnswer(), 5);
                    activity.setAnswer(5);
                    VUtil.setEmotionImage(mContext, 5, mEmotionImage);
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
            if (show){
                mDescriptionExpand.setVisibility(View.VISIBLE);
                mDescriptionExpand.setText("Ver menos");
                mDescriptionExpand.setCompoundDrawablesWithIntrinsicBounds(R.drawable.up_arrow_24dp, 0, 0, 0);
            } else {
                mDescriptionExpand.setVisibility(View.VISIBLE);
                mDescriptionExpand.setText("Ver más");
                mDescriptionExpand.setCompoundDrawablesWithIntrinsicBounds(R.drawable.down_arrow_24dp, 0, 0, 0);
            }
        }

        public void showEmotions(boolean show){
            int visibility = show ? View.VISIBLE : View.GONE;
            mEmotionLayout.setVisibility(visibility);
        }

        public void showActivityColorBar(boolean show){
            int visibility = show ? View.VISIBLE : View.INVISIBLE;
            mActivityColor.setVisibility(visibility);
        }

        void startTour(){
            Activity activity = (MainActivity) mContext;
            final ShowcaseView showcaseView = new ShowcaseView.Builder(activity)
                    .setContentTitle("Welcome to your Action Plan")
                    .singleShot(99)
                    .withMaterialShowcase()
                    .setTarget(Target.NONE)
                    .setStyle(R.style.CustomShowcase)
                    .setContentText("We'll give you a brief tour around, so you can start by yourself.")
                    .build();
            showcaseView.setButtonText("Next");
            showcaseView.overrideButtonClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "1", Toast.LENGTH_SHORT).show();
                    switch (mTourCount){
                        case 0:
                            showcaseView.setShowcase(new ViewTarget(mEmotionImage), true);
                            showcaseView.setContentTitle("Press here");
                            showcaseView.setContentText("You'll need to press in here whenever you have finished an activity so you can tell us how you felt.");
                            break;

                        case 1:
                            showEmotions(true);
                            showcaseView.setShowcase(new ViewTarget(mEmotionLayout), true);
                            showcaseView.setContentTitle("Emotions");
                            showcaseView.setContentText("You'll need to press in here whenever you have finished an activity so you can tell us how you felt.");
                            break;

                        case 2:
                            showEmotions(false);
                            showcaseView.setShowcase(new ViewTarget(mDescriptionExpand), true);
                            showcaseView.setContentTitle("Press here");
                            showcaseView.setContentText("You'll need to press in here whenever you have finished an activity so you can tell us how you felt.");
                            break;

                        case 3:
                            showDescription(true);
                            showcaseView.setShowcase(new ViewTarget(mActivityDescription), true);
                            showcaseView.setContentTitle("Description");
                            showcaseView.setContentText("You'll need to press in here whenever you have finished an activity so you can tell us how you felt.");
                            break;

                        case 4:
                            showDescription(false);
                            showcaseView.hide();
                            break;
                    }
                    mTourCount ++;
                }
            });
        }

    }
}
