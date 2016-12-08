package com.icaboalo.yana.presentation.screens.main.profile.birth_date;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericPostPresenter;
import com.icaboalo.yana.presentation.screens.view_model.UserViewModel;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by icaboalo on 6/12/16.
 */
public class BirthDatePresenter extends GenericPostPresenter<UserViewModel> {

    @Inject
    public BirthDatePresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void post(HashMap<String, Object> postBundle) {

    }
}
