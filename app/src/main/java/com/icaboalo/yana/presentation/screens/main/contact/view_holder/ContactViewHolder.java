package com.icaboalo.yana.presentation.screens.main.contact.view_holder;

import android.view.View;

import com.icaboalo.yana.presentation.screens.component.adapter.GenericRecyclerViewAdapter;

import butterknife.ButterKnife;

/**
 * @author icaboalo on 31/08/16.
 */
public class ContactViewHolder extends GenericRecyclerViewAdapter.ViewHolder {



    public ContactViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Object data, int position, boolean isEnabled) {
        super.bindData(data, position, isEnabled);
    }


}
