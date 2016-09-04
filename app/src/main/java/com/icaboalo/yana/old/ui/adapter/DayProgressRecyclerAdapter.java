package com.icaboalo.yana.old.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.old.realm.DayModel;
import com.icaboalo.yana.util.VUtil;

import java.util.ArrayList;

/**
 * Created by icaboalo on 09/06/16.
 */
public class DayProgressRecyclerAdapter extends RecyclerView.Adapter<DayProgressRecyclerAdapter.DayProgressViewHolder> {

    Context mContext;
    ArrayList<DayModel> mDayList;
    ArrayList<Integer> mCompletedActivities, mIncompleteActivities, mEmotionAverage;
    LayoutInflater mInflater;

    public DayProgressRecyclerAdapter(Context context, ArrayList<DayModel> dayList, ArrayList<Integer> completedActivities, ArrayList<Integer> incompleteActivities, ArrayList<Integer> emotionAverage) {
        this.mContext = context;
        this.mDayList = dayList;
        this.mCompletedActivities = completedActivities;
        this.mIncompleteActivities = incompleteActivities;
        this.mEmotionAverage = emotionAverage;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public DayProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_day_progress_adapter, parent, false);
        return new DayProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayProgressViewHolder holder, int position) {
        DayModel day = mDayList.get(position);
        int completed = mCompletedActivities.get(position);
        int incomplete = mIncompleteActivities.get(position);
        int emotion = mEmotionAverage.get(position);
        holder.setDayText(day.getNumber(), day.getDate());
        holder.mCompletedCount.setText("" + completed);
        holder.mIncompleteCount.setText("" + incomplete);
        VUtil.setEmotionImage(mContext, emotion, holder.mEmotionAverage);
    }

    @Override
    public int getItemCount() {
        return mDayList.size();
    }

    class DayProgressViewHolder extends RecyclerView.ViewHolder{

        TextView mDayText, mCompletedCount, mIncompleteCount;
        ImageView mEmotionAverage;

        public DayProgressViewHolder(View itemView) {
            super(itemView);
            mDayText = (TextView) itemView.findViewById(R.id.tvDate);
            mCompletedCount = (TextView) itemView.findViewById(R.id.tvCompletedCount);
            mIncompleteCount = (TextView) itemView.findViewById(R.id.tvIncompleteCount);
            mEmotionAverage = (ImageView) itemView.findViewById(R.id.ivEmotionAverage);
        }



        void setDayText(int dayNumber, String date){
            mDayText.setText(Html.fromHtml("<b>Día " + dayNumber + "</b>  |  " + date));
        }
    }
}
