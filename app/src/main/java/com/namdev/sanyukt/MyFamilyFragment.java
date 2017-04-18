package com.namdev.sanyukt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.namdev.sanyukt.adapters.OnLoadMoreListener;
import com.namdev.sanyukt.adapters.SanyuktListAdapter;
import com.namdev.sanyukt.beans.AppConstants;
import com.namdev.sanyukt.beans.Member;
import com.namdev.sanyukt.beans.MemberListResponse;
import com.namdev.sanyukt.beans.Users;
import com.namdev.sanyukt.utils.AppController;
import com.namdev.sanyukt.utils.AppPreferences;
import com.namdev.sanyukt.utils.GenericRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harish on 2/24/2017.
 */
public class MyFamilyFragment extends Fragment {

    Activity mActivity;
    private SanyuktListAdapter mAdapter;
    int PAGE = 0;
    private List<Member> memberList;
    static int Gender = 0;
    private ProgressBar finalProgressBar;
    private boolean isFirstCall = true;
    private TextView addFamilyMember;

    public static MyFamilyFragment newInstance() {
        MyFamilyFragment fragment = new MyFamilyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.id_fragment_recycle_list_view);
        mActivity = getActivity();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        memberList = new ArrayList<>();
        mAdapter = new SanyuktListAdapter(mActivity, mRecyclerView, memberList, AppConstants.MY_FAMILY_FRAGMENT);

        addFamilyMember = (TextView) rootView.findViewById(R.id.txt_add_family);
        addFamilyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AddEditPersonDetails.class);
                startActivity(intent);
            }
        });
        addFamilyMember.setVisibility(View.VISIBLE);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        finalProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_example);
        finalProgressBar.setVisibility(View.VISIBLE);

        if (isFirstCall) {
            fetchList();
            isFirstCall = false;
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PAGE = 0;
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isFirstCall) {
                    Log.d("Harish", "mAdapter.setOnLoadMoreListener");
                    memberList.add(null);
                    Handler handler = new Handler();

                    final Runnable r = new Runnable() {
                        public void run() {
                            mAdapter.notifyItemInserted(memberList.size() - 1);
                            fetchList();
                        }
                    };

                    handler.post(r);

                }
            }
        });
    }

    private void fetchList() {
        Log.d("Harish", "fetchList ");
        Member member = new Member();
        final Users users = AppPreferences.getInstance().getUser(mActivity);
        member.setMemberUserId(users.getUserid());
        member.setPageNumber(PAGE);
        member.setPerPageData(5);
        member.setAction(AppConstants.GET_USER_MEMBER_LIST);

        GenericRequest<MemberListResponse> genericRequest = new GenericRequest<MemberListResponse>(Request.Method.POST, AppConstants.BASE_URL,
                MemberListResponse.class, member, new Response.Listener<MemberListResponse>() {
            @Override
            public void onResponse(MemberListResponse response) {
                finalProgressBar.setVisibility(View.GONE);
                if (response.getResponsecode().equals(AppConstants.SUCCESS)) {

                    if (PAGE != 0) {
                        memberList.remove(memberList.size() - 1);
                        mAdapter.notifyItemRemoved(memberList.size());
                    }
                    PAGE++;
                    List<Member> memberListNew = response.getMemberList();
                    mAdapter.addAll(memberListNew);
                    mAdapter.notifyItemInserted(memberList.size());
                    mAdapter.setLoaded();

                } else {
                    if (PAGE != 0) {
                        memberList.remove(memberList.size() - 1);
                        mAdapter.notifyItemRemoved(memberList.size());
                    }
                    Toast.makeText(mActivity, "No More Profile", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                finalProgressBar.setVisibility(View.GONE);
                Log.d("Harish", "response " + new Gson().toJson(error));
            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(genericRequest);

    }
}
