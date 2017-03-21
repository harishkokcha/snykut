package com.namdev.sanyukt.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.namdev.sanyukt.R;
import com.namdev.sanyukt.SanyuktMemberDetails;
import com.namdev.sanyukt.beans.AppConstants;
import com.namdev.sanyukt.beans.Member;

import java.util.List;

/**
 * s
 * Created by Harish on 11/16/2016.
 */
public class SanyuktListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity mActivity;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    List<Member> mMemberList;

    public SanyuktListAdapter(Activity context, RecyclerView mRecyclerView, List<Member> memberList) {
        this.mActivity = context;
        Log.d("Harish", "Item SanyuktListAdapter mActivity" + mActivity);
        mMemberList = memberList;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("harish", "mMemberList.get(position) == null :" + (mMemberList.get(position) == null));
        return mMemberList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.boys_girls_list_item, parent, false);
            return new MemberViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MemberViewHolder) {
            MemberViewHolder userViewHolder = (MemberViewHolder) holder;
            userViewHolder.textView.setText(mMemberList.get(position).getMemberName());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mMemberList == null ? 0 : mMemberList.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    private class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        MemberViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.id_sanyukt_list_member_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {
                Log.d("Harish", "Item Clicked mActivity" + mActivity);
                Intent intent = new Intent(mActivity, SanyuktMemberDetails.class);
                intent.putExtra(AppConstants.MemberId, mMemberList.get(getAdapterPosition()).getMemberId());
                mActivity.startActivity(intent);
            }
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

}
