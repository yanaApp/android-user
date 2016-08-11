package com.icaboalo.yana.old.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.icaboalo.yana.R;
import com.icaboalo.yana.old.domain.FragmentPagerModel;
import com.icaboalo.yana.old.ui.adapter.ViewPagerAdapter;
import com.icaboalo.yana.old.ui.fragment.TutorialPageFragment;
import com.icaboalo.yana.presentation.screens.login.LoginActivity;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TutorialActivity extends AppCompatActivity {

    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.view_pager_indicator)
    InkPageIndicator mViewPagerIndicator;
    @Bind(R.id.tvSkipTutorial)
    TextView tvSkipTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tour);
        ButterKnife.bind(this);

        setupViewPager();
        final TextView loginButton = (TextView) findViewById(R.id.login_button);


        tvSkipTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(4);
            }
        });

        assert loginButton != null;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("INTENT", "login");
                Intent goToLogin = new Intent(TutorialActivity.this, com.icaboalo.yana.presentation.screens.login.LoginActivity.class);
                startActivity(LoginActivity.getCallingIntent(TutorialActivity.this));
                finish();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 4){
                    loginButton.setVisibility(View.VISIBLE);
                    loginButton.animate().translationY(0).setDuration(800);
                    tvSkipTutorial.setVisibility(View.GONE);
                } else{
                    tvSkipTutorial.setVisibility(View.VISIBLE);
                    loginButton.animate().translationY(loginButton.getHeight()).setDuration(100);
                }
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
