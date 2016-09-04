package com.icaboalo.yana.presentation.screens;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * @author icaboalo on 31/07/16.
 */
public interface GenericListView<M, H extends RecyclerView.ViewHolder> extends LoadDataView {

    /**
     * Render a list in the UI.
     *
     * @param itemList The list of {@link M} that will be shown.
     */
    void renderItemList(List<M> itemList);

    /**
     * View a {@link M} profile/details.
     *
     * @param viewModel The item that will be shown.
     */
    void viewItemDetail(M viewModel, H viewHolder);
}
