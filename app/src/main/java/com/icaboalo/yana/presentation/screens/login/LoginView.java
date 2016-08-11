package com.icaboalo.yana.presentation.screens.login;

import com.icaboalo.yana.presentation.screens.GenericDetailView;
import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.login.view_model.RecoverPasswordViewModel;

/**
 * @author icaboalo on 10/08/16.
 */
public interface LoginView<M> extends GenericPostView<M> {
    void recoverPasswordSuccess(boolean success);
}
