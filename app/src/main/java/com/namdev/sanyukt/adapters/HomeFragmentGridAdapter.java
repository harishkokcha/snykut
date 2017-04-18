package com.namdev.sanyukt.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.namdev.sanyukt.HomeFragment;
import com.namdev.sanyukt.R;
import com.namdev.sanyukt.SanyuktListActivity;
import com.namdev.sanyukt.beans.AppConstants;

/**
 * Created by Harish on 11/16/2016.
 */
public class HomeFragmentGridAdapter extends BaseAdapter {

    private int[] mImage;
    private String[] mText;
    private Activity mActivity;
    private static LayoutInflater inflater = null;

    public HomeFragmentGridAdapter(HomeFragment homeFragment, String[] prgmNameList, int[] myImageList) {
        mActivity = homeFragment.getActivity();
        mImage = myImageList;
        mText = prgmNameList;
        inflater = (LayoutInflater) mActivity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mImage.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.home_fragment_grid_item, null);
        holder.tv = (TextView) rowView.findViewById(R.id.id_grid_item_text);
        holder.img = (ImageView) rowView.findViewById(R.id.id_grid_item_image);

        holder.tv.setText(mText[position]);
        holder.img.setImageResource(mImage[position]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, SanyuktListActivity.class);
                intent.putExtra(AppConstants.GENDER, position);
                mActivity.startActivity(intent);

            }
        });

        return rowView;
    }

    private class Holder {
        TextView tv;
        ImageView img;
    }

}
