package com.icaboalo.yana.presentation.screens.main.progress.view_holder;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.icaboalo.yana.MyApplication;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.view_model.ActivityViewModel;
import com.icaboalo.yana.presentation.view_model.DayViewModel;
import com.icaboalo.yana.presentation.component.adapter.GenericRecyclerViewAdapter;
import com.icaboalo.yana.util.Utils;
import com.icaboalo.yana.util.VUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 23/08/16.
 */
public class DayInfoViewHolder extends GenericRecyclerViewAdapter.ViewHolder {

    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvCompletedCount)
    TextView tvCompletedCount;
    @BindView(R.id.tvIncompleteCount)
    TextView tvIncompleteCount;
    @BindView(R.id.tvNotDoneCount)
    TextView tvNotDoneCount;
    @BindView(R.id.ivEmotionAverage)
    ImageView ivEmotionAverage;

    int completedCount, incompleteCount, answerTotal, notDoneCount;

    public DayInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Object data, int position, boolean isEnabled) {
        Context context = MyApplication.getInstance().getApplicationContext();
        completedCount = 0;
        incompleteCount = 0;
        answerTotal = 0;
        if (data instanceof DayViewModel) {
            DayViewModel day = (DayViewModel) data;
            tvDate.setText(Html.fromHtml("<b>Día " + day.getDayNumber() + "</b>  |  " +
                    Utils.transformDateToText(day.getDate(), "dd-MM-yyyy", "MMMM dd, yyyy")));

            for (ActivityViewModel activity : day.getActivityList()) {
                if (activity.getAnswer() > 0) {
                    completedCount++;
                    answerTotal += activity.getAnswer();
                } else if (activity.getAnswer() == 0)
                    incompleteCount++;
                else
                    notDoneCount++;
            }

            tvCompletedCount.setText(String.valueOf(completedCount));
            tvIncompleteCount.setText(String.valueOf(incompleteCount));
            tvNotDoneCount.setText(String.valueOf(notDoneCount));

            if (completedCount > 0)
                VUtil.setEmotionImage(context, answerTotal / completedCount, ivEmotionAverage);
        }
    }
}
