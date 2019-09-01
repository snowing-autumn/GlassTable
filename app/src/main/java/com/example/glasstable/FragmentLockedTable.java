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
    private RecyclerView mNumberRecyclerView;
    private RecyclerView mCourseRecyclerView;
    private ArrayList<courseTime> mCourseTimes=courseTime.getInstance();

    public enum ITEM_TYPE {
        ITEM_TYPE_WEEK,
        ITEM_TYPE_NUM,
        ITEM_TYPE_COURSE
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View table=inflater.inflate(R.layout.locked_table,container,false);
        mNumberRecyclerView =(RecyclerView) table.findViewById(R.id.lockedCowView);
        mCourseRecyclerView=(RecyclerView)table.findViewById(R.id.unlockedRecyclerView);
        return table;
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





}

/**/