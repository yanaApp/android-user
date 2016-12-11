package com.icaboalo.yana.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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
        if (answer >= 0) {
            switch (answer) {
                case 0:
                    Picasso.with(context).load(R.drawable.cancel_32dp).into(image);
                    break;
                case 1:
                    Picasso.with(context).load(R.drawable.very_sad_32dp).into(image);
                    break;

                case 2:
                    Picasso.with(context).load(R.drawable.sad_32dp).into(image);
                    break;

                case 3:
                    Picasso.with(context).load(R.drawable.normal_32dp).into(image);
                    break;

                case 4:
                    Picasso.with(context).load(R.drawable.happy_32dp).into(image);
                    break;

                case 5:
                    Picasso.with(context).load(R.drawable.very_happy_32dp).into(image);
                    break;

            }
        } else {
            image.setImageDrawable(null);
        }

    }

    public static String answerToText(@NonNull Integer answer){
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
}

