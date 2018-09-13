package com.psi.shen.primary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RoundRectBtn extends RelativeLayout {
    private CardView BtnCV;
    private ImageView BtnImg;
    private TextView BtnText;
    private BtnOnClickListenr onClickListener;

    public interface BtnOnClickListenr{
        void BtnOnClick();
    }
    public void setBtnOnClickListener(BtnOnClickListenr listener){
        this.onClickListener=listener;
    }
    public RoundRectBtn(Context ctx, AttributeSet RoundRectBtnAttr){
        super(ctx,RoundRectBtnAttr);
        LayoutInflater.from(ctx).inflate(R.layout.round_rect_btn,this);
        BtnCV = findViewById(R.id.RoundRectBtn);
        BtnImg= findViewById(R.id.BtnImg);
        BtnText = findViewById(R.id.BtnText);
        TypedArray typedArray = ctx.obtainStyledAttributes(RoundRectBtnAttr,R.styleable.RoundRectBtnAttr);
        String text = typedArray.getString(R.styleable.RoundRectBtnAttr_BtnText);
        int ImgSrc = typedArray.getResourceId(R.styleable.RoundRectBtnAttr_BtnImg,0);
        int BtnColor = typedArray.getColor(R.styleable.RoundRectBtnAttr_BtnBackgroundColor,0);
        typedArray.recycle();
        BtnText.setText(text);
        BtnImg.setImageResource(ImgSrc);
        BtnCV.setCardBackgroundColor(BtnColor);
        this.BtnCV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.BtnOnClick();
            }
        });
    }
}
