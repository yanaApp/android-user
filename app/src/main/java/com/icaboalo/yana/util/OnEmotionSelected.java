package com.icaboalo.yana.util;

import com.icaboalo.yana.realm.ActivityModel;

/**
 * Created by icaboalo on 03/06/16.
 */
public interface OnEmotionSelected {
    void onSelect(ActivityModel activity, int previousAnswer, int newAnswer);
}
