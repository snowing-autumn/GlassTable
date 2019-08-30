package com.example.glasstable;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isAdd=false;
    private FloatingActionButton fbEdit;
    private AnimatorSet addfb1;
    private AnimatorSet addfb2;
    private AnimatorSet addfb3;
    private AnimatorSet addfb4;
    private int[] llId=new int[]{R.id.ll01,R.id.ll02,R.id.ll03,R.id.ll04};
    private LinearLayout[] ll = new LinearLayout[llId.length];
    private int[] fabId = new int[]{R.id.miniFab01,R.id.miniFab02,R.id.miniFab03,R.id.miniFab04};
    private FloatingActionButton[] fab = new FloatingActionButton[fabId.length];

    private RelativeLayout fbDocker;
    private ArrayList<Course> courseList;
    private ArrayList<ArrayList<Course>> courseArray;
    private Fragment tableFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.drawable.ww2);

        fbEdit=(FloatingActionButton)findViewById(R.id.fabMenu);


        fbEdit.setOnClickListener(this);/*new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivityForResult(intent,1);
            }
        });*/

        FragmentManager fm = getSupportFragmentManager();
        tableFragment = fm.findFragmentById(R.id.courseTable);

        if (tableFragment == null) {
            tableFragment = new FragmentCourse();
            fm.beginTransaction().add(R.id.courseTable, tableFragment)
                    .commit();
        }

        fbDocker=(RelativeLayout)findViewById(R.id.rlAddBill);
        for (int i = 0; i < llId.length;i++){
            ll[i] = (LinearLayout)findViewById(llId[i]);
        }
        for (int i = 0;i < fabId.length; i++){
            fab[i] = (FloatingActionButton)findViewById(fabId[i]);
        }
        addfb1=(AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.fb_animation);
        addfb2=(AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.fb_animation);
        addfb3=(AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.fb_animation);
        addfb4=(AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.fb_animation);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        courseList=(ArrayList<Course>)data.getSerializableExtra("courseList");
        courseListToArray(courseList);

    }

    private void courseListToArray(ArrayList<Course> mcourseList){
        ArrayList<ArrayList<Course>> tempCourseArray=new ArrayList<>();
        Course nullCourse=new Course();
        for(int i=0;i<20;i++) {
            ArrayList<Course> weekOfCourseArray=new ArrayList<>();
            for (int j = 0; j < 12; j++){
                weekOfCourseArray.add(nullCourse);
            }
            tempCourseArray.add(weekOfCourseArray);
        }
        for(Course course:mcourseList){
            if(course.getIsOddWeek()==0)
                for(int i=course.getStartWeek()-1;i<course.getEndWeek()&&(i+1)%2==0;i++){
                    /*for(int j=course.getStartNumber()-1;j<course.getEndNumber();j++){
                        tempCourseArray.get(i).set(j,course);
                    }*/
                }
            if(course.getIsOddWeek()==1)
                for(int i=course.getStartWeek()-1;i<course.getEndWeek()&&(i+1)%2==1;i++){
                    /*for(int j=course.getStartNumber()-1;j<course.getEndNumber();j++){
                        tempCourseArray.get(i).set(j,course);
                    }*/
                    tempCourseArray.get(i).set(course.getWeekDay()-1,course);
                }
            if(course.getIsOddWeek()==2)
                for(int i=course.getStartWeek()-1;i<course.getEndWeek();i++){
                    /*for(int j=course.getStartNumber()-1;j<course.getEndNumber();j++){
                        tempCourseArray.get(i).set(j,course);
                    }*/
                    tempCourseArray.get(i).set(course.getWeekDay()-1,course);
                }
            if(course.getIsOddWeek()==3)
                for(int i:course.getWeeks()){
                    /*for(int j=course.getStartNumber()-1;j<course.getEndNumber();j++){
                        tempCourseArray.get(i-1).set(j,course);
                    }*/
                    tempCourseArray.get(i).set(course.getWeekDay()-1,course);
                }
        }
        courseArray=tempCourseArray;
    }

    public ArrayList<ArrayList<Course>> getCourseArray(){
        return  courseArray;
    }

    protected void setCourseArray(ArrayList<ArrayList<Course>> courseArray){
        this.courseArray=courseArray;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabMenu:
                fbEdit.setImageResource(isAdd ? R.mipmap.ic_edit_white_24dp:R.mipmap.ic_edit_white_24dp);
                isAdd = !isAdd;
                fbDocker.setVisibility(isAdd ? View.VISIBLE : View.GONE);
                if (isAdd) {
                    addfb1.setTarget(ll[0]);
                    addfb1.start();
                    addfb2.setTarget(ll[1]);
                    addfb2.setStartDelay(150);
                    addfb2.start();
                    addfb3.setTarget(ll[2]);
                    addfb3.setStartDelay(200);
                    addfb3.start();
                    addfb4.setTarget(ll[3]);
                    addfb4.setStartDelay(250);
                    addfb4.start();
                }
                break;
            case R.id.miniFab01:
                hideFABMenu();
                break;
            case R.id.miniFab02:
                hideFABMenu();
                break;
            case R.id.miniFab03:
                hideFABMenu();
                break;
            case R.id.miniFab04:
                hideFABMenu();
                break;

            default:
                break;
        }
    }
    private void hideFABMenu(){
        fbDocker.setVisibility(View.GONE);
        fbEdit.setImageResource(R.mipmap.ic_edit_white_24dp);
        isAdd = false;
    }
}
