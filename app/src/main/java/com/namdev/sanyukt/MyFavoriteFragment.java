package com.namdev.sanyukt;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namdev.sanyukt.adapters.OnLoadMoreListener;
import com.namdev.sanyukt.adapters.SanyuktListAdapter;
import com.namdev.sanyukt.beans.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harish on 2/24/2017.
 */
public class MyFavoriteFragment extends Fragment {

    Context mContext;
    private RecyclerView mRecyclerView;
    private Activity mActivity;

    public static MyFamilyFragment newInstance(){
        MyFamilyFragment fragment = new MyFamilyFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        mContext=context;
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.id_fragment_recycle_list_view);
        mActivity=getActivity();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        final List<Member> memberList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Member user = new Member();
            memberList.add(user);
        }
        final SanyuktListAdapter mAdapter = new SanyuktListAdapter(mActivity,mRecyclerView,memberList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("haint", "Load More");
                memberList.add(null);
                mAdapter.notifyItemInserted(memberList.size() - 1);
                //Load more data for reyclerview
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");
                        //Remove loading item
                        memberList.remove(memberList.size() - 1);
                        mAdapter.notifyItemRemoved(memberList.size());
                        //Load data
                        int index = memberList.size();
                        int end = index + 3;
                        for (int i = index; i < end; i++) {
                            Member user = new Member();
                            user.setMemberName("Harish : "+i);
                            memberList.add(user);
                        }
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setLoaded();
                    }
                }, 5000);
            }
        });

    }
}

