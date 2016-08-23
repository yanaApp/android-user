package com.icaboalo.yana.presentation.screens.action_plan.progress.view_holder;

import android.view.View;

import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;

import butterknife.ButterKnife;

/**
 * @author icaboalo on 23/08/16.
 */
public class DayInfoViewHolder extends GenericRecyclerViewAdapter.ViewHolder {

    public DayInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Object data, int position, boolean isEnabled) {
        super.bindData(data, position, isEnabled);
    }
}
