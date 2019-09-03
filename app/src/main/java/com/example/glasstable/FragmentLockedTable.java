package com.example.glasstable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;


public class FragmentLockedTable extends Fragment {
    private Context mContext;
    private ArrayList<ArrayList<Course>> mCoursesArray;
    //时间列
    private RecyclerView mNumberRecyclerView;
    //课程格
    private RecyclerView mCourseRecyclerView;
    //左上角文本区
    private TextView mLiftTopTextView;
    //上下滑动区域
    private ArrayList<HorizontalScrollView> mScrollViews;
    //第一行布局(周)
    private LinearLayout mWeekLineLayout;
    //第一行滑动视图
    private MyHorizontalScrollView mWeekScrollView;
    //主课程滑动视图
    private MyHorizontalScrollView mCourseScrollView;

    //左侧课程时间
    private ArrayList<courseTime> mCourseTimes=courseTime.getInstance();
    //获取系统日期
    private Calendar mCalendar=Calendar.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCoursesArray=((MainActivity)getActivity()).getCourseArray();

        View table=inflater.inflate(R.layout.locked_table,container,false);
        mNumberRecyclerView =(RecyclerView) table.findViewById(R.id.lockedCowView);
        mCourseRecyclerView=(RecyclerView)table.findViewById(R.id.unlockedRecyclerView);
        mWeekScrollView=(MyHorizontalScrollView) table.findViewById(R.id.lockedRowView);
        mCourseScrollView=(MyHorizontalScrollView)table.findViewById(R.id.unlockedScrollView);
        mWeekScrollView.setMyScrollChangeListener(new MyHorizontalScrollView.OnMyScrollViewChangeListener() {
            @Override
            public void OnOnMyScrollViewChange(MyHorizontalScrollView myHorizontalScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                ScrollAllScrollView(scrollX,scrollY);
            }
        });
        mCourseScrollView.setMyScrollChangeListener(new MyHorizontalScrollView.OnMyScrollViewChangeListener() {
            @Override
            public void OnOnMyScrollViewChange(MyHorizontalScrollView myHorizontalScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                ScrollAllScrollView(scrollX,scrollY);
            }
        });
        initHeader();
        return table;
    }

    private void ScrollAllScrollView(int x,int y){
        mCourseScrollView.scrollTo(x,y);
        mWeekScrollView.scrollTo(x,y);
    }

    private void initHeader(){
        ArrayList<String> mWeekList=new ArrayList<>();
        mWeekList.add("周一");
        mWeekList.add("周二");
        mWeekList.add("周三");
        mWeekList.add("周四");
        mWeekList.add("周五");
        mWeekList.add("周六");
        mWeekList.add("周日");
        mWeekLineLayout=new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
mWeekLineLayout.setLayoutParams(layoutParams);
        mWeekLineLayout.setOrientation(LinearLayout.HORIZONTAL);
        //绘制单元格
        for(int i=0;i<7;i++){
            View mWeekCardView= LayoutInflater.from(getActivity()).inflate(R.layout.week_view,null);
            TextView mWeekTextView=(TextView)mWeekCardView.findViewById(R.id.weekDayText);
            TextView mDayTextView=(TextView)mWeekCardView.findViewById(R.id.dayText);
            mWeekTextView.setText(mWeekList.get(i));
            mDayTextView.setText(getDay(1,1));
            mWeekLineLayout.addView(mWeekCardView);
            //分割线
            if (i != 6) {
                View splitView = new View(mContext);
                ViewGroup.LayoutParams splitViewParmas = new ViewGroup.LayoutParams(1,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                splitView.setLayoutParams(splitViewParmas);
                splitView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_gray));
                mWeekLineLayout.addView(splitView);
            }
        }
        mWeekScrollView.addView(mWeekLineLayout);
    }

    private String getDay(int weekNum, int WeekDay){
        //获得第x周星期x为?月?日
        return "";
    }

    private  ArrayList<ArrayList<String>> tableConstruct(){
        ArrayList<ArrayList<String>> table=new ArrayList<>();
        ArrayList<String> date=new ArrayList<>();
        mCoursesArray=((MainActivity)getActivity()).getCourseArray();
        date.add("星期一");
        date.add("星期二");
        date.add("星期三");
        date.add("星期四");
        date.add("星期五");
        date.add("星期六");
        date.add("星期日");
        table.add(date);
        if(mCoursesArray==null) {
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
                        temp.add(mCoursesArray.get(i).get(j-1).getCourseName());
                }
                table.add(temp);
            }
        }
        return table;
    }




}

/**/