package com.example.glasstable;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    class CourseHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        TextView mTextView;

        public CourseHolder(View itemView){
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.numberCardView);
            mTextView=(TextView)itemView.findViewById(R.id.courseInfo);
        }

        public void bind(Course course){
            String info=course.getCourseName()+"\n"+course.getClassroom();
            mTextView.setText(info);
        }
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
