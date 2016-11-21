package com.icaboalo.yana.presentation.screens.main.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.main.activities.ActivitiesRecyclerAdapter.ActivityViewHolder;
import com.icaboalo.yana.presentation.screens.view_model.ActivityViewModel;
import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemInfo;
import com.icaboalo.yana.util.VUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 15/08/16.
 */
public class ActivitiesRecyclerAdapter extends GenericRecyclerViewAdapter<ActivityViewHolder> {

    public interface ActivitiesListener {
        void onExpand(int position, boolean expanded);
        void onSelect(ActivityViewModel activityViewModel, int answer);
    }

    private int emotionExpandedPosition = -1, descriptionExpandedPosition = -1;
    private ActivitiesListener mActivitiesListener;

    public ActivitiesRecyclerAdapter(Context context, List<ItemInfo> dataList) {
        super(context, dataList);
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActivityViewHolder(mLayoutInflater.inflate(R.layout.item_activity_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (position == emotionExpandedPosition) {
            holder.showEmotions(true);
//            holder.showDescription(false);
//            holder.showActivityColorBar(false);
            mActivitiesListener.onExpand(position, true);

        } else if (position == descriptionExpandedPosition) {
//            holder.showDescription(true);
            holder.showEmotions(false);
//            holder.showActivityColorBar(false);
            mActivitiesListener.onExpand(position, true);

        } else {
            holder.showEmotions(false);
//            holder.showDescription(false);
//            holder.showActivityColorBar(true);
            mActivitiesListener.onExpand(position, false);
        }

        holder.btEmotion.setOnClickListener(v -> {
            if (holder.btEmotion.getDrawable() != null) {
                Toast.makeText(mContext, "No se puede cambiar la respuesta despues de elegirla.",
                        Toast.LENGTH_SHORT).show();
            } else {
                descriptionExpandedPosition = -1;
                if (emotionExpandedPosition == position) {
                    emotionExpandedPosition = -1;
                } else {
                    emotionExpandedPosition = position;
                }
                notifyItemRangeChanged(position, getItemCount());
            }
        });

        holder.btDescription.setOnClickListener(v -> {
            emotionExpandedPosition = -1;
            if (descriptionExpandedPosition == position) {
                descriptionExpandedPosition = -1;
            } else {
                descriptionExpandedPosition = position;
            }
            notifyItemRangeChanged(position, getItemCount());
        });

        if (position == 0)
            holder.startTour();

    }

    public void setOnEmotionSelectedListener(ActivitiesListener emotionSelectedListener) {
        this.mActivitiesListener = emotionSelectedListener;
    }

    public class ActivityViewHolder extends GenericRecyclerViewAdapter.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.activity_color)
        View vColor;
        @BindView(R.id.btEmotion)
        ImageView btEmotion;
        @BindView(R.id.btDescription)
        TextView btDescription;
        @BindView(R.id.rlDescription)
        RelativeLayout rlDescription;
        @BindView(R.id.rlEmotion)
        RelativeLayout rlEmotion;
        @BindView(R.id.ivCancel)
        ImageView ivCancel;
        @BindView(R.id.ivVerySad)
        ImageView ivVerySad;
        @BindView(R.id.ivSad)
        ImageView ivSad;
        @BindView(R.id.ivNormal)
        ImageView ivNormal;
        @BindView(R.id.ivHappy)
        ImageView ivHappy;
        @BindView(R.id.ivVeryHappy)
        ImageView ivVeryHappy;

        int mTourCount = 0, answer = 0;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setListeners();
        }

        @Override
        public void bindData(Object data, int position, boolean isEnabled) {
            super.bindData(data, position, isEnabled);
            Context context = MyApplication.getInstance().getApplicationContext();
            if (data instanceof ItemInfo) {
                ItemInfo item = (ItemInfo) data;
                ActivityViewModel activity = (ActivityViewModel) item.getData();
                tvTitle.setText(activity.getTitle());
                tvDescription.setText(activity.getDescription());
                vColor.setBackgroundColor(Color.parseColor(activity.getCategory().getColor()));
                VUtil.setEmotionImage(context, activity.getAnswer(), btEmotion);
                answer = activity.getAnswer();
            }
        }

        public void setListeners() {
            ivCancel.setOnClickListener(this);
            ivVerySad.setOnClickListener(this);
            ivSad.setOnClickListener(this);
            ivNormal.setOnClickListener(this);
            ivHappy.setOnClickListener(this);
            ivVeryHappy.setOnClickListener(this);
        }

        public void showDescription(boolean show) {
            int visibility = show ? View.VISIBLE : View.GONE;
            rlDescription.setVisibility(visibility);
            if (show) {
                btDescription.setVisibility(View.VISIBLE);
                btDescription.setText("Ver menos");
                btDescription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.up_arrow_24dp, 0, 0, 0);
            } else {
                btDescription.setVisibility(View.VISIBLE);
                btDescription.setText("Ver más");
                btDescription.setCompoundDrawablesWithIntrinsicBounds(R.drawable.down_arrow_24dp, 0, 0, 0);
            }
        }

        public void showEmotions(boolean show) {
            int visibility = show ? View.VISIBLE : View.GONE;
            rlEmotion.setVisibility(visibility);
            tvDescription.setVisibility(!show ? View.VISIBLE : View.GONE);
//            btDescription.setVisibility(!show ? View.VISIBLE : View.GONE);
        }

        public void showActivityColorBar(boolean show) {
            int visibility = show ? View.VISIBLE : View.INVISIBLE;
            vColor.setVisibility(visibility);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivCancel:
                    mActivitiesListener.onSelect((ActivityViewModel) getItem(getAdapterPosition()).getData(), 0);
                    VUtil.setEmotionImage(mContext, 0, btEmotion);
                    break;
                case R.id.ivVerySad:
                    mActivitiesListener.onSelect((ActivityViewModel) getItem(getAdapterPosition()).getData(), 1);
                    VUtil.setEmotionImage(mContext, 1, btEmotion);
                    break;
                case R.id.ivSad:
                    mActivitiesListener.onSelect((ActivityViewModel) getItem(getAdapterPosition()).getData(), 2);
                    VUtil.setEmotionImage(mContext, 2, btEmotion);
                    break;
                case R.id.ivNormal:
                    mActivitiesListener.onSelect((ActivityViewModel) getItem(getAdapterPosition()).getData(), 3);
                    VUtil.setEmotionImage(mContext, 3, btEmotion);
                    break;
                case R.id.ivHappy:
                    mActivitiesListener.onSelect((ActivityViewModel) getItem(getAdapterPosition()).getData(), 4);
                    VUtil.setEmotionImage(mContext, 4, btEmotion);
                    break;
                case R.id.ivVeryHappy:
                    mActivitiesListener.onSelect((ActivityViewModel) getItem(getAdapterPosition()).getData(), 5);
                    VUtil.setEmotionImage(mContext, 5, btEmotion);
                    break;
            }
            showEmotions(false);
            emotionExpandedPosition = -1;
//            showActivityColorBar(true);
        }

        void startTour() {
            final ShowcaseView showcaseView = new ShowcaseView.Builder((Activity) mContext)
                    .setContentTitle("Welcome to your Action Plan")
                    .singleShot(99)
                    .withMaterialShowcase()
                    .setTarget(Target.NONE)
                    .setStyle(R.style.CustomShowcase)
                    .setContentText("We'll give you a brief tour around, so you can start by yourself.")
                    .build();
            showcaseView.setButtonText("Next");
            showcaseView.overrideButtonClick(v -> {
                switch (mTourCount) {
                    case 0:
                        showcaseView.setShowcase(new ViewTarget(btEmotion), true);
                        showcaseView.setContentTitle("Press here");
                        showcaseView.setContentText("You'll need to press in here whenever you have finished an activity so you can tell us how you felt.");
                        break;

                    case 1:
                        showEmotions(true);
                        showcaseView.setShowcase(new ViewTarget(rlEmotion), true);
                        showcaseView.setContentTitle("Emotions");
                        showcaseView.setContentText("You'll need to press in here whenever you have finished an activity so you can tell us how you felt.");
                        break;

//                    case 2:
//                        showEmotions(false);
//                        showcaseView.setShowcase(new ViewTarget(btDescription), true);
//                        showcaseView.setContentTitle("Press here");
//                        showcaseView.setContentText("You'll need to press in here whenever you have finished an activity so you can tell us how you felt.");
//                        break;
//
//                    case 3:
//                        showDescription(true);
//                        showcaseView.setShowcase(new ViewTarget(rlDescription), true);
//                        showcaseView.setContentTitle("Description");
//                        showcaseView.setContentText("You'll need to press in here whenever you have finished an activity so you can tell us how you felt.");
//                        break;

                    case 2:
                        showEmotions(false);
//                        showDescription(false);
                        showcaseView.hide();
                        break;
                }
                mTourCount++;
            });
        }
    }
}
