package com.icaboalo.yana.presentation.factories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.icaboalo.yana.R;
import com.icaboalo.yana.presentation.component.widget.ColoredSnackbar;

/**
 * Created by icaboalo on 15/01/17.
 */

public class SnackbarFactory {

    @StringDef({TYPE_ERROR})
    public @interface SnackbarType {
    }

    public static final String TYPE_ERROR = "snackbar_error";
    public static final String TYPE_INFO = "snackbar_info";

    public static Snackbar getSnackbar(@SnackbarType String type, @NonNull View view,
                                       @StringRes int stringId, int duration) {
        return createSnackbar(type, Snackbar.make(view, stringId, duration), view.getContext());
    }

    public static Snackbar getSnackbar(@SnackbarType String type, @NonNull View view,
                                       @NonNull CharSequence message, int duration) {
        return createSnackbar(type, Snackbar.make(view, message, duration), view.getContext());
    }

    private static Snackbar createSnackbar(@SnackbarType String type, @NonNull Snackbar snackbar,
                                           @NonNull Context context) {
        switch (type) {
            case TYPE_ERROR:
                return ColoredSnackbar.color(snackbar, ContextCompat.getColor(context, R.color.error_red));

            case TYPE_INFO:
                return ColoredSnackbar.color(snackbar, ContextCompat.getColor(context, R.color.yana_green));

            default:
                return snackbar;
        }
    }
}
