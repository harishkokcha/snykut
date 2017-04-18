package com.namdev.sanyukt;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.namdev.sanyukt.beans.AppConstants;

/**
 * Created by Harish on 11/16/2016.
 */
public class SanyuktListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        FrameLayout frame = (FrameLayout) findViewById(R.id.id_content_main_fragment);
        Log.d("Harish", "SanyuktListActivity");
        Intent intent = getIntent();
        ActionBar actionBar=getSupportActionBar();
        int i = intent.getIntExtra(AppConstants.GENDER, 0);
        actionBar.setTitle("Sanyukt List");

        if (savedInstanceState == null) {
            SanyuktListFragment mFragment = SanyuktListFragment.newInstance(i);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(frame.getId(), mFragment).commit();
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
