package com.icaboalo.yana.presentation.screens.main.progress;

import android.support.v7.widget.RecyclerView;

import com.icaboalo.yana.presentation.screens.GenericListView;
import com.icaboalo.yana.presentation.screens.component.adapter.ItemInfo;
import com.icaboalo.yana.presentation.screens.main.view_model.ActionPlanViewModel;

import java.util.List;

/**
 * Created by icaboalo on 03/10/16.
 */

public interface ProgressView extends GenericListView<ActionPlanViewModel, RecyclerView.ViewHolder> {

    void setDayInfoList(List<ItemInfo> dayItemInfoList);
}
