package com.example.glasstable;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeHolder> {
    class TimeHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        TextView mStartTimeText;
        TextView mNumberText;

        public TimeHolder(View itemView){
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.numberCardView);
            mStartTimeText=(TextView)itemView.findViewById(R.id.startTimeText);
            mNumberText=(TextView)itemView.findViewById(R.id.numberText);
        }
    }

    @Override
    public TimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TimeHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
