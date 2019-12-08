package com.example.glasstable.model;


import java.util.ArrayList;

public class ConflictedCourse {
    private ArrayList<Course> mConflictedCourses;
    private int mWeekDay;
    private int mStatTime;
    private int mEndTime;
    private String mCourseName;

    public ConflictedCourse(int WeekDay,int StartTime,int EndTime,String CourseName){
        mConflictedCourses=new ArrayList<>();
        mWeekDay=WeekDay;
        mStatTime=StartTime;
        mEndTime=EndTime;
        mCourseName=CourseName;
    }

    public boolean addCourse(Course course){
        if(mConflictedCourses.size()==0) {
            mConflictedCourses.add(course);
            return true;
        }else {
            if(course.getWeekDay()==mWeekDay&&course.getCourseName()!=mCourseName){
                if(course.getStartNumber()>=mStatTime&&course.getStartNumber()<mEndTime){
                    addCourse(course);
                    mEndTime=(mEndTime>course.getEndNumber()?mEndTime:course.getEndNumber());
                    return true;
                }else if(mStatTime>=course.getStartNumber()&&mStatTime<course.getEndNumber()){
                    addCourse(course);
                    mStatTime=(mStatTime<course.getStartNumber()?mStatTime:course.getStartNumber());
                    return true;
                }else
                    return false;
            }else
                return false;
        }
    }

    public ArrayList<Course> getConflictedCourses() {
        return mConflictedCourses;
    }

    public boolean isConflicted(){
        return mConflictedCourses.size()>1?true:false;
    }

    public int getWeekDay() {
        return mWeekDay;
    }

    public void setWeekDay(int weekDay) {
        mWeekDay = weekDay;
    }

    public int getStatTime() {
        return mStatTime;
    }

    public void setStatTime(int statTime) {
        mStatTime = statTime;
    }

    public int getEndTime() {
        return mEndTime;
    }

    public void setEndTime(int endTime) {
        mEndTime = endTime;
    }

}
