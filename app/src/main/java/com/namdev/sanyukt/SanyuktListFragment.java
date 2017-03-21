package com.namdev.sanyukt;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.namdev.sanyukt.adapters.OnLoadMoreListener;
import com.namdev.sanyukt.adapters.SanyuktListAdapter;
import com.namdev.sanyukt.beans.ApiResponse;
import com.namdev.sanyukt.beans.AppConstants;
import com.namdev.sanyukt.beans.Member;
import com.namdev.sanyukt.beans.Users;
import com.namdev.sanyukt.utils.AppController;
import com.namdev.sanyukt.utils.AppPreferences;
import com.namdev.sanyukt.utils.GenericRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Harish on 11/16/2016.
 */

public class SanyuktListFragment extends Fragment {
    Activity mActivity;
    private RecyclerView mRecyclerView;
    private SanyuktListAdapter mAdapter;
    int PAGE = 0;
    private List<Member> memberList;
    private Member member;
    static int Gender = 0;

    public static SanyuktListFragment newInstance(int i) {
        Gender = i;
        return new SanyuktListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.id_fragment_recycle_list_view);
        mActivity = getActivity();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PAGE = 0;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        final Users users = AppPreferences.getInstance().getUser(mActivity);
        memberList = new ArrayList<>();
        mAdapter = new SanyuktListAdapter(mActivity, mRecyclerView, memberList);
        member = new Member();
        member.setMemberUserId(users.getUserid());
        member.setPageNumber(PAGE);
        member.setPerPageData(5);
        if (Gender != 0)
            member.setMamberGender("F");
        else
            member.setMamberGender("M");

        GenericRequest genericRequest = new GenericRequest<ApiResponse>(Request.Method.POST, AppConstants.USER_LOGIN,
                ApiResponse.class, member, new Response.Listener<ApiResponse>() {
            @Override
            public void onResponse(ApiResponse response) {
                if (response.getData().equals(AppConstants.SUCCESS)) {
                    Type listType = new TypeToken<List<Member>>() {
                    }.getType();
                    memberList = new Gson().fromJson(response.getData(), listType);
                    mAdapter = new SanyuktListAdapter(mActivity, mRecyclerView, memberList);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(genericRequest);


        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("hint", "Load More");
                memberList.add(null);
                mAdapter.notifyItemInserted(memberList.size() - 1);

                member.setMemberUserId(users.getUserid());
                member.setPageNumber(PAGE + 1);
                member.setPerPageData(5);
                GenericRequest genericRequest = new GenericRequest<ApiResponse>(Request.Method.POST, AppConstants.USER_LOGIN,
                        ApiResponse.class, member, new Response.Listener<ApiResponse>() {
                    @Override
                    public void onResponse(ApiResponse response) {
                        if (response.getData().equals(AppConstants.SUCCESS)) {
                            Type listType = new TypeToken<List<Member>>() {
                            }.getType();
                            List<Member> memberList = new Gson().fromJson(response.getData(), listType);
                            memberList.remove(memberList.size() - 1);
                            mAdapter.notifyItemRemoved(memberList.size());
                            mAdapter.notifyDataSetChanged();
                            mAdapter.setLoaded();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                };
                AppController.getInstance().addToRequestQueue(genericRequest);

                /*//Load more data for reyclerview
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
                }, 5000);*/
            }
        });

    }
}
