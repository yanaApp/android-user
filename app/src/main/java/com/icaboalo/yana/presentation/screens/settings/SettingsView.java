package com.icaboalo.yana.presentation.screens.settings;

import android.os.Bundle;

import com.icaboalo.yana.presentation.screens.GenericDetailView;

/**
 * Created by icaboalo on 12/10/16.
 */

public interface SettingsView extends GenericDetailView<Bundle> {

    void notificationUpdated(String type);
}
