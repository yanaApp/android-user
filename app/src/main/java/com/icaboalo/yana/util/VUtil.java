package com.icaboalo.yana.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.squareup.picasso.Picasso;

/**
 * Created by icaboalo on 26/05/16.
 */
public class VUtil {

    public static void setEmotionImage(Context context, int answer, ImageView image) {
        if (answer > 0) {
            switch (answer) {
                case 1:
                    Picasso.with(context).load(R.drawable.very_sad_32).into(image);
                    break;

                case 2:
                    Picasso.with(context).load(R.drawable.sad_32).into(image);
                    break;

                case 3:
                    Picasso.with(context).load(R.drawable.normal_32).into(image);
                    break;

                case 4:
                    Picasso.with(context).load(R.drawable.happy_32).into(image);
                    break;

                case 5:
                    Picasso.with(context).load(R.drawable.very_happy_32).into(image);
                    break;

            }
        } else {
            image.setImageDrawable(null);
        }

    }

    public static String answerToText(int answer){
        switch (answer){
            case 0:
                return "null";

            case 1:
                return "very sad";

            case 2:
                return "sad";

            case 3:
                return "normal";

            case 4:
                return "happy";

            case 5:
                return "very happy";
        }
        return "";
    }

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
//                if(interpolatedTime == 1){
//                    v.setVisibility(View.GONE);
//                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
//                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}

