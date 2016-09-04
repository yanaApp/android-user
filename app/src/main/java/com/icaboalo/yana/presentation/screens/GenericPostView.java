package com.icaboalo.yana.presentation.screens;

/**
 * @author icaboalo on 31/07/16.
 */
public interface GenericPostView<M> extends LoadDataView {

    void postSuccessful(M item);
}
