package com.icaboalo.yana.presentation.screens.register;

import com.icaboalo.yana.presentation.screens.GenericPostView;
import com.icaboalo.yana.presentation.screens.register.view_model.UserViewModel;

/**
 * @author icaboalo on 12/08/16.
 */
public interface RegisterView<M> extends GenericPostView<M> {

    void saveSuccess(UserViewModel model);
}
