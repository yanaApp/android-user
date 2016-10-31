package com.icaboalo.yana.other;

import java.security.InvalidParameterException;

/**
 * Created by icaboalo on 28/10/16.
 */

public class PreferenceKey {

    private String fileName, keyValue;
    private Object defaultValue;

    public PreferenceKey(String key, Object value) {
        this(FilePreference.DEFAULT_PREFERENCE, key, value);
    }

    public PreferenceKey(@FilePreference.File String file, String key, Object value) {
        if (key == null)
            throw new InvalidParameterException("key cannot be null");

        keyValue = key;
        defaultValue = value;
        fileName = file;

        ManagerPreferenceKey.addPreferenceKey(this);
    }

    protected String getFileName() {
        return fileName;
    }

    protected String getKeyValue() {
        return keyValue;
    }

    protected Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        return keyValue;
    }
}
