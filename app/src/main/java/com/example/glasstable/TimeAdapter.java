package com.example.glasstable;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeHolder> {
    //课程时间列表
    private ArrayList<courseTime> mCourseTimes;
    private Context mContext;

    public TimeAdapter(ArrayList<courseTime> mCourseTimes,Context mContext){
        this.mCourseTimes=mCourseTimes;
        this.mContext=mContext;
    }


    class TimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView mCardView;
        TextView mStartTimeText;
        TextView mNumberText;

        public TimeHolder(View itemView){
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.numberCardView);
            mStartTimeText=(TextView)itemView.findViewById(R.id.startTimeText);
            mNumberText=(TextView)itemView.findViewById(R.id.numberText);
        }

        @Override
        public void onClick(View v) {
            //调用时间修改弹窗
        }
    }



    @Override
    public TimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TimeHolder viewHolder=new TimeHolder(LayoutInflater.from(mContext).inflate(R.layout.number_view,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TimeHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mCourseTimes.size();
    }


}
