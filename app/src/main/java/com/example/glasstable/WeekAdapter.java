package com.example.glasstable;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.WeekHolder> {
    class WeekHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        TextView mWeekTextView;
        TextView mDayTextView;

        public WeekHolder(View itemView){
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.weekCardView);
            mWeekTextView=(TextView)itemView.findViewById(R.id.weekDayText);
            mDayTextView=(TextView)itemView.findViewById(R.id.dayText);
        }
    }

    @Override
    public WeekHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(WeekHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
