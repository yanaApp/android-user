package com.icaboalo.yana.presentation.screens.action_plan.progress.view_holder;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.ActivityViewModel;
import com.icaboalo.yana.presentation.screens.action_plan.view_model.DayViewModel;
import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemInfo;
import com.icaboalo.yana.util.VUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 23/08/16.
 */
public class DayInfoViewHolder extends GenericRecyclerViewAdapter.ViewHolder {

    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.tvCompletedCount)
    TextView tvCompletedCount;
    @Bind(R.id.tvIncompleteCount)
    TextView tvIncompleteCount;
    @Bind(R.id.ivEmotionAverage)
    ImageView ivEmotionAverage;

    int completedCount, incompleteCount, answerTotal;

    public DayInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Object data, int position, boolean isEnabled) {
        Context context = MyApplication.getInstance().getApplicationContext();
        if (data instanceof ItemInfo){
            ItemInfo item = (ItemInfo) data;
            DayViewModel day = (DayViewModel) item.getData();
            tvDate.setText(Html.fromHtml("<b>Día " + day.getDayNumber() + "</b>  |  " + day.getDate()));
            for (ActivityViewModel activity : day.getActivityList()){
                if (activity.getAnswer() > 0){
                    answerTotal += activity.getAnswer();
                    completedCount ++;
                }
                else
                    incompleteCount ++;
            }
            tvCompletedCount.setText(String.valueOf(completedCount));
            tvIncompleteCount.setText(String.valueOf(incompleteCount));
            if (completedCount > 0)
                VUtil.setEmotionImage(context, answerTotal / completedCount, ivEmotionAverage);
        }
    }
}
