package com.example.glasstable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;



public class MyHorizontalScrollView extends HorizontalScrollView {

    public interface OnMyScrollViewChangeListener {
        void OnOnMyScrollViewChange(MyHorizontalScrollView myHorizontalScrollView,int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    private OnMyScrollViewChangeListener listener;

    public MyHorizontalScrollView(Context context){
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attributes){
        super(context,attributes);
    }

    public MyHorizontalScrollView(Context context,AttributeSet attributeSet,int defStyleAttr){
        super(context,attributeSet,defStyleAttr);
    }

    public void setMyScrollChangeListener(OnMyScrollViewChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener!=null)
            listener.OnOnMyScrollViewChange(MyHorizontalScrollView.this,l,t,oldl,oldt);
    }
}
