package com.example.glasstable;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private String courseName;
    private String teacherName;
    private String classroom;
    private int startWeek;
    private int endWeek;
    private int isOddWeek;
    private int startNumber;
    private int endNumber;
    private int weekDay;
    private ArrayList<Integer> weeks=null;

    public Course(String courseName, String teacherName, String classroom, int startWeek, int endWeek, int isOddWeek, int startNumber,int endNumber, int weekDay) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.classroom = classroom;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.isOddWeek = isOddWeek;
        this.startNumber=startNumber;
        this.endNumber=endNumber;
        this.weekDay=weekDay;
    }

    public void addWeek(int i){
        if(weeks==null)
            weeks = new ArrayList<>();
        weeks.add(i);
    }

    public Course(){
        this(" "," "," ",-1,-1,-1,-1,-1,-1);
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public int getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(int endNumber) {
        this.endNumber = endNumber;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public int getIsOddWeek() {
        return isOddWeek;
    }

    public void setIsOddWeek(int isOddWeek) {
        this.isOddWeek = isOddWeek;
    }

    public ArrayList<Integer> getWeeks(){
        return weeks;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }
    @Override
    public String toString(){
        return courseName+ teacherName+classroom+"\n"+ startWeek+"--" +endWeek+"\t" +isOddWeek+"\n"+startNumber+"-"+endNumber+"   "+weekDay;
    }

}
