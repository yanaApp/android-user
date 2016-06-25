package com.icaboalo.yana.util;

import android.content.Context;
import android.content.SharedPreferences;
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
}

