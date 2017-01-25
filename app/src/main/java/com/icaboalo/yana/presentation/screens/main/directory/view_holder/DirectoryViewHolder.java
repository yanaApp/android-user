package com.icaboalo.yana.presentation.screens.main.directory.view_holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.component.adapter.GenericSwipeRecyclerAdapter;
import com.icaboalo.yana.presentation.screens.view_model.PsychologistViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by icaboalo on 23/01/17.
 */

public class DirectoryViewHolder extends GenericSwipeRecyclerAdapter.SwipeableViewHolder {

    @BindView(R.id.swipeLayout)
    public SwipeLayout swipeLayout;
    @BindView(R.id.tv_psychologist_name)
    TextView tvPsychologistName;
    @BindView(R.id.bt_call)
    public ImageView btCall;
    @BindView(R.id.bt_location)
    public ImageView btLocation;

    public DirectoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindData(Object data, int position, boolean isEnabled) {
        if (data instanceof PsychologistViewModel) {
            PsychologistViewModel psychologist = (PsychologistViewModel) data;
            tvPsychologistName.setText(psychologist.getName());
        }
    }
}
