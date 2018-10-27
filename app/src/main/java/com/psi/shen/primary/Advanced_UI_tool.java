package com.psi.shen.primary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
//this class is build for guassian blur, however, if this blurring effect is not satisfying, delete this file to keep organized;
public class Advanced_UI_tool {
    public Advanced_UI_tool(){}

    public Bitmap AddBlur(Bitmap inputImage, Context context, float BlurRadius){
        Bitmap output = Bitmap.createBitmap(inputImage);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur gaussianBlur = ScriptIntrinsicBlur.create(rs,Element.U8_4(rs));
        Allocation allIn = Allocation.createFromBitmap(rs,inputImage);
        Allocation allOut = Allocation.createFromBitmap(rs,output);
        gaussianBlur.setRadius(BlurRadius);
        gaussianBlur.setInput(allIn);
        gaussianBlur.forEach(allOut);
        allOut.copyTo(output);
        rs.destroy();
        return output;
    }
    //be cautious when using the following two methods under the situation that content if continuously changing
    public Bitmap getResizedScreenShot(Activity activity, int margin, int startY, int endY){
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //get statusbar height, in case of other usage;
        Rect rect =  new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int tsatusBarHeight = rect.top;
        //
        WindowManager windowManager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        //
        int startX = margin;
        int endX = width-margin;
        Bitmap orginalShot = view.getDrawingCache(false);
        Bitmap outMap = Bitmap.createBitmap(orginalShot,startX,startY,width,endY-startY);
        return outMap;
    }
    public Bitmap getScreenShotFromBottom(Activity activity,int margin,int ViewHeight){
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //get statusbar height, in case of other usage;
        Rect rect =  new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int tsatusBarHeight = rect.top;
        //
        WindowManager windowManager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        //
        int startX = margin;
        int endX = width-margin;
        Bitmap orginalShot = view.getDrawingCache(false);
        Bitmap outMap = Bitmap.createBitmap(orginalShot,startX,height-ViewHeight,width-2*margin,ViewHeight);
        return outMap;
    }
}
