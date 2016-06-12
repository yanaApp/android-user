package com.icaboalo.yana.domain;

import android.support.v4.app.Fragment;

/**
 * Created by icaboalo on 26/05/16.
 */
public class FragmentPagerModel {
    private Fragment pager;
    private String title;

    public FragmentPagerModel(Fragment pager, String title) {
        this.pager = pager;
        this.title = title;
    }

    public FragmentPagerModel(Fragment pager) {
        this.pager = pager;
    }

    public Fragment getPager() {
        return pager;
    }

    public String getTitle() {
        return title;
    }
}