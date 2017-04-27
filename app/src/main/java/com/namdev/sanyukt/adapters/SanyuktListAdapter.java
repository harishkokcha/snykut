package com.namdev.sanyukt.adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.namdev.sanyukt.AddEditPersonDetails;
import com.namdev.sanyukt.R;
import com.namdev.sanyukt.SanyuktMemberDetails;
import com.namdev.sanyukt.beans.AppConstants;
import com.namdev.sanyukt.beans.Member;
import com.namdev.sanyukt.beans.MemberResponse;
import com.namdev.sanyukt.utils.GenericRequest;
import com.namdev.sanyukt.views.LetterTileProvider;

import java.util.List;

/**
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
    private List<Member> mMemberList;
    private String mclass;

    public SanyuktListAdapter(Activity context, RecyclerView mRecyclerView, List<Member> memberList, String myString) {
        this.mActivity = context;
        mMemberList = memberList;
        mclass = myString;
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
            /*TextDrawable drawable = TextDrawable.builder()
                    .buildRect("A", Color.RED);

            userViewHolder.imageView.setImageDrawable(drawable);*/

            final Resources res = mActivity.getResources();
            final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);

            final LetterTileProvider tileProvider = new LetterTileProvider(mActivity);
            final Bitmap letterTile = tileProvider.getLetterTile(String.valueOf(mMemberList.get(position).getMemberName().charAt(0)), mMemberList.get(position).getMemberName(), tileSize, tileSize);
            userViewHolder.imageView.setImageBitmap(new BitmapDrawable(mActivity.getResources(), letterTile).getBitmap());
            userViewHolder.name.setText(mMemberList.get(position).getMemberName());

            userViewHolder.ageHeight.setText(mMemberList.get(position).getMemberAge() + " ( " + mMemberList.get(position).getMemberHeight() + ")");
            userViewHolder.edu.setText(mMemberList.get(position).getMemberEducation() + " (" + mMemberList.get(position).getMemberEmployeeDetails() + " )");
            userViewHolder.city.setText(mMemberList.get(position).getMemberCity());

            if (mclass.equals(AppConstants.MY_FAMILY_FRAGMENT)) {
//                userViewHolder.remove.setVisibility(View.VISIBLE);
                userViewHolder.edit.setVisibility(View.VISIBLE);
            } else {
                userViewHolder.remove.setVisibility(View.GONE);
                userViewHolder.edit.setVisibility(View.GONE);
            }

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

    public void addAll(List<Member> memberList) {
        mMemberList.addAll(memberList);
        notifyDataSetChanged();
    }

    private class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, ageHeight, edu, city, createdBy, remove, edit;
        ImageView imageView;

        MemberViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.id_sanyukt_list_member_name);
            remove = (TextView) itemView.findViewById(R.id.id_sanyukt_list_member_remove);
            edit = (TextView) itemView.findViewById(R.id.id_sanyukt_list_member_edit);
            ageHeight = (TextView) itemView.findViewById(R.id.id_sanyukt_list_member_age);
            edu = (TextView) itemView.findViewById(R.id.id_sanyukt_list_member_education);
            city = (TextView) itemView.findViewById(R.id.id_sanyukt_list_member_city);
            createdBy = (TextView) itemView.findViewById(R.id.id_sanyukt_list_member_created_by);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
            remove.setOnClickListener(this);
            edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {
                Intent intent = new Intent(mActivity, SanyuktMemberDetails.class);
                intent.putExtra(AppConstants.MemberId, mMemberList.get(getAdapterPosition()).getMemberId());
                mActivity.startActivity(intent);
            } else if (v == remove) {
                // Delete user api
            } else if (v == edit) {
                Intent intent = new Intent(mActivity, AddEditPersonDetails.class);
                intent.putExtra(AppConstants.MemberId, mMemberList.get(getAdapterPosition()).getMemberId());
                mActivity.startActivity(intent);
            }

        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar_example);
        }
    }

}
