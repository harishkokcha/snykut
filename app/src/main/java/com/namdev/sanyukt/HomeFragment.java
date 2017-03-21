package com.namdev.sanyukt;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.namdev.sanyukt.adapters.HomeFragmentGridAdapter;
import com.namdev.sanyukt.adapters.ScreenSlidePagerAdapter;

/**
 * Created by Harish on 11/16/2016.
 */

public class HomeFragment extends Fragment {
    Context mContext;

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

        return rootView;

    }
}
