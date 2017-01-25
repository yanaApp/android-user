package com.icaboalo.yana.presentation.component.adapter;

/**
 * @author icaboalo on 13/08/16.
 */
public interface ItemBase<M> {
    void bindData(M data, int position, boolean isEnabled);
}
