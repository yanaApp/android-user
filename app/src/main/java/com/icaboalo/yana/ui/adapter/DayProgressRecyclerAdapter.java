package com.icaboalo.yana.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.realm.DayModel;

import java.util.ArrayList;

/**
 * Created by icaboalo on 09/06/16.
 */
public class DayProgressRecyclerAdapter extends RecyclerView.Adapter<DayProgressRecyclerAdapter.DayProgressViewHolder> {

    Context mContext;
    ArrayList<DayModel> mDayList;
    LayoutInflater mInflater;

    public DayProgressRecyclerAdapter(Context context, ArrayList<DayModel> dayList) {
        this.mContext = context;
        this.mDayList = dayList;
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
        holder.setDayText(day.getNumber(), day.getDate());
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
            mDayText = (TextView) itemView.findViewById(R.id.day_text);
            mCompletedCount = (TextView) itemView.findViewById(R.id.completed_count);
            mIncompleteCount = (TextView) itemView.findViewById(R.id.incomplete_count);
            mEmotionAverage = (ImageView) itemView.findViewById(R.id.emotion_average_image);
        }



        void setDayText(int dayNumber, String date){
            mDayText.setText(Html.fromHtml("<b>Día " + dayNumber + "</b>  |  " + date));
        }
    }
}
