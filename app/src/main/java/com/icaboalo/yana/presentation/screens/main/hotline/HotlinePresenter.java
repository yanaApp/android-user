package com.icaboalo.yana.presentation.screens.main.hotline;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;

import javax.inject.Inject;

/**
 * Created by icaboalo on 28/09/16.
 */

public class HotlinePresenter extends GenericDetailPresenter<Object> {

    @Inject
    public HotlinePresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemDetails() {

    }
}
