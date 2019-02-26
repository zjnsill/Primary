package com.psi.shen.primary;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {
    private signedUser currentUser;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Bundle> searchResults;
    private ArrayList<String> starredAlloys;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);
        void onLongClick(int position,boolean isStarred);
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public SearchResultsAdapter(Context context, ArrayList<Bundle> searchResults, ArrayList<String> starredAlloys, signedUser user) {
        this.context = context;
        this.currentUser = user;
        layoutInflater = LayoutInflater.from(this.context);
        this.searchResults = searchResults;
        this.starredAlloys = starredAlloys;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView NameTV, IntroTV;
        private ImageView isStarredIV;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            NameTV = itemView.findViewById(R.id.NameTV);
            IntroTV = itemView.findViewById(R.id.IntroTV);
            isStarredIV = itemView.findViewById(R.id.isStarredIV);
        }
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_in_results,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Bundle alloy = searchResults.get(position);
        viewHolder.NameTV.setText(alloy.getString("Name"));
        viewHolder.IntroTV.setText(alloy.getString("Introduction"));

        final boolean isStarred = starredAlloys.contains(alloy.get("Name"));
        if(isStarred) {
            viewHolder.isStarredIV.setImageResource(R.drawable.ic_star);
        } else {
            viewHolder.isStarredIV.setImageResource(R.drawable.ic_star_empty);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.onLongClick(position,isStarred);
                return false;
            }
        });
    }
}
