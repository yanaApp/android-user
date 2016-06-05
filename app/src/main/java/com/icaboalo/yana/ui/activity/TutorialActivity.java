package com.icaboalo.yana.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.icaboalo.yana.PrefConstants;
import com.icaboalo.yana.R;
import com.icaboalo.yana.domain.FragmentPagerModel;
import com.icaboalo.yana.ui.adapter.ViewPagerAdapter;
import com.icaboalo.yana.ui.fragment.ActionPlanFragment;
import com.icaboalo.yana.ui.fragment.TutorialPageFragment;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TutorialActivity extends AppCompatActivity {

    ViewPager mViewPager;
    InkPageIndicator mViewPagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tutorial);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPagerIndicator = (InkPageIndicator) findViewById(R.id.view_pager_indicator);
        setupViewPager();
        final TextView loginButton = (TextView) findViewById(R.id.login_button);


        findViewById(R.id.skip_tutorial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(4);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 4){
                    assert loginButton != null;
                    loginButton.animate().translationY(0).setDuration(1000);
                } else{
                    assert loginButton != null;
                    loginButton.animate().translationY(loginButton.getHeight()).setDuration(200);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void setupViewPager(){
        ArrayList<FragmentPagerModel> pagerList = new ArrayList<>();
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        pagerList.add(new FragmentPagerModel(new TutorialPageFragment().newInstance(pagerList.size())));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), pagerList);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPagerIndicator.setViewPager(mViewPager);
    }
}
