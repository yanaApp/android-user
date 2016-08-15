package com.icaboalo.yana.presentation.screens.component.adapter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author icaboalo on 13/08/16.
 */
public class ItemInfo<M> implements Serializable {

    public static final int HEADER = 1, FOOTER = 2, LOADING = 3, SECTION_HEADER = 4, SECTION_ITEM = 5,
            CARD_SECTION_HEADER = 6;

    private M data;
    private int layoutId;
    private long id;
    private boolean isEnabled = true;

    public ItemInfo(M data, int layoutId) {
        this.data = data;
        this.layoutId = layoutId;
    }

    public long getId() {
        return id;
    }

    public ItemInfo<M> setId(long id){
        this.id = id;
        return this;
    }

    public M getData() {
        return data;
    }

    public ItemInfo<M> setData(M data){
        this.data = data;
        return this;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public ItemInfo<M> setEnabled(boolean enabled) {
        isEnabled = enabled;
        return this;
    }
}
