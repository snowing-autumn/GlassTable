package com.example.glasstable.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.example.glasstable.model.ConflictedCourse;
import com.example.glasstable.model.Course;
import com.example.glasstable.R;

import java.util.ArrayList;
import java.util.Comparator;

public class CourseAppWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<Course> mCourseOnWeek;
    private ArrayList<Course> mCourseOnTable;
    private ArrayList<ConflictedCourse> mConflictedCourses;
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
    private int[] mColor={R.color.powderblueTr,R.color.tomatoTr,
            R.color.mediumslateblueTr, R.color.royalblueTr,
            R.color.forestgreenTr, R.color.darkvioletTr,
            R.color.crimsonTr, R.color.orangeredTr,
            R.color.burlywoodTr
    };


    public CourseAppWidgetFactory(Context context,ArrayList<Course> CourseOnWeek){
        mConflictedCourses=new ArrayList<>();
        mCourseOnTable=new ArrayList<>();
        mContext=context;
        if(mCourseOnWeek==null||mCourseOnWeek!=CourseOnWeek) {
            if(mCourseOnWeek!=null)
                mCourseOnWeek.clear();
            mCourseOnWeek = CourseOnWeek;
            initCourse();
        }
    }

    private void findConflictedCourse(){
        int[] flag=new int[84];
        for(int i=0;i<84;i++)
            flag[i]=0;
        //课程冲突查找
        for(Course course:mCourseOnWeek){
            ConflictedCourse tempConflictedCourse=new ConflictedCourse(course.getWeekDay(),course.getStartNumber(),course.getEndNumber(),course.getCourseName());
            tempConflictedCourse.addCourse(course);
            mConflictedCourses.add(tempConflictedCourse);
            boolean conflicted=false;
            int startPosition=(course.getWeekDay()-1)*12+course.getStartNumber()-1;
            int endPosition=(course.getWeekDay()-1)*12+course.getEndNumber()-1;
            for(int j=startPosition;j<endPosition;j++){
                if(flag[j]>0)
                    conflicted=true;
                flag[j]++;
            }
            if(conflicted)
                for(ConflictedCourse conflictedCourse:mConflictedCourses){
                    conflictedCourse.addCourse(course);
                }
        }
    }

    private void initCourse() {
        {
        findConflictedCourse();
        int[] flag = new int[84];
        for (int i = 0; i < 84; i++)
            flag[i] = 0;
        for (ConflictedCourse conflictedCourse : mConflictedCourses) {
            int startPosition = (conflictedCourse.getWeekDay() - 1) * 12 + conflictedCourse.getStatTime() - 1;
            int endPosition = (conflictedCourse.getWeekDay() - 1) * 12 + conflictedCourse.getEndTime() - 1;
            for (int j = startPosition; j < endPosition; j++) {
                flag[j]++;
            }
        }
        mConflictedCourses.sort(new Comparator<ConflictedCourse>() {
            @Override
            public int compare(ConflictedCourse o1, ConflictedCourse o2) {
                return (o1.getStatTime() + o1.getWeekDay() * 12) - (o2.getStatTime() + o2.getWeekDay() * 12);
            }
        });
        //添加空白课程
        int start = 0;
        int startWeekDay = 1;
        for (int index = 0; index < mConflictedCourses.size(); index++) {
            int end = mConflictedCourses.get(index).getStatTime();
            int endWeekDay = mConflictedCourses.get(index).getWeekDay();
            //同一天课间隔或顺延至下一天
            if (endWeekDay - startWeekDay == 0 || endWeekDay - startWeekDay == 1 && start == 12 && end == 1) {
                //同一天课间隔
                if (end - start > 1) {
                    Course emptyCourse = new Course(startWeekDay, start + 1, end - 1);
                    mCourseOnTable.add(emptyCourse);
                }
            } else {
                //下一节课来到了第二天，甚至存在N整天没课
                for (int re = 0; re < endWeekDay - startWeekDay; re++) {
                    Course emptyCourse;
                    if (re == 0)
                        emptyCourse = new Course(startWeekDay + re, start + 1, 12);
                    else
                        emptyCourse = new Course(startWeekDay + re, 1, 12);
                    mCourseOnTable.add(emptyCourse);
                }
                start = 0;
                if (end - start > 1) {
                    startWeekDay = mConflictedCourses.get(index).getWeekDay();
                    Course emptyCourse = new Course(startWeekDay, 1, end - 1);
                    mCourseOnTable.add(emptyCourse);
                }
            }
            if (!mConflictedCourses.get(index).isConflicted())
                mCourseOnTable.add(mConflictedCourses.get(index).getConflictedCourses().get(0));
            else {
                Course course = new Course(mConflictedCourses.get(index).getWeekDay(), mConflictedCourses.get(index).getStatTime(),
                        mConflictedCourses.get(index).getEndTime());
                course.setCourseName("该课程冲突");
            }
            start = mConflictedCourses.get(index).getEndTime();
            startWeekDay = mConflictedCourses.get(index).getWeekDay();
        }
        if (startWeekDay != 7) {
            if (start != 12) {
                Course emptyCourse = new Course(startWeekDay, start + 1, 12);
                mCourseOnTable.add(emptyCourse);
            }
            for (int i = startWeekDay + 1; i <= 7; i++) {
                Course emptyCourse = new Course(startWeekDay, 1, 12);
                mCourseOnTable.add(emptyCourse);
            }
        }

        System.out.println(mCourseOnTable);
        int mColorIndex=0;
        for(Course course:mCourseOnTable) {
            if(course.getCourseName()!="") {
                course.setColor(mContext.getColor(mColor[mColorIndex % 9]));
                mColorIndex++;
            }
        }
        for(Course course:mCourseOnTable){
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
    }
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
        RemoteViews remoteViews=new RemoteViews(mContext.getPackageName(),R.layout.widget_list);
        remoteViews.removeAllViews(R.id.numWidgetContainer);
        for(int i=0;i<8;i++){
            RemoteViews temp;
            if(i==0){
                for(int j=1;j<13;j++){
                    temp=new RemoteViews(mContext.getPackageName(),R.layout.widget_item_1);
                    temp.setTextViewText(R.id.widget_text_1,""+j);
                    remoteViews.addView(R.id.numWidgetContainer,temp);
                }
            }
            else{
                remoteViews.removeAllViews(mLineLayoutId[i-1]);
                for(Course course:mCoursesByWeekDay.get(i-1)){
                    System.out.println(course.getEndNumber()-course.getStartNumber());
                    switch (course.getEndNumber()-course.getStartNumber()){
                        case 0:temp=new RemoteViews(mContext.getPackageName(),R.layout.widget_item_1);
                            temp.setInt(R.id.widget_text_1,"setBackgroundColor",course.getColor());
                            temp.setTextViewText(R.id.widget_text_1,course.getCourseName()+"\n"+course.getClassroom());
                            remoteViews.addView(mLineLayoutId[i-1],temp);
                            break;
                        case 1:temp=new RemoteViews(mContext.getPackageName(),R.layout.widget_item_2);
                            temp.setInt(R.id.widget_text_2,"setBackgroundColor",course.getColor());
                            temp.setTextViewText(R.id.widget_text_2,course.getCourseName()+"\n"+course.getClassroom());
                            remoteViews.addView(mLineLayoutId[i-1],temp);
                            break;
                        case 2:temp=new RemoteViews(mContext.getPackageName(),R.layout.widget_item_3);
                            temp.setInt(R.id.widget_text_3,"setBackgroundColor",course.getColor());
                            temp.setTextViewText(R.id.widget_text_3,course.getCourseName()+"\n"+course.getClassroom());
                            remoteViews.addView(mLineLayoutId[i-1],temp);
                            break;
                        case 3:temp=new RemoteViews(mContext.getPackageName(),R.layout.widget_item_4);
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
