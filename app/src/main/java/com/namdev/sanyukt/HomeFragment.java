package com.namdev.sanyukt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.namdev.sanyukt.adapters.HomeFragmentGridAdapter;
import com.namdev.sanyukt.adapters.ScreenSlidePagerAdapter;
import com.namdev.sanyukt.views.BubbleViewPagerIndicator;

/**
 * Created by Harish on 11/16/2016.
 */

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {
    Context mContext;

    private static int[] sliderImagesId = new int[]{
            R.drawable.homepage_banner, R.drawable.background
    };
    private ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    private ViewPager viewPager;
    private BubbleViewPagerIndicator pageIndicator;

    private static int PAGER_DELAY = 2500;
    private Runnable viewPagerVisibleScroll;
    private Handler mHandler;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        String[] prgmNameList = {"Boys", "Girls"};
        int[] prgmImages = {R.mipmap.ic_boys,
                R.mipmap.ic_girl};

        GridView gridview = (GridView) rootView.findViewById(R.id.id_fragment_home_grid_view);
        gridview.setAdapter(new HomeFragmentGridAdapter(this, prgmNameList, prgmImages));

        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getChildFragmentManager());
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(imageFragmentPagerAdapter);
        viewPager.setVisibility(View.GONE);
        pageIndicator = (BubbleViewPagerIndicator) rootView.findViewById(R.id.page_indicator);
        pageIndicator.makeBubbles(R.drawable.bg_bubble_dark_indicator, sliderImagesId.length);
        pageIndicator.setBubbleActive(0);

        viewPager.addOnPageChangeListener(this);
        scrollViewPager();

        return rootView;

    }


    private void scrollViewPager() {

        viewPagerVisibleScroll = new Runnable() {
            @Override
            public void run() {
                try {
                    if (viewPager.getCurrentItem() == sliderImagesId.length - 1) {
                        viewPager.setCurrentItem(0, true);
                    } else {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                    }
                    PAGER_DELAY = 3500;
                } finally {
                    // 100% guarantee that this always happens, even if
                    // your update method throws an exception
                    mHandler.postDelayed(viewPagerVisibleScroll, PAGER_DELAY);
                }
            }
        };
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageIndicator.setBubbleActive(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class ImageFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public ImageFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return sliderImagesId.length;
        }

        @Override
        public Fragment getItem(int position) {
            return SwipeFragment.newInstance(position);
        }

    }

    public static class SwipeFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View swipeView = inflater.inflate(R.layout.swipe_fragment, container, false);
            ImageView imageView = (ImageView) swipeView.findViewById(R.id.imageView);
            Bundle bundle = getArguments();
            int position = bundle.getInt("position");
            int imgResId = sliderImagesId[position];
            imageView.setImageResource(imgResId);
            imageView.setTag(imgResId);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return swipeView;
        }

        static SwipeFragment newInstance(int position) {
            SwipeFragment swipeFragment = new SwipeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            swipeFragment.setArguments(bundle);
            return swipeFragment;
        }
    }


}
