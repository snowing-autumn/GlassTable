package com.example.glasstable;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.WeekHolder> {
    private Context mContext;
    //获取系统时间
    private Calendar mCalendar=Calendar.getInstance();
    //周
    private ArrayList<String> mWeekDay;

    public WeekAdapter(Context context){

        mContext=context;
        mWeekDay=new ArrayList<>();
        mWeekDay.add("周一");
        mWeekDay.add("周二");
        mWeekDay.add("周三");
        mWeekDay.add("周四");
        mWeekDay.add("周五");
        mWeekDay.add("周六");
        mWeekDay.add("周日");
    }

    class WeekHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        TextView mWeekDayText;
        TextView mDayText;

        public WeekHolder(View itemView){
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.weekCardView);
            mWeekDayText=(TextView)itemView.findViewById(R.id.weekDayText);
            mDayText=(TextView)itemView.findViewById(R.id.dayText);
        }

        public void bind(int position,ArrayList<String> weekDay){
                mWeekDayText.setText(weekDay.get(position));
        }
    }

    @Override
    public WeekHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WeekHolder weekHolder=new WeekHolder(LayoutInflater.from(mContext).inflate(R.layout.week_view,parent,false));
        return weekHolder;
    }

    @Override
    public void onBindViewHolder(WeekHolder holder, int position) {
        holder.bind(position,mWeekDay);
    }

    @Override
    public int getItemCount() {
        return mWeekDay.size();
    }
}
