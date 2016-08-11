package com.icaboalo.yana.presentation.screens.tour;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.old.domain.FragmentPagerModel;
import com.icaboalo.yana.old.ui.adapter.ViewPagerAdapter;
import com.icaboalo.yana.presentation.screens.BaseActivity;
import com.icaboalo.yana.presentation.screens.GenericListView;
import com.icaboalo.yana.presentation.screens.login.LoginActivity;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.btLogin)
    TextView btLogin;

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

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 4){
                    btLogin.setVisibility(View.VISIBLE);
                    btLogin.animate().translationY(0).setDuration(800);
                    tvSkipTutorial.setVisibility(View.GONE);
                } else{
                    tvSkipTutorial.setVisibility(View.VISIBLE);
                    btLogin.animate().translationY(btLogin.getHeight()).setDuration(100);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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

    @OnClick(R.id.btLogin)
    void goToLogin(){
        navigator.navigateTo(getApplicationContext(), LoginActivity.getCallingIntent(getApplicationContext()));
    }

    public static Intent getCallingContext(Context context){
        return new Intent(context, TourActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
