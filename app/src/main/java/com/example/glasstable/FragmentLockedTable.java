package com.example.glasstable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

    private ArrayList<Course> mCoursesList;
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

    //一节课的高度
    private int mCourseHeightDp;


    //一节课的宽度
    private int mCourseWidthDp;

    public static FragmentLockedTable newInstance(ArrayList<Course> courses){
        FragmentLockedTable fragmentLockedTable=new FragmentLockedTable();
        fragmentLockedTable.setCoursesList(courses);
        return fragmentLockedTable;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCoursesList=((MainActivity)getActivity()).getCourseList();
        mContext=getActivity();
        View table=inflater.inflate(R.layout.locked_table,container,false);
        //RecyclerView初始化
        mNumberRecyclerView =(RecyclerView) table.findViewById(R.id.lockedCowView);
        mNumberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TimeAdapter timeAdapter=new TimeAdapter(courseTime.getInstance(),getActivity());
        mNumberRecyclerView.setAdapter(timeAdapter);
        mCourseRecyclerView=(RecyclerView)table.findViewById(R.id.unlockedRecyclerView);
        mCourseRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(7,StaggeredGridLayoutManager.VERTICAL));
        CourseAdapter courseAdapter=new CourseAdapter(mCoursesList,mContext);
        mCourseRecyclerView.setAdapter(courseAdapter);
        //ScrollView初始化
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
        //左上角方格初始化
        mLiftTopTextView=(TextView) table.findViewById(R.id.textCorner);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DisplayUtil.dip2px(mContext,16)
                ,DisplayUtil.dip2px(mContext,16));
        mLiftTopTextView.setLayoutParams(layoutParams);
        //周&&日期初始化
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
                LinearLayout.LayoutParams.WRAP_CONTENT);
mWeekLineLayout.setLayoutParams(layoutParams);
        mWeekLineLayout.setOrientation(LinearLayout.HORIZONTAL);
        //绘制单元格
        for(int i=0;i<7;i++){
            View mWeekCardView= LayoutInflater.from(getActivity()).inflate(R.layout.week_view,null);
            TextView mWeekTextView=(TextView)mWeekCardView.findViewById(R.id.weekDayText);
            TextView mDayTextView=(TextView)mWeekCardView.findViewById(R.id.dayText);
            mWeekTextView.setText(mWeekList.get(i));
            mWeekTextView.setLayoutParams(layoutParams);
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


    public void setCourseHeightDp(int courseHeightDp) {
        mCourseHeightDp = courseHeightDp;
    }

    public void setCourseWidthDp(int courseWidthDp) {
        mCourseWidthDp = courseWidthDp;
    }

    private void setCoursesList(ArrayList<Course> coursesList) {
        mCoursesList = coursesList;
    }

}

/**/