package com.icaboalo.yana.old.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.icaboalo.yana.domain.FragmentPagerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<FragmentPagerModel> mPagerList = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm, List<FragmentPagerModel> pagerList) {
        super(fm);
        this.mPagerList = pagerList;
    }

    @Override
    public Fragment getItem(int position) {
        return mPagerList.get(position).getPager();
    }

    @Override
    public int getCount() {
        return mPagerList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPagerList.get(position).getTitle();
    }
}
