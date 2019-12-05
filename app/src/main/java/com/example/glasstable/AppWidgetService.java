package com.example.glasstable;

import android.content.Intent;
import android.widget.RemoteViewsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;

public class AppWidgetService extends RemoteViewsService {

    private ArrayList<Course> courseList;
    private ArrayList<ArrayList<Course>> courseArray;
    private String FileName="coursedata";

    @Override
    public void onCreate() {
        super.onCreate();
        File file=new File(getFilesDir(),FileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois=new ObjectInputStream(fis);
            courseList=(ArrayList<Course>)ois.readObject();
        }catch (FileNotFoundException fnfe){
            System.out.println(fnfe);
        }catch (IOException ioe){
            System.out.println(ioe);
        }catch(ClassNotFoundException cnfe) {
            System.out.println(cnfe);
        }
        if(courseList.size()!=0)
            courseListToArray(courseList);
    }

    private void courseListToArray(ArrayList<Course> mcourseList){
        class Cmp implements Comparator<Course> {
            @Override
            public int compare(Course o1, Course o2) {
                return (o1.getStartNumber()+o1.getWeekDay()*12)-(o2.getStartNumber()+o2.getWeekDay()*12);
            }
        }
        Cmp cmp=new Cmp();
        ArrayList<ArrayList<Course>> tempCourseArray=new ArrayList<>();
        for(Course course:courseList){
            course.initWeek();
        }
        for(int i=0;i<20;i++){
            ArrayList<Course> coursesOnWeek=new ArrayList<>();
            for(Course course:courseList) {
                if(course.getWeeks().contains(i+1)){
                    coursesOnWeek.add(course);
                }
            }
            coursesOnWeek.sort(cmp);
            tempCourseArray.add(coursesOnWeek);
        }
        courseArray=tempCourseArray;
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CourseAppWidgetFactory(getApplicationContext(),courseList);
    }
}

