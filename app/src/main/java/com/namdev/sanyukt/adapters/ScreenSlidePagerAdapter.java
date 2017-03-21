package com.namdev.sanyukt.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.namdev.sanyukt.ScreenSlidePageFragment;

/**
 * Created by Harish on 3/10/2017.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 5;

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        return ScreenSlidePageFragment.create(position);

    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
