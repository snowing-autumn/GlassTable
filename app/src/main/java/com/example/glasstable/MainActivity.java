package com.example.glasstable;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Comparator;


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
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.drawable.ww2);

        fbEdit=(FloatingActionButton)findViewById(R.id.fabMenu);
        fbEdit.setOnClickListener(this);

        courseArray=new ArrayList<ArrayList<Course>>();
        courseArray.add(new ArrayList<Course>());
        mViewPager=(ViewPager) findViewById(R.id.pageView);
        FragmentManager pageFM=getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(pageFM) {
            @Override
            public Fragment getItem(int position) {
                return FragmentLockedTable.newInstance(courseArray.get(position));
            }

            @Override
            public int getCount() {
                return courseArray.size();
            }
        });

        /*FragmentManager fm = getSupportFragmentManager();
        tableFragment = fm.findFragmentById(R.id.courseTable);

        if (tableFragment == null) {
            tableFragment = new FragmentLockedTable();
            fm.beginTransaction().add(R.id.courseTable, tableFragment)
                    .commit();
        }*/

        fbDocker=(RelativeLayout)findViewById(R.id.rlAddBill);
        for (int i = 0; i < llId.length;i++){
            ll[i] = (LinearLayout)findViewById(llId[i]);
        }
        for (int i = 0;i < fabId.length; i++){
            fab[i] = (FloatingActionButton)findViewById(fabId[i]);
            fab[i].setOnClickListener(this);
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
        class Cmp implements Comparator<Course>{
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
                if(course.getWeeks().contains(i)){
                    coursesOnWeek.add(course);
                }
            }
            coursesOnWeek.sort(cmp);
        }
        courseArray=tempCourseArray;
    }


    public ArrayList<Course> getCourseList(){
        return  courseList;
    }

    public void setCourseList(ArrayList<Course> courseList){
        this.courseList=courseList;
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
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivityForResult(intent,1);
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
