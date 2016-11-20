package com.icaboalo.yana.other;

import android.content.Context;
import android.content.SharedPreferences;

import com.icaboalo.yana.util.Constants;

/**
 * Created by icaboalo on 28/10/16.
 */

public class ManagerPreference {

    public static final String SETTINGS = Constants.SETTINGS_FILE_NAME;

    private static ManagerPreference sInstance;

    private Context context;

    private static SharedPreferences sharedPreferences;

    public static ManagerPreference getInstance() {
        if (sInstance == null)
            throw new UnsupportedOperationException("Before you call this method first you need to instance it");
        return sInstance;
    }

    public static void init(Context context) {
        if (sInstance == null)
            sInstance = new ManagerPreference(context);
    }

    public ManagerPreference(Context context) {
        this.context = context;
    }

    private SharedPreferences getPreferenceFile(String filePreferenceName) {
        return context.getSharedPreferences(filePreferenceName, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor(String filePreferenceName) {
        SharedPreferences customPref = getPreferenceFile(filePreferenceName);
        return customPref.edit();
    }

    public String getString(PreferenceKey preferenceKey) {
        SharedPreferences sharedPref = getPreferenceFile(preferenceKey.getFileName());
        return sharedPref.getString(preferenceKey.getKeyValue(), (String) preferenceKey.getDefaultValue());
    }

    public String getSettings(PreferenceKey preferenceKey, String defValue) {
        SharedPreferences sharedPref = getPreferenceFile(preferenceKey.getFileName());
        return sharedPref.getString(preferenceKey.getKeyValue(), defValue);
    }

    public int getInt(PreferenceKey preferenceKey) {
        SharedPreferences sharedPref = getPreferenceFile(preferenceKey.getFileName());
        return sharedPref.getInt(preferenceKey.getKeyValue(), (int) preferenceKey.getDefaultValue());
    }

    public int getInt(PreferenceKey preferenceKey, int defaultValue) {
        SharedPreferences sharedPref = getPreferenceFile(preferenceKey.getFileName());
        return sharedPref.getInt(preferenceKey.getKeyValue(), defaultValue);
    }

    public boolean getBoolean(PreferenceKey preferenceKey) {
        SharedPreferences sharedPref = getPreferenceFile(preferenceKey.getFileName());
        return sharedPref.getBoolean(preferenceKey.getKeyValue(), (boolean) preferenceKey.getDefaultValue());
    }

    public boolean getBoolean(PreferenceKey preferenceKey, boolean defaultValue) {
        SharedPreferences sharedPref = getPreferenceFile(preferenceKey.getFileName());
        return sharedPref.getBoolean(preferenceKey.getKeyValue(), defaultValue);
    }

    public void set(PreferenceKey preferenceKey, String value) {
        getEditor(preferenceKey.getFileName())
                .putString(preferenceKey.getKeyValue(), value)
                .commit();
    }

    public void set(PreferenceKey preferenceKey, int value) {
        getEditor(preferenceKey.getFileName())
                .putInt(preferenceKey.getKeyValue(), value)
                .commit();
    }

    public void set(PreferenceKey preferenceKey, boolean value) {
        getEditor(preferenceKey.getFileName())
                .putBoolean(preferenceKey.getKeyValue(), value)
                .commit();
    }

    public void set(PreferenceKey preferenceKey, float value) {
        getEditor(preferenceKey.getFileName())
                .putFloat(preferenceKey.getKeyValue(), value)
                .commit();
    }

    public void set(PreferenceKey preferenceKey, long value) {
        getEditor(preferenceKey.getFileName())
                .putLong(preferenceKey.getKeyValue(), value)
                .commit();
    }

    public void remove(PreferenceKey preferenceKey) {
        getEditor(preferenceKey.getFileName())
                .remove(preferenceKey.getKeyValue())
                .commit();
    }

    public boolean contains(PreferenceKey preferenceKey) {
        return getPreferenceFile(preferenceKey.getFileName())
                .contains(preferenceKey.getKeyValue());
    }

    private void resetUserPreference(PreferenceKey preferenceKey) {
        Object defaultValue = preferenceKey.getDefaultValue();
        String className = defaultValue.getClass().getName();

        if (className.equals(String.class.getName())) {
            String value = (String) defaultValue;
            set(preferenceKey, value);
        }
        else if (className.equals(int.class.getName())) {
            int value = (int) defaultValue;
            set(preferenceKey, value);
        }
        else if (className.equals(boolean.class.getName())) {
            boolean value = (boolean) defaultValue;
            set(preferenceKey, value);
        }
        else if (className.equals(float.class.getName())) {
            float value = (float) defaultValue;
            set(preferenceKey, value);
        }
        else if (className.equals(long.class.getName())) {
            long value = (long) defaultValue;
            set(preferenceKey, value);
        }
    }

    public void resetPreferences() {
        for (PreferenceKey preferenceKey : ManagerPreferenceKey.values) {
            resetUserPreference(preferenceKey);
        }
    }

    public void resetGroupPreference(@FilePreference.File String fileNamePreference) {
        getEditor(fileNamePreference).clear().commit();
    }
}
