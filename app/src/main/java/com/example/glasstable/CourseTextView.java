package com.example.glasstable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.TextView;

public class CourseTextView extends TextView {
    private int colorNum;
    private int[] mColor={R.color.powderblue,R.color.tomato,
            R.color.mediumslateblue, R.color.royalblue,
            R.color.forestgreen, R.color.darkviolet,
            R.color.crimson, R.color.orangered,
            R.color.burlywood
    };

    public CourseTextView(Context mContext){
       super(mContext);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setAlpha(200);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mColor[colorNum]);
        canvas.drawRoundRect(new RectF(0,0,getMeasuredWidth(),getMaxHeight()),0,0,paint);
    }
}
