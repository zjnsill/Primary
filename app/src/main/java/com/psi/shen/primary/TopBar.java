package com.psi.shen.primary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopBar extends RelativeLayout {
    private ImageView leftImg,rightImg;
    private TextView Title;
    private CardView TopbarEntity;
    private LeftAndRightListener leftAndRightListener;
    public interface LeftAndRightListener{
        void leftListener();
        void rightListener();
    }
    public void setLeftAndRightListener(LeftAndRightListener leftAndRightListener){
        this.leftAndRightListener = leftAndRightListener;
    }
    public TopBar(Context ctx, AttributeSet TopbarAttr){
        super(ctx,TopbarAttr);
        LayoutInflater.from(ctx).inflate(R.layout.topbar,this);
        leftImg = findViewById(R.id.TopbarLeftImg);
        rightImg = findViewById(R.id.TopbarRightImg);
        Title = findViewById(R.id.TopbarTitle);
        TopbarEntity = findViewById(R.id.Topbar);
        TypedArray typedArray = ctx.obtainStyledAttributes(TopbarAttr,R.styleable.TopBarAttr);
        int textColor = typedArray.getColor(R.styleable.TopBarAttr_TextColor,0);
        int topbarColor = typedArray.getColor(R.styleable.TopBarAttr_TopbarColor,0);
        int leftImgRsc = typedArray.getResourceId(R.styleable.TopBarAttr_LeftImg,0);
        int rightImgRsc = typedArray.getResourceId(R.styleable.TopBarAttr_RightImg,0);
        float textSize = typedArray.getDimension(R.styleable.TopBarAttr_TextSize,0);
        String textString = typedArray.getString(R.styleable.TopBarAttr_Text);
        typedArray.recycle();
        leftImg.setImageResource(leftImgRsc);
        rightImg.setImageResource(rightImgRsc);
        Title.setText(textString);
        Title.setTextSize(textSize);
        Title.setTextColor(textColor);
        TopbarEntity.setCardBackgroundColor(topbarColor);
        leftImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { leftAndRightListener.leftListener(); }
        });
        rightImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { leftAndRightListener.rightListener(); }
        });
    }
}
