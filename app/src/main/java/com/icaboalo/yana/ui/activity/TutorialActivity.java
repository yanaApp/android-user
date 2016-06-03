package com.icaboalo.yana.ui.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.icaboalo.yana.R;
import com.icaboalo.yana.domain.FragmentPagerModel;
import com.icaboalo.yana.ui.adapter.ViewPagerAdapter;
import com.icaboalo.yana.ui.fragment.ActionPlanFragment;
import com.icaboalo.yana.ui.fragment.TutorialPageFragment;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;

public class TutorialActivity extends AppCompatActivity {

    ViewPager mViewPager;
    InkPageIndicator mViewPagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPagerIndicator = (InkPageIndicator) findViewById(R.id.view_pager_indicator);
        setupViewPager();
    }

    void setupViewPager(){
        ArrayList<FragmentPagerModel> pagerList = new ArrayList<>();
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        pagerList.add(new FragmentPagerModel(new ActionPlanFragment()));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), pagerList);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPagerIndicator.setViewPager(mViewPager);
    }
}
