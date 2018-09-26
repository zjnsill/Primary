package com.psi.shen.primary;

import android.content.Context;
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
    private ArrayList<StarredListItem> mStarredList;/////
    private onItemClickListener mOnItemClickListener;

    public interface onItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener monItemClickListener){
        this.mOnItemClickListener = monItemClickListener;
    }

    public StarredListAdapter(Context context,ArrayList<StarredListItem> mStarredList){
        this.context=context;
        this.mStarredList=mStarredList;
        layoutInflater=LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,density,elasticModu,meltingRange,hardness;
        ImageView CloudStatus,isSelfCreated,isToBeDeleted;
        View view;

        public ViewHolder(View itemView){
            super(itemView);
            this.view = itemView;
            name = view.findViewById(R.id.name);
            density = view.findViewById(R.id.density);
            elasticModu = view.findViewById(R.id.elasticModu);
            meltingRange = view.findViewById(R.id.meltingRange);
            hardness = view.findViewById(R.id.hardness);
            CloudStatus = itemView.findViewById(R.id.CloudStatus);
            isSelfCreated = itemView.findViewById(R.id.isSelfCreated);
            isToBeDeleted = itemView.findViewById(R.id.isToBeDeleted);
        }
    }
    @Override
    public int getItemCount(){
        return mStarredList.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType){
        View itemView = layoutInflater.inflate(R.layout.starred_list_item,viewGroup,false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position){
        final StarredListItem starredListItem = mStarredList.get(position);
        SingleAlloyItem singleAlloyItem=starredListItem.getAlloyItem();
        if(starredListItem.isToBeDeleted){viewHolder.isToBeDeleted.setImageResource(R.drawable.ic_delete);}
        if(starredListItem.isSelfCreated){viewHolder.isSelfCreated.setImageResource(R.drawable.ic_perm_identity);}
        if(starredListItem.isCould){viewHolder.CloudStatus.setImageResource(R.drawable.ic_cloud_done);}
        else{viewHolder.CloudStatus.setImageResource(R.drawable.ic_cloud_off);}
        if(starredListItem.isToBeClouded){viewHolder.CloudStatus.setImageResource(R.drawable.ic_cloud_upload);}
        viewHolder.name.setText(singleAlloyItem.Validation[0]? "Name: "+singleAlloyItem.getAlloyName():"No Name");
        viewHolder.density.setText(singleAlloyItem.Validation[2]? "Density: "+singleAlloyItem.getDensity():"No density data");
        viewHolder.elasticModu.setText(singleAlloyItem.Validation[7]? "Elastic modulus: "+singleAlloyItem.getElasticModu():"No elastic modulus data");
        viewHolder.meltingRange.setText(singleAlloyItem.Validation[8]? "Melting Range: "+singleAlloyItem.getMeltingRange_Min()+"~"+singleAlloyItem.getMeltingRange_Max():"No Data");
        viewHolder.hardness.setText(singleAlloyItem.Validation[9]? "Hardness: "+singleAlloyItem.getHardness_Min()+"~"+singleAlloyItem.getHardness_Max():"No hardness data");
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
