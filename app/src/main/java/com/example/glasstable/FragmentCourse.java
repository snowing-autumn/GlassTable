package com.example.glasstable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.rmondjone.locktableview.LockTableView;
import com.rmondjone.xrecyclerview.ProgressStyle;
import com.rmondjone.xrecyclerview.XRecyclerView;

import java.util.ArrayList;


public class FragmentCourse extends Fragment {
    private ArrayList<ArrayList<Course>> coursesArray;
    private LockTableView mLockTableView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View table=inflater.inflate(R.layout.course_fragment,container,false);
        //coursesList=((MainActivity)getActivity()).getCourseList();
        ArrayList<ArrayList<String>> tb=tableConstruct();
        mLockTableView = new LockTableView(this.getContext(), container,tb );
        Log.e("表格加载开始", "当前线程：" + Thread.currentThread());
        mLockTableView.setLockFristColumn(true) //是否锁定第一列
                .setLockFristRow(true) //是否锁定第一行
                .setMaxColumnWidth(100) //列最大宽度
                .setMinColumnWidth(60) //列最小宽度
                .setColumnWidth(1,60) //设置指定列文本宽度(从0开始计算,宽度单位dp)
                .setMinRowHeight(20)//行最小高度
                .setMaxRowHeight(60)//行最大高度
                .setTextViewSize(16) //单元格字体大小
                .setCellPadding(15)//设置单元格内边距(dp)
                .setFristRowBackGroudColor(R.color.table_head)//表头背景色
                .setTableHeadTextColor(R.color.beijin)//表头字体颜色
                .setTableContentTextColor(R.color.border_color)//单元格字体颜色
                .setNullableString("N/A") //空值替换值
                .setTableViewListener(new LockTableView.OnTableViewListener() {
                    //设置横向滚动监听
                    @Override
                    public void onTableViewScrollChange(int x, int y) {
                        Log.e("滚动值","["+x+"]"+"["+y+"]");
                    }
                }).setTableViewRangeListener(new LockTableView.OnTableViewRangeListener() {
            //设置横向滚动边界监听
            @Override
            public void onLeft(HorizontalScrollView view) {
                Log.e("滚动边界","滚动到最左边");
            }

            @Override
            public void onRight(HorizontalScrollView view) {
                Log.e("滚动边界","滚动到最右边");
            }
        }).setOnLoadingListener(new LockTableView.OnLoadingListener() {
            //下拉刷新、上拉加载监听
            @Override
            public void onRefresh(final XRecyclerView mXRecyclerView, final ArrayList<ArrayList<String>> mTableDatas) {
                ArrayList<ArrayList<String>> tb=tableConstruct();
                //如需更新表格数据调用,部分刷新不会全部重绘
                mLockTableView.setTableDatas(tb);
                //停止刷新
                mXRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore(final XRecyclerView mXRecyclerView, final ArrayList<ArrayList<String>> mTableDatas) {
                ArrayList<ArrayList<String>> tb=tableConstruct();
                //如需更新表格数据调用,部分刷新不会全部重绘
                mLockTableView.setTableDatas(tb);
                //停止刷新
                mXRecyclerView.loadMoreComplete();
                //如果没有更多数据调用
                mXRecyclerView.setNoMore(true);
            }
        }).setOnItemClickListenter(new LockTableView.OnItemClickListenter() {
            @Override
            public void onItemClick(View item, int position) {
                Log.e("点击事件",position+"");
            }
        }).setOnItemLongClickListenter(new LockTableView.OnItemLongClickListenter() {
            @Override
            public void onItemLongClick(View item, int position) {
                Log.e("长按事件",position+"");
            }
        }).setOnItemSeletor(R.color.dashline_color)//设置Item被选中颜色
                .show(); //显示表格,此方法必须调用
        mLockTableView.getTableScrollView().setPullRefreshEnabled(true);
        mLockTableView.getTableScrollView().setLoadingMoreEnabled(true);
        mLockTableView.getTableScrollView().setRefreshProgressStyle(ProgressStyle.SquareSpin);
//属性值获取
        Log.e("每列最大宽度(dp)", mLockTableView.getColumnMaxWidths().toString());
        Log.e("每行最大高度(dp)", mLockTableView.getRowMaxHeights().toString());
        Log.e("表格所有的滚动视图", mLockTableView.getScrollViews().toString());
        Log.e("表格头部固定视图(锁列)", mLockTableView.getLockHeadView().toString());
        Log.e("表格头部固定视图(不锁列)", mLockTableView.getUnLockHeadView().toString());

        return table;
    }


    public  ArrayList<ArrayList<String>> tableConstruct(){
        ArrayList<ArrayList<String>> table=new ArrayList<>();
        ArrayList<String> date=new ArrayList<>();
        coursesArray=((MainActivity)getActivity()).getCourseArray();
        date.add("星期一");
        date.add("星期二");
        date.add("星期三");
        date.add("星期四");
        date.add("星期五");
        date.add("星期六");
        date.add("星期日");
        table.add(date);
        if(coursesArray==null) {
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
                        temp.add(coursesArray.get(i).get(j-1).getCourseName());
                }
                table.add(temp);
            }
        }
        return table;
    }

    public void notifyChanged(){

    }

/*
    private RecyclerView mRecyclerView;
    private ArrayList<courseTime> mCourseTimes=courseTime.getInstance();

    public static enum ITEM_TYPE {
        ITEM_TYPE_WEEK,
        ITEM_TYPE_NUM,
        ITEM_TYPE_COURSE
    }

    private class CourseHolder extends  RecyclerView.ViewHolder{
        private Course mCourse;
        private TextView mCourseName;
        private TextView mCourseLocation;
        public CourseHolder(View view){
            super(view);
            mCourseName=(TextView)view.findViewById(R.id.courseName);
            mCourseLocation=(TextView)view.findViewById(R.id.courseLocation);
        }
        public void bind(Course course){
            mCourse=course;
            mCourseName.setText(mCourse.getCourseName());
            mCourseLocation.setText(mCourse.getClassroom());
        }
    }

    private class WeekHolder extends  RecyclerView.ViewHolder{
        private TextView mWeekDayText;
        private TextView mDayText;
        public WeekHolder(View view){
            super(view);
            mWeekDayText=(TextView)view.findViewById(R.id.weekDayText);
            mDayText=(TextView)view.findViewById(R.id.dayText);
        }
    }

    private class NumberHolder extends RecyclerView.ViewHolder{

        private TextView mStartTimeText;
        private TextView mNumberText;
        public NumberHolder(View view){
            super(view);
            mStartTimeText=(TextView)view.findViewById(R.id.startTimeText);
            mNumberText=(TextView)view.findViewById(R.id.numberText);
        }
    }

    private class  tableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private List<Course> mCourses;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater= LayoutInflater.from(getActivity());
            if(ITEM_TYPE.ITEM_TYPE_WEEK.ordinal()==i)
                return new WeekHolder(inflater.inflate(R.layout.week_view,viewGroup,false));
            else if(ITEM_TYPE.ITEM_TYPE_NUM.ordinal()==i)
                return new NumberHolder(inflater.inflate(R.layout.number_view,viewGroup,false));
            else if(ITEM_TYPE.ITEM_TYPE_COURSE.ordinal()==i)
                return new CourseHolder(inflater.inflate(R.layout.single_course_view,viewGroup,false));
            else
                return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return mCourses.size();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View table=inflater.inflate(R.layout.course_fragment,container,false);
        mRecyclerView =(RecyclerView) table.findViewById(R.id.tableRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),8));
        return table;
    }
    */

}
