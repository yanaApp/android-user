package com.icaboalo.yana.old.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author icaboalo on 05/08/16.
 */
public class CustomFontEditText extends EditText {

    final static String DOSIS_FONT = "fonts/Dosis-Regular.ttf";
    final static String AVENIR_FONT = "fonts/AvenirLTStd-Book.otf";
    final static String AVENIR_LIGHT_FONT = "fonts/AvenirLTStd-Light.otf";

    public CustomFontEditText(Context context) {
        super(context);
    }

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(context);
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            init(context);
    }


    private void init(Context context) {
        this.setTypeface(getBlackTypeFace(context));
    }

    private Typeface getBlackTypeFace(Context context) {
        return Typeface.createFromAsset(context.getAssets(), AVENIR_LIGHT_FONT);
    }
}
