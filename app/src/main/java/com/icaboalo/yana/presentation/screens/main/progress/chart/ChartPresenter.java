package com.icaboalo.yana.presentation.screens.main.progress.chart;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericDetailPresenter;

import javax.inject.Inject;

/**
 * Created by icaboalo on 01/10/16.
 */

public class ChartPresenter extends GenericDetailPresenter {

    @Inject
    public ChartPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemDetails() {

    }
}
