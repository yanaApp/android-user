package com.icaboalo.yana.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.util.OnEmotionSelected;
import com.icaboalo.yana.util.OnViewHolderClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActivityRecyclerAdapter extends RecyclerView.Adapter<ActivityRecyclerAdapter.ActivityViewHolder> {

    Context mContext;
    ArrayList<ActivityModel> mActivityList;
    LayoutInflater mInflater;
    OnViewHolderClick viewHolderClick;
    OnEmotionSelected mOnEmotionSelected;
    private int nExpandedPosition = -1;


    public ActivityRecyclerAdapter(Context context, ArrayList<ActivityModel> activityList, OnViewHolderClick onViewHolderClick, OnEmotionSelected onEmotionSelected) {
        this.mContext = context;
        this.mActivityList = activityList;
        this.viewHolderClick = onViewHolderClick;
        this.mOnEmotionSelected = onEmotionSelected;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_action_plan_adapter, parent, false);
        return new ActivityViewHolder(view, viewHolderClick, mOnEmotionSelected);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        ActivityModel activity = mActivityList.get(position);

        if (position == nExpandedPosition) {
            holder.mExpandedLayout.setVisibility(View.VISIBLE);
        } else {
            holder.mExpandedLayout.setVisibility(View.GONE);
        }

        if (position == mActivityList.size() -1){
            holder.mItemDivider.setVisibility(View.GONE);
        }

        holder.setTitle(activity.getTitle());
        holder.setDescription(activity.getDescription());
        holder.setEmotionImage(activity.getAnswer());

        if (activity.getDescription().isEmpty() || activity.getDescription() == null){
            holder.mActivityColor.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return mActivityList.size();
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mActivityTitle, mActivityDescription;
        View mActivityColor, mItemDivider;
        LinearLayout mExpandedLayout;
        RelativeLayout mNormalLayout;
        ImageView mEmotionImage, mVerySadImage, mSadImage, mNormalImage, mHappyImage, mVeryHappyImage;
        OnViewHolderClick viewHolderClick;
        OnEmotionSelected mEmotionSelected;

        public ActivityViewHolder(View itemView, OnViewHolderClick onViewHolderClick, OnEmotionSelected onEmotionSelected) {
            super(itemView);
            this.mNormalLayout = (RelativeLayout) itemView.findViewById(R.id.normal_layout);
            this.mExpandedLayout = (LinearLayout) itemView.findViewById(R.id.expanded_layout);
            this.mVerySadImage = (ImageView) itemView.findViewById(R.id.very_sad);
            this.mSadImage = (ImageView) itemView.findViewById(R.id.sad);
            this.mNormalImage = (ImageView) itemView.findViewById(R.id.normal);
            this.mHappyImage = (ImageView) itemView.findViewById(R.id.happy);
            this.mVeryHappyImage = (ImageView) itemView.findViewById(R.id.very_happy);
            this.mActivityColor = itemView.findViewById(R.id.activity_color);
            this.mItemDivider = itemView.findViewById(R.id.item_divider);
            mVerySadImage.setOnClickListener(this);
            mSadImage.setOnClickListener(this);
            mNormalImage.setOnClickListener(this);
            mHappyImage.setOnClickListener(this);
            mVeryHappyImage.setOnClickListener(this);
            this.viewHolderClick = onViewHolderClick;
            this.mEmotionSelected = onEmotionSelected;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolderClick.onClick(v, getAdapterPosition());

                    expand();
                }
            });
        }

        void setEmotionImage(int answer){
            mEmotionImage = (ImageView) itemView.findViewById(R.id.image_emotion);
            if (answer > 0){
                switch (answer){
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
            }
            else{
                mEmotionImage.setImageDrawable(null);
            }

        }

        void setTitle(String text){
                mActivityTitle = (TextView) itemView.findViewById(R.id.activity_title);
                mActivityTitle.setText(text);
        }

        void setDescription(String description){
            mActivityDescription = (TextView) itemView.findViewById(R.id.activity_description);
            if (description.isEmpty() || description == null){
                mActivityDescription.setVisibility(View.GONE);
            } else {
                mActivityDescription.setVisibility(View.VISIBLE);
                mActivityDescription.setText(description);
            }
        }

        @Override
        public void onClick(View v) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            ActivityModel activity = mActivityList.get(getAdapterPosition());
            switch (v.getId()){
                case R.id.very_sad:
                    activity.setAnswer(1);
                    setEmotionImage(1);
                    mEmotionSelected.onSelect(activity.getId());
                    notifyItemChanged(getAdapterPosition());
                    expand();
                    break;

                case R.id.sad:
                    activity.setAnswer(2);
                    setEmotionImage(2);
                    mEmotionSelected.onSelect(activity.getId());
                    notifyItemChanged(getAdapterPosition());
                    expand();
                    break;

                case R.id.normal:
                    activity.setAnswer(3);
                    setEmotionImage(3);
                    mEmotionSelected.onSelect(activity.getId());
                    notifyItemChanged(getAdapterPosition());
                    expand();
                    break;

                case R.id.happy:
                    activity.setAnswer(4);
                    setEmotionImage(4);
                    mEmotionSelected.onSelect(activity.getId());
                    notifyItemChanged(getAdapterPosition());
                    expand();
                    break;

                case R.id.very_happy:
                    activity.setAnswer(5);
                    setEmotionImage(5);
                    mEmotionSelected.onSelect(activity.getId());
                    notifyItemChanged(getAdapterPosition());
                    expand();
                    break;
            }
            realm.copyToRealmOrUpdate(activity);
            realm.commitTransaction();

        }

        void expand(){
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
