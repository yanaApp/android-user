package com.icaboalo.yana.presentation.screens;

/**
 * @author icaboalo on 31/07/16.
 */
public interface GenericDetailView<M> extends LoadDataView{


    /**
     * Render an item in the UI.
     *
     * @param item the {@link M} that will be shown.
     * */
    void renderItem(M item);
}
