package com.namdev.sanyukt.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.namdev.sanyukt.R;
import com.namdev.sanyukt.beans.NameModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Harish on 4/14/2017.
 */
public class CustomAdapter extends ArrayAdapter<NameModel> {
    Context context;
    int textViewResourceId;
    List<NameModel> items, tempItems, suggestions;


    public CustomAdapter(Activity mActivity, List<NameModel> items) {
        super(mActivity, R.layout.auto_complete_item, R.id.id_auto_complete_txtvw, items);
        this.context = mActivity;
        this.items = items;
        tempItems = new ArrayList<>(items); // this makes the difference.
        suggestions = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.auto_complete_item, parent, false);
        }
        NameModel nameModel = items.get(position);
        if (nameModel != null) {
            TextView lblName = (TextView) view.findViewById(R.id.id_auto_complete_txtvw);
            if (lblName != null)
                lblName.setText(nameModel.getName());
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((NameModel) resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                if (suggestions != null) suggestions.clear();
                for (NameModel nameModel : tempItems) {
                    if (nameModel.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(nameModel);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results != null && results.count > 0) {
                List<NameModel> filterList = (List<NameModel>) results.values;
                clear();
                for (NameModel userMembers : filterList) {
                    add(userMembers);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
