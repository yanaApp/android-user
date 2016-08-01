package com.icaboalo.yana.presentation.screens;

import android.content.Context;

/**
 * @author icaboalo on 31/07/16.
 */
public interface LoadDataView {

    /**
     * Show a view with a progress bar, representing loading.
     * */
    void showLoading();

    /**
     *  Hide a view with a progress bar, representing finish.
     * */
    void hideLoading();

    /**
     *  Show a retry view in case of an error when retrieving data.
     * */
    void showRetry();

    /**
     * Hide a retry view.
     * */
    void hideRetry();

    /**
     *  Show an error
     *
     *  @param message representing an error
     * */
    void showError(String message);

    /**
     *  Get a {@link Context}
     * */
    Context getApplicationContext();
}
