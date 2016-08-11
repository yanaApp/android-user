package com.icaboalo.yana.presentation.screens.tour;

import android.support.v7.widget.RecyclerView;

import com.icaboalo.yana.domain.interactors.GenericUseCase;
import com.icaboalo.yana.old.domain.FragmentPagerModel;
import com.icaboalo.yana.old.ui.fragment.TutorialPageFragment;
import com.icaboalo.yana.presentation.screens.GenericListPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * @author icaboalo on 11/08/16.
 */
public class TourPresenter extends GenericListPresenter<FragmentPagerModel, RecyclerView.ViewHolder>{

    @Inject
    public TourPresenter(GenericUseCase genericUseCase) {
        super(genericUseCase);
    }

    @Override
    public void getItemList() {
        ArrayList<FragmentPagerModel> pagerList = new ArrayList<>();
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        getGenericListView().renderItemList(pagerList);
    }
}
