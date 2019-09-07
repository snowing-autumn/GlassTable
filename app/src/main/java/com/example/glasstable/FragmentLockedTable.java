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
    //周行
    private RecyclerView mWeekRecyclerView;
    //课程格
    private RecyclerView mCourseRecyclerView;
    //左上角文本区
    private TextView mLiftTopTextView;
    //上下滑动区域
    private ArrayList<HorizontalScrollView> mScrollViews;
    //第一列布局(时间)
    private LinearLayout mNumLineLayout;
    //第一列滑动视图
    private ScrollView mNumScrollView;
    //主课程滑动视图
    private ScrollView mCourseScrollView;

    //左侧课程时间
    private ArrayList<courseTime> mCourseTimes=courseTime.getInstance();

    //一节课的高度
    private int mCourseHeightDp;


    //一节课的宽度
    private int mCourseWidthDp;

    public static FragmentLockedTable newInstance(ArrayList<Course> courses){
        FragmentLockedTable fragmentLockedTable=new FragmentLockedTable();
        fragmentLockedTable.setCoursesList(courses==null?new ArrayList<Course>():courses);
        return fragmentLockedTable;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext=getActivity();
        View table=inflater.inflate(R.layout.locked_table,container,false);
        //左上角方格初始化
        mLiftTopTextView=(TextView) table.findViewById(R.id.textCorner);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        mLiftTopTextView.setLayoutParams(layoutParams);
        //RecyclerView初始化
        mWeekRecyclerView =(RecyclerView) table.findViewById(R.id.lockedRowView);
        mWeekRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (RecyclerView.SCROLL_STATE_IDLE != recyclerView.getScrollState()) {
                    mCourseRecyclerView.scrollBy(dx, dy);
                }
            }
        });
        mWeekRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        WeekAdapter weekAdapter=new WeekAdapter(getActivity());
        mWeekRecyclerView.setAdapter(weekAdapter);
        mCourseRecyclerView=(RecyclerView)table.findViewById(R.id.unlockedRecyclerView);
        mCourseRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (RecyclerView.SCROLL_STATE_IDLE != recyclerView.getScrollState()) {
                    mWeekRecyclerView.scrollBy(dx, dy);
                }
            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),12,GridLayoutManager.HORIZONTAL,false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mCourseRecyclerView.getAdapter().getItemViewType(position);
            }
        });
        mCourseRecyclerView.setLayoutManager(gridLayoutManager);
        CourseAdapter courseAdapter=new CourseAdapter(mCoursesList,mContext);
        mCourseRecyclerView.setAdapter(courseAdapter);
        //ScrollView初始化
        mNumScrollView=(ScrollView) table.findViewById(R.id.lockedCowView);
        mCourseScrollView=(ScrollView)table.findViewById(R.id.unlockedScrollView);
        mNumScrollView.setOnScrollChangeListener(new ScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                ScrollAllScrollView(scrollX,scrollY);
            }
        });
        mCourseScrollView.setOnScrollChangeListener(new ScrollView.OnScrollChangeListener(){
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                ScrollAllScrollView(scrollX,scrollY);
            }
        });

        //时间初始化
        initCow();
        return table;
    }

    private void ScrollAllScrollView(int x,int y){
        mCourseScrollView.scrollTo(x,y);
        mNumScrollView.scrollTo(x,y);
    }

    private void initCow(){
        mNumLineLayout=new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mNumLineLayout.setOrientation(LinearLayout.VERTICAL);
        mNumLineLayout.setLayoutParams(layoutParams);
        for(int i=0;i<mCourseTimes.size();i++){
            View mNumCardView= LayoutInflater.from(getActivity()).inflate(R.layout.number_view,null);
            TextView mStartTimeTextView=(TextView)mNumCardView.findViewById(R.id.startTimeText);
            TextView mNumTextView=(TextView)mNumCardView.findViewById(R.id.numberText);
            mStartTimeTextView.setText(""+mCourseTimes.get(i).getStartHour()+":"+mCourseTimes.get(i).getStartMinute());
            mStartTimeTextView.setLayoutParams(layoutParams);
            mNumTextView.setText(""+mCourseTimes.get(i).getNumber());
            mNumLineLayout.addView(mNumCardView);
            //分割线
            if (i != mCourseTimes.size()-1) {
                View splitView = new View(mContext);
                ViewGroup.LayoutParams splitViewParmas = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        5);
                splitView.setLayoutParams(splitViewParmas);
                splitView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_gray));
                mNumLineLayout.addView(splitView);
            }
        }
        mNumScrollView.addView(mNumLineLayout);
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