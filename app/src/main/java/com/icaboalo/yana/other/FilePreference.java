package com.icaboalo.yana.other;

import android.support.annotation.StringDef;

import com.icaboalo.yana.util.Constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by icaboalo on 28/10/16.
 */

public class FilePreference {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            DEFAULT_PREFERENCE,
            APP_INFORMATION,
            TEST,
    })
    @interface File {

    }

    public static final String DEFAULT_PREFERENCE = Constants.SETTINGS_FILE_NAME;
    public static final String APP_INFORMATION = "";
    public static final String TEST = "TEST_INFORMATION";
}
