package com.psi.shen.primary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {
    private StarredListDatabaseManager proxy;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<SingleAlloyItem> searchResults;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position,boolean isStarred);
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }

    public SearchResultsAdapter(Context context,ArrayList<SingleAlloyItem> searchResults,StarredListDatabaseManager proxy){
        this.context=context;
        this.proxy = proxy;
        layoutInflater=LayoutInflater.from(this.context);
        this.searchResults=searchResults;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView NameTV,DensityTV,ElasticModuTV,MeltingRangeTV,HardnessTV,ResistivityTV;
        View itemView;
        private ImageView isStarredIV;
        public ViewHolder(View itemView){
            super(itemView);
            this.itemView=itemView;
            NameTV = itemView.findViewById(R.id.NameTV);
            DensityTV = itemView.findViewById(R.id.DensityTV);
            ElasticModuTV = itemView.findViewById(R.id.ElasticModuTV);
            MeltingRangeTV = itemView.findViewById(R.id.MeltingRangeTV);
            HardnessTV = itemView.findViewById(R.id.HardnessTV);
            ResistivityTV = itemView.findViewById(R.id.ResistivityTV);
            isStarredIV = itemView.findViewById(R.id.isStarredIV);
        }
    }

    @Override
    public int getItemCount(){
        return searchResults.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewtype){
        View itemView = layoutInflater.inflate(R.layout.item_in_results,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position){
        final SingleAlloyItem Item = searchResults.get(position);
        viewHolder.NameTV.setText(Item.getAlloyName());
        viewHolder.ResistivityTV.setText("Resistivity: "+Item.getResistivity()+"");
        viewHolder.DensityTV.setText("Density: "+Item.getDensity()+"");
        viewHolder.ElasticModuTV.setText("Elastic modulus: "+Item.getElasticModu()+"");
        viewHolder.MeltingRangeTV.setText("Melting Range: "+Item.getMeltingRange_Min()+"~"+Item.getMeltingRange_Max());
        viewHolder.HardnessTV.setText("Hardness: "+Item.getHardness()+"");
        final boolean isStarred = proxy.hasItem(Item.getAlloyName());
        if(isStarred){viewHolder.isStarredIV.setImageResource(R.drawable.ic_star);}
        else {viewHolder.isStarredIV.setImageResource(R.drawable.ic_star_empty);}
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
