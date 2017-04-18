package com.namdev.sanyukt.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.namdev.sanyukt.R;

/**
 * Created by Harish on 4/11/2017.
 */

public class RelationAdapter extends BaseAdapter {

    private Activity mActivity;
    private String[] data;
    private static LayoutInflater inflater=null;


    public RelationAdapter(Activity m, String[] d) {
        mActivity = m;
        data=d;
        inflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        //Log.i("Chiranjeev", parent.toString());

        if(convertView==null)
            vi = inflater.inflate(R.layout.city_list_item, null);

        TextView text=(TextView)vi.findViewById(R.id.id_city_list_item_txt);
        text.setText(data[position]);
        View divider = (View)vi.findViewById(R.id.id_city_list_item_divider);
        divider.setVisibility(View.VISIBLE);

        if (position == getCount()-1)
            divider.setVisibility(View.GONE);

        return vi;
    }
}
