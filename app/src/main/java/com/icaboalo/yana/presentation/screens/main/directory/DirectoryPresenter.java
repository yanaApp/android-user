package com.icaboalo.yana.presentation.screens.main.directory;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.presentation.screens.GenericListPresenter;
import com.icaboalo.yana.presentation.screens.main.directory.view_holder.DirectoryViewHolder;
import com.icaboalo.yana.presentation.screens.view_model.PsychologistViewModel;

import javax.inject.Inject;

/**
 * Created by icaboalo on 24/01/17.
 */

public class DirectoryPresenter extends GenericListPresenter<PsychologistViewModel, DirectoryViewHolder> {

    @Inject
    public DirectoryPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemList() {

    }
}
