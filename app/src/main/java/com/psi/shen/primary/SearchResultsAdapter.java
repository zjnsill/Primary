package com.psi.shen.primary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {
    private UserDatabaseManager proxy;
    private String currentUser;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<SingleAlloyItem> searchResults;
    private OnItemClickListener mOnItemClickListener;

    private int expandedHeight=50;

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position,boolean isStarred);
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }

    public SearchResultsAdapter(Context context,ArrayList<SingleAlloyItem> searchResults,String userName){
        this.context=context;
        this.currentUser = userName;
        this.proxy = UserDatabaseManager.getInstance(context,currentUser);
        layoutInflater=LayoutInflater.from(this.context);
        this.searchResults=searchResults;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView NameTV,DensityTV,ComponentTV,MeltingRangeTV,HardnessTV,ResistivityTV;
        private TextView ThermalExpanTV,ThermalConTV,SpecificHeat,moreDetailTV;
        private ImageView foldArrow;
        private LinearLayout foldedDetail;
        View itemView;
        private ImageView isStarredIV;
        public ViewHolder(View itemView){
            super(itemView);
            this.itemView=itemView;
            NameTV = itemView.findViewById(R.id.NameTV);
            DensityTV = itemView.findViewById(R.id.DensityTV);
            ComponentTV = itemView.findViewById(R.id.ComponentTV);
            MeltingRangeTV = itemView.findViewById(R.id.MeltingRangeTV);
            HardnessTV = itemView.findViewById(R.id.HardnessTV);
            ResistivityTV = itemView.findViewById(R.id.ResistivityTV);
            isStarredIV = itemView.findViewById(R.id.isStarredIV);
            //hidden view
            foldedDetail = itemView.findViewById(R.id.FoldedDetail);
            ThermalExpanTV = itemView.findViewById(R.id.ThermalExpanTV);
            ThermalConTV = itemView.findViewById(R.id.ThermalConTV);
            SpecificHeat = itemView.findViewById(R.id.SpecificHeatTV);
            foldArrow = itemView.findViewById(R.id.ExpandIV);
            moreDetailTV = itemView.findViewById(R.id.moreDetailTV);
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
    public void onBindViewHolder(final ViewHolder viewHolder, final int position){
        final SingleAlloyItem Item = searchResults.get(position);
        viewHolder.NameTV.setText(Item.getAlloyName());
        viewHolder.ResistivityTV.setText("Resistivity: "+Item.getResistivity());
        viewHolder.DensityTV.setText("Density: "+Item.getDensity());
        viewHolder.ComponentTV.setText("Component: "+Item.getComponent());
        viewHolder.MeltingRangeTV.setText("Melting Range: "+Item.getMeltingRange_Min()+"~"+Item.getMeltingRange_Max());
        viewHolder.HardnessTV.setText("Hardness: "+Item.getHardness_Min()+"~"+Item.getHardness_Max());
        viewHolder.ThermalConTV.setText("Thermal Conductivity: "+Item.getThermalCon());
        viewHolder.ThermalExpanTV.setText("Thermal Expansion: "+Item.getThermalExpan());
        viewHolder.SpecificHeat.setText("Specific Heat: "+Item.getSpecificHeat());
        final boolean isStarred = proxy.hasItem(Item.getAlloyName());
        if(isStarred){viewHolder.isStarredIV.setImageResource(R.drawable.ic_star);}
        //star interactive methods
        else {viewHolder.isStarredIV.setImageResource(R.drawable.ic_star_empty);}
        /*
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });
        */
        //temporarily abandon this interactive method
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.onLongClick(position,isStarred);
                return false;
            }
        });
        //item expand interactive
        viewHolder.foldArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFold = viewHolder.foldedDetail.getVisibility()==View.GONE;
                foldViewAnimation(isFold,viewHolder.foldArrow);
                if(isFold){
                    startAnim(viewHolder.foldedDetail);
                }else{
                    closeAnim(viewHolder.foldedDetail);
                }
            }
        });
        viewHolder.moreDetailTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });





    }
    //methods to expand the detail view
    private ValueAnimator createDropAnimator(final View v,int start,int end){
        ValueAnimator animator = ValueAnimator.ofInt(start,end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator arg) {
                int value = (int)arg.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
    private void closeAnim(final View view){
        int origheight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view,origheight,0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }
    private void startAnim(View v){
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(v,0,expandedHeight);
        animator.start();
    }
    private void foldViewAnimation(boolean isFold,View toRotate){
        Animation animation;
        if(isFold){
            animation = new RotateAnimation(0,180,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        }else{
            animation = new RotateAnimation(180,0,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        }
        animation.setDuration(100);
        animation.setFillAfter(true);
        animation.setRepeatMode(Animation.REVERSE);
        toRotate.startAnimation(animation);
    }





}
