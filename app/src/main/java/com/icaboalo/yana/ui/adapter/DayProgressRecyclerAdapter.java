package com.icaboalo.yana.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by icaboalo on 09/06/16.
 */
public class DayProgressRecyclerAdapter extends RecyclerView.Adapter<DayProgressRecyclerAdapter.DayProgressViewHolder> {

    @Override
    public DayProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(DayProgressViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class DayProgressViewHolder extends RecyclerView.ViewHolder{

        public DayProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
}
