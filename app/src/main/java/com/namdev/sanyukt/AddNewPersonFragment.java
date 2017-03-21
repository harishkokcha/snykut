package com.namdev.sanyukt;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.namdev.sanyukt.beans.Member;

import java.util.Calendar;

/**
 * Created by Harish on 3/17/2017.
 */
public class AddNewPersonFragment extends Fragment {
    Context mContext;
    Member member;
    private Activity mActivity;
    private Calendar presentCalender;
    private Calendar selectedCalendar;
    private DatePickerDialog datePickerDialog;
    private EditText dob;

    public static AddNewPersonFragment newInstance() {
        return new AddNewPersonFragment();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_person_new, container, false);

        return rootView;
    }
}
