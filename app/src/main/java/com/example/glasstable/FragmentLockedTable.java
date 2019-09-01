package com.example.glasstable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class FragmentLockedTable extends Fragment {
    private ArrayList<ArrayList<Course>> coursesArray;
    private RecyclerView mRecyclerView;
    private ArrayList<courseTime> mCourseTimes=courseTime.getInstance();

    public enum ITEM_TYPE {
        ITEM_TYPE_WEEK,
        ITEM_TYPE_NUM,
        ITEM_TYPE_COURSE
    }

    public  ArrayList<ArrayList<String>> tableConstruct(){
        ArrayList<ArrayList<String>> table=new ArrayList<>();
        ArrayList<String> date=new ArrayList<>();
        coursesArray=((MainActivity)getActivity()).getCourseArray();
        date.add("星期一");
        date.add("星期二");
        date.add("星期三");
        date.add("星期四");
        date.add("星期五");
        date.add("星期六");
        date.add("星期日");
        table.add(date);
        if(coursesArray==null) {
            for (int i = 0; i < 12; i++) {
                ArrayList<String> temp = new ArrayList<>();
                for (int j = 0; j < 8; j++) {
                    if (j == 0)
                        temp.add("第" + (i + 1) + "节");
                    else
                        temp.add("" + i * 12 + j);
                }
                table.add(temp);
            }
        }else {
            for (int i = 0; i < 12; i++) {
                ArrayList<String> temp = new ArrayList<>();
                for (int j = 0; j < 8; j++) {
                    if (j == 0)
                        temp.add("第" + (i + 1) + "节");
                    else
                        temp.add(coursesArray.get(i).get(j-1).getCourseName());
                }
                table.add(temp);
            }
        }
        return table;
    }





    private class CourseHolder extends  RecyclerView.ViewHolder{
        private Course mCourse;
        private TextView mCourseInfo;

        public CourseHolder(View view){
            super(view);
            mCourseInfo=(TextView)view.findViewById(R.id.courseInfo);
        }
        public void bind(Course course){
            mCourse=course;
            mCourseInfo.setText(mCourse.getCourseName()+"\n"+mCourse.getClassroom());
        }
    }

    private class WeekHolder extends  RecyclerView.ViewHolder{
        private TextView mWeekDayText;
        private TextView mDayText;
        public WeekHolder(View view){
            super(view);
            mWeekDayText=(TextView)view.findViewById(R.id.weekDayText);
            mDayText=(TextView)view.findViewById(R.id.dayText);
        }
    }

    private class NumberHolder extends RecyclerView.ViewHolder{

        private TextView mStartTimeText;
        private TextView mNumberText;
        public NumberHolder(View view){
            super(view);
            mStartTimeText=(TextView)view.findViewById(R.id.startTimeText);
            mNumberText=(TextView)view.findViewById(R.id.numberText);
        }
    }

    private class  tableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private List<Course> mCourses;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater= LayoutInflater.from(getActivity());
            if(ITEM_TYPE.ITEM_TYPE_WEEK.ordinal()==i)
                return new WeekHolder(inflater.inflate(R.layout.week_view,viewGroup,false));
            else if(ITEM_TYPE.ITEM_TYPE_NUM.ordinal()==i)
                return new NumberHolder(inflater.inflate(R.layout.number_view,viewGroup,false));
            else if(ITEM_TYPE.ITEM_TYPE_COURSE.ordinal()==i)
                return new CourseHolder(inflater.inflate(R.layout.single_course_view,viewGroup,false));
            else
                return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return mCourses.size();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View table=inflater.inflate(R.layout.course_fragment,container,false);
        mRecyclerView =(RecyclerView) table.findViewById(R.id.tableRecyclerView);
        return table;
    }


}

/**/