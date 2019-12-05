package com.example.glasstable;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import java.util.ArrayList;

public class CourseAppWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<Course> mCourseOnWeek;


    public CourseAppWidgetFactory(Context context,ArrayList<Course> CourseOnWeek){
        mContext=context;
        mCourseOnWeek=CourseOnWeek;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public int getCount() {
        return mCourseOnWeek.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(position<0||position>=mCourseOnWeek.size())
            return null;
        String mCourseInfo=mCourseOnWeek.get(position).getCourseName() + "\n" + mCourseOnWeek.get(position).getClassroom();
        int length=mCourseOnWeek.get(position).getEndNumber()-mCourseOnWeek.get(position).getStartNumber();
        return
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
