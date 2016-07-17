package com.icaboalo.yana.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by icaboalo on 16/07/16.
 */
public class CustomFontTextView extends TextView {

    final static String DOSIS_FONT = "fonts/Dosis-Regular.ttf";

    public CustomFontTextView(Context context) {
        super(context);
        if (! isInEditMode())
            init(context);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (! isInEditMode())
            init(context);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) init(context);
    }

    private void init(Context context) {
        this.setTypeface(getDosisBlackTypeFace(context));
    }

    private Typeface getDosisBlackTypeFace(Context context) {
        return Typeface.createFromAsset(context.getAssets(), DOSIS_FONT);
    }
}
