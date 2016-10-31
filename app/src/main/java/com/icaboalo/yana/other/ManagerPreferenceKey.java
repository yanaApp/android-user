package com.icaboalo.yana.other;

import java.util.ArrayList;

/**
 * Created by icaboalo on 28/10/16.
 */

public class ManagerPreferenceKey {

    protected static ArrayList<PreferenceKey> values;

    protected static void addPreferenceKey(PreferenceKey key) {
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(key);
    }
}
