package com.icaboalo.yana.presentation.screens.tour;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.old.domain.FragmentPagerModel;
import com.icaboalo.yana.old.ui.adapter.ViewPagerAdapter;
import com.icaboalo.yana.old.ui.fragment.TutorialPageFragment;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericListView;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author icaboalo on 11/08/16.
 */
public class TourActivity extends BaseActivity implements GenericListView<FragmentPagerModel, RecyclerView.ViewHolder> {

    @Inject
    TourPresenter mTourPresenter;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.view_pager_indicator)
    InkPageIndicator mViewPagerIndicator;
    @Bind(R.id.tvSkipTutorial)
    TextView tvSkipTutorial;

    @Override
    public void initialize() {
        getComponent().inject(this);
        mTourPresenter.setView(this);
        mTourPresenter.initialize();
    }

    @Override
    public void setupUI() {
        setContentView(R.layout.activity_tour);
        ButterKnife.bind(this);
    }

    @Override
    public void renderItemList(List<FragmentPagerModel> itemList) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), itemList);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPagerIndicator.setViewPager(mViewPager);
    }

    @Override
    public void viewItemDetail(FragmentPagerModel viewModel, RecyclerView.ViewHolder viewHolder) {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showRetry() {
    }

    @Override
    public void hideRetry() {
    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    public static Intent getCallingContext(Context context){
        return new Intent(context, TourActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
