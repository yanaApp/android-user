package com.icaboalo.yana.presentation.screens.component.adapter;

/**
 * @author icaboalo on 13/08/16.
 */
public interface ItemBase<M> {
    void bindData(M data, int position, boolean isEnabled);
}
