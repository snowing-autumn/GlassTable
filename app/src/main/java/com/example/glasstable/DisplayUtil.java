package com.example.glasstable;

import android.content.Context;


public class DisplayUtil {


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getPhoneWidthPx(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getPhoneHeightPx(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
