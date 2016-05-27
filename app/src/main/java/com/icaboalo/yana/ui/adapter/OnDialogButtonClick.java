package com.icaboalo.yana.ui.adapter;

import android.content.DialogInterface;
import android.support.annotation.Nullable;

/**
 * Created by icaboalo on 27/05/16.
 */
public interface OnDialogButtonClick {
    void onPositiveClick(DialogInterface dialog, @Nullable Object object, int labelResource);
    void onNeutralClick(DialogInterface dialog, @Nullable Object object, int labelResource);
    void onNegativeClick(DialogInterface dialog, @Nullable Object object, int labelResource);
}
