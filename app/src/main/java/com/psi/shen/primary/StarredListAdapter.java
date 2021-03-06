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

public class StarredListAdapter extends RecyclerView.Adapter<StarredListAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Bundle> starredAlloys;
    private onItemClickListener mOnItemClickListener;

    public interface onItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener monItemClickListener){
        this.mOnItemClickListener = monItemClickListener;
    }

    public StarredListAdapter(Context context, ArrayList<Bundle> starredAlloys){
        this.context = context;
        this.starredAlloys = starredAlloys;
        layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView NameTV, IntroTV;
        private ImageView isStarredIV;
        View view;

        public ViewHolder(View itemView){
            super(itemView);
            this.view = itemView;
            NameTV = itemView.findViewById(R.id.NameTV);
            IntroTV = itemView.findViewById(R.id.IntroTV);
            isStarredIV = itemView.findViewById(R.id.isStarredIV);
        }
    }

    @Override
    public int getItemCount() {
        return starredAlloys.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_in_results, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        Bundle alloy = starredAlloys.get(position);
        viewHolder.NameTV.setText(alloy.getString("Name"));
        viewHolder.IntroTV.setText(alloy.getString("Introduction"));

        viewHolder.isStarredIV.setImageResource(R.drawable.ic_star);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.onLongClick(position);
                return false;
            }
        });
    }
}
