package com.example.glasstable;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;


import java.util.ArrayList;

public class CourseAppWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<Course> mCourseOnWeek;
    private ArrayList<ArrayList<Course>> mCoursesByWeekDay;
    private ArrayList<Course> mCourseOnWeek1=new ArrayList<>();
    private ArrayList<Course> mCourseOnWeek2=new ArrayList<>();
    private ArrayList<Course> mCourseOnWeek3=new ArrayList<>();
    private ArrayList<Course> mCourseOnWeek4=new ArrayList<>();
    private ArrayList<Course> mCourseOnWeek5=new ArrayList<>();
    private ArrayList<Course> mCourseOnWeek6=new ArrayList<>();
    private ArrayList<Course> mCourseOnWeek7=new ArrayList<>();
    private int[] mLineLayoutId={R.id.MondayWidgetContainer,R.id.TuesdayWidgetContainer,
            R.id.WednesdayWidgetContainer, R.id.ThursdayWidgetContainer, R.id.FridayWidgetContainer,
            R.id.SaturdayWidgetContainer, R.id.SundayWidgetContainer};
    private int[] mColor={R.color.powderblue,R.color.tomato,
            R.color.mediumslateblue, R.color.royalblue,
            R.color.forestgreen, R.color.darkviolet,
            R.color.crimson, R.color.orangered,
            R.color.burlywood
    };


    public CourseAppWidgetFactory(Context context,ArrayList<Course> CourseOnWeek){
        mContext=context;
        mCourseOnWeek=CourseOnWeek;
        initCourse();
    }


    private void initCourse(){
        int mColorIndex=0;
        for(Course course:mCourseOnWeek) {
            course.setColor(mColor[mColorIndex % 9]);
            mColorIndex++;
        }
        for(Course course:mCourseOnWeek){
            switch (course.getWeekDay()){
                case 1:mCourseOnWeek1.add(course);break;
                case 2:mCourseOnWeek2.add(course);break;
                case 3:mCourseOnWeek3.add(course);break;
                case 4:mCourseOnWeek4.add(course);break;
                case 5:mCourseOnWeek5.add(course);break;
                case 6:mCourseOnWeek6.add(course);break;
                case 7:mCourseOnWeek7.add(course);break;
            }
        }
        mCoursesByWeekDay=new ArrayList<>();
        mCoursesByWeekDay.add(mCourseOnWeek1);
        mCoursesByWeekDay.add(mCourseOnWeek2);
        mCoursesByWeekDay.add(mCourseOnWeek3);
        mCoursesByWeekDay.add(mCourseOnWeek4);
        mCoursesByWeekDay.add(mCourseOnWeek5);
        mCoursesByWeekDay.add(mCourseOnWeek6);
        mCoursesByWeekDay.add(mCourseOnWeek7);
        System.out.println(mCoursesByWeekDay);
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
        return 1;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        TextView textView=new TextView(mContext);
        RemoteViews remoteViews=new RemoteViews(mContext.getPackageName(),R.layout.widget_list);
        for(int i=0;i<8;i++){
            RemoteViews temp;
            if(i==0){
                for(int j=1;j<13;j++){
                    temp=new RemoteViews(mContext.getPackageName(),R.layout.widget_item_1);
                    temp.setInt(R.id.widget_text_1,"setBackgroundColor",R.color.transparent);
                    temp.setTextViewText(R.id.widget_text_1,""+j);
                    remoteViews.addView(R.id.numWidgetContainer,temp);
                }
            }
            else{
                for(Course course:mCoursesByWeekDay.get(i-1)){
                    System.out.println(course.getEndNumber()-course.getStartNumber());
                    switch (course.getEndNumber()-course.getStartNumber()){
                        case 1:temp=new RemoteViews(mContext.getPackageName(),R.layout.widget_item_1);
                            temp.setInt(R.id.widget_text_1,"setBackgroundColor",course.getColor());
                            temp.setTextViewText(R.id.widget_text_1,course.getCourseName()+"\n"+course.getClassroom());
                            remoteViews.addView(mLineLayoutId[i-1],temp);
                            break;
                        case 2:temp=new RemoteViews(mContext.getPackageName(),R.layout.widget_item_2);
                            temp.setInt(R.id.widget_text_2,"setBackgroundColor",course.getColor());
                            temp.setTextViewText(R.id.widget_text_2,course.getCourseName()+"\n"+course.getClassroom());
                            remoteViews.addView(mLineLayoutId[i-1],temp);
                            break;
                        case 3:temp=new RemoteViews(mContext.getPackageName(),R.layout.widget_item_3);
                            temp.setInt(R.id.widget_text_3,"setBackgroundColor",course.getColor());
                            temp.setTextViewText(R.id.widget_text_3,course.getCourseName()+"\n"+course.getClassroom());
                            remoteViews.addView(mLineLayoutId[i-1],temp);
                            break;
                        case 4:temp=new RemoteViews(mContext.getPackageName(),R.layout.widget_item_4);
                            temp.setInt(R.id.widget_text_4,"setBackgroundColor",course.getColor());
                            temp.setTextViewText(R.id.widget_text_4,course.getCourseName()+"\n"+course.getClassroom());
                            remoteViews.addView(mLineLayoutId[i-1],temp);
                            break;
                    }
                }
            }

        }
        return remoteViews;

    }

    @Override
    public int getViewTypeCount() {
        return 1;
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
