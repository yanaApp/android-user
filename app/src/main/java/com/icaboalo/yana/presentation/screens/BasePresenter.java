package com.icaboalo.yana.presentation.screens;

import com.icaboalo.yana.domain.interactors.GenericUseCase;

/**
 * @author icaboalo on 31/07/16.
 */
public abstract class BasePresenter {

    private final GenericUseCase mGenericUseCase;

    public BasePresenter(GenericUseCase genericUseCase) {
        mGenericUseCase = genericUseCase;
    }

    public abstract void resume();
    public abstract void pause();

    public void destroy(){
        mGenericUseCase.unsubscribe();
    }

    public GenericUseCase getGenericUseCase() {
        return mGenericUseCase;
    }
}
