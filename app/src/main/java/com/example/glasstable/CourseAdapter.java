package com.example.glasstable;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private ArrayList<Course> mCourses;
    private Context mContext;

    public CourseAdapter(ArrayList<Course> mCourses, Context mContext){
        this.mCourses=mCourses;
        this.mContext=mContext;
    }

    class CourseHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
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

        @Override
        public void onClick(View v) {
           //弹出课程信息
        }

        @Override
        public boolean onLongClick(View v) {
            //弹出课程删除/修改/添加
            return false;
        }
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CourseHolder mCourseHolder= new CourseHolder(LayoutInflater.from(mContext).inflate(R.layout.single_course_view,parent,false));
        return  mCourseHolder;
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {
        holder.bind(mCourses.get(position));

    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }
}
