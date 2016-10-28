package com.icaboalo.yana.old.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by icaboalo on 16/07/16.
 */
public class CustomFontTextView extends TextView {

    final static String DOSIS_FONT = "fonts/Dosis-Regular.ttf";
    final static String AVENIR_FONT = "fonts/AvenirLTStd-Book.otf";
    final static String AVENIR_LIGHT_FONT = "fonts/AvenirLTStd-Light.otf";
    final static String MAXWELL_LIGHT_FONT = "fonts/MAXWELL_LIGHT.ttf";

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
        if (!isInEditMode())
            init(context);
    }

    private void init(Context context) {
        this.setTypeface(getBlackTypeFace(context));
    }

    private Typeface getBlackTypeFace(Context context) {
        return Typeface.createFromAsset(context.getAssets(), MAXWELL_LIGHT_FONT);
    }

//    @Override
//    public void setText(CharSequence text, BufferType type) {
//        super.setText(text, type);
//        this.setText(text);
//    }
}
