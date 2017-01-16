package com.icaboalo.yana.presentation.component.widget;

import android.support.annotation.ColorInt;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by icaboalo on 15/01/17.
 */

public class ColoredSnackbar {

    private static View getSnackbarLayout(Snackbar snackbar) {
        if (snackbar != null)
            return snackbar.getView();
        else
            return null;
    }

    public static Snackbar color(Snackbar snackbar, @ColorInt int colorRes) {
        View view = getSnackbarLayout(snackbar);

        if (view != null) {
            view.setBackgroundColor(colorRes);
        }

        return snackbar;
    }
}
