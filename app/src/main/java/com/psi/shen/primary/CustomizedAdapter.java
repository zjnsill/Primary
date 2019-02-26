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

public class CustomizedAdapter extends RecyclerView.Adapter<CustomizedAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Bundle> customizedAlloys;
    private onItemClickListener mOnItemClickListener;

    public interface onItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener monItemClickListener){
        this.mOnItemClickListener = monItemClickListener;
    }

    public CustomizedAdapter(Context context, ArrayList<Bundle> customizedAlloys) {
        this.context = context;
        this.customizedAlloys = customizedAlloys;
        layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        return customizedAlloys.size();
    }

    @Override
    public CustomizedAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_in_results, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomizedAdapter.ViewHolder viewHolder, final int position) {
        Bundle alloy = customizedAlloys.get(position);
        viewHolder.NameTV.setText(alloy.getString("Name"));
        if(alloy.containsKey("Introduction"))
            viewHolder.IntroTV.setText(alloy.getString("Introduction"));

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
