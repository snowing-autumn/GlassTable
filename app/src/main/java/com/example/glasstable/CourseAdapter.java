package com.example.glasstable;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

import static android.content.ContentValues.TAG;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private ArrayList<Course> mCourses;
    private Context mContext;
    private ArrayList<Course> mCourseOnTable;
    private ArrayList<Course> mEmptyCourse;
    private ArrayList<ConflictedCourse> mConflictedCourses;
    private int mColorIndex=0;
    private int mCourseWidthPx;
    private int mCourseHeigthtPx;
    private int[] mColor={R.color.powderblue,R.color.tomato,
            R.color.mediumslateblue, R.color.royalblue,
            R.color.forestgreen, R.color.darkviolet,
            R.color.crimson, R.color.orangered,
            R.color.burlywood
    };


    public CourseAdapter(ArrayList<Course> mCourses, Context mContext,int courseHeigthtPx,int courseWidthPx){
        mConflictedCourses=new ArrayList<>();
        mCourseOnTable=new ArrayList<>();
        mEmptyCourse=new ArrayList<>();
        this.mCourses=mCourses;
        this.mContext=mContext;
        mCourseHeigthtPx=courseHeigthtPx;
        mCourseWidthPx=courseWidthPx;
        if(this.mCourses.size()==0)
            genetateTestData();
        initCourse();
        for(Course course:mCourseOnTable)
            Log.d(TAG, course.getCourseName()+course.getWeekDay()+"周   "+course.getStartNumber()+"-"
            +course.getEndNumber());
    }

    class CourseHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        CardView mCardView;
        TextView mTextView;

        public CourseHolder(View itemView){
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.courseCardView);
            mTextView=(TextView)itemView.findViewById(R.id.courseInfo);
        }

        public void bind(Course course){
            if(course.getCourseName()!="") {
                String info = course.getCourseName() + "\n" + course.getClassroom();
                mTextView.setText(info);
                mCardView.setBackgroundColor(mContext.getColor(course.getColor()));
                mCardView.setAlpha((float)0.8);
                mTextView.setTextColor(mContext.getColor(R.color.pureWhite));
            }else {
                mCardView.setAlpha((float)0);
            }
        }

        @Override
        public void onClick(View v) {
           //弹出课程信息
        }

        @Override
        public boolean onLongClick(View v) {
            //弹出课程删除/修改/添加
            return false;
        }
    }

    private void genetateTestData(){
        for(int i=0;i<7;i++){
            Course course=new Course(i+1,1,12);
            mCourses.add(course);
        }
    }

    private void findConflictedCourse(){
        int[] flag=new int[84];
        for(int i=0;i<84;i++)
            flag[i]=0;
        //课程冲突查找
        for(Course course:mCourses){
            setCourseColor(course);
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

    private void initCourse(){
        findConflictedCourse();
        int[] flag=new int[84];
        for(int i=0;i<84;i++)
            flag[i]=0;
        for(ConflictedCourse conflictedCourse:mConflictedCourses){
            int startPosition=(conflictedCourse.getWeekDay()-1)*12+conflictedCourse.getStatTime()-1;
            int endPosition=(conflictedCourse.getWeekDay()-1)*12+conflictedCourse.getEndTime()-1;
            for(int j=startPosition;j<endPosition;j++) {
                flag[j]++;
            }
        }
        mConflictedCourses.sort(new Comparator<ConflictedCourse> (){
        @Override
        public int compare(ConflictedCourse o1, ConflictedCourse o2) {
            return (o1.getStatTime()+o1.getWeekDay()*12)-(o2.getStatTime()+o2.getWeekDay()*12);
            }
        });
        //添加空白课程
        int start=0;
        int startWeekDay=1;
        for(int index=0;index<mConflictedCourses.size();index++){
            int end=mConflictedCourses.get(index).getStatTime();
            int endWeekDay=mConflictedCourses.get(index).getWeekDay();
            //同一天课间隔或顺延至下一天
            if(endWeekDay-startWeekDay==0||endWeekDay-startWeekDay==1&&start==12&&end==1) {
                //同一天课间隔
                if (end - start > 1) {
                    Course emptyCourse = new Course(startWeekDay, start+1, end-1);
                    mEmptyCourse.add(emptyCourse);
                    mCourseOnTable.add(emptyCourse);
                }
            }else {
                //下一节课来到了第二天，甚至存在N整天没课
                for(int re=0;re<endWeekDay-startWeekDay;re++) {
                    Course emptyCourse;
                    if(re==0)
                         emptyCourse= new Course(startWeekDay+re, start+1, 12);
                    else
                        emptyCourse = new Course(startWeekDay+re, 1, 12);
                    mEmptyCourse.add(emptyCourse);
                    mCourseOnTable.add(emptyCourse);
                }
                start=0;
                if(end-start>1) {
                    startWeekDay = mConflictedCourses.get(index).getWeekDay();
                    Course emptyCourse = new Course(startWeekDay, 1, end-1);
                    mEmptyCourse.add(emptyCourse);
                    mCourseOnTable.add(emptyCourse);
                }
            }
            if(!mConflictedCourses.get(index).isConflicted())
                mCourseOnTable.add(mConflictedCourses.get(index).getConflictedCourses().get(0));
            else {
                Course course=new Course(mConflictedCourses.get(index).getWeekDay(),mConflictedCourses.get(index).getStatTime(),
                mConflictedCourses.get(index).getEndTime());
                course.setCourseName("该课程冲突");
            }
            start=mConflictedCourses.get(index).getEndTime();
            startWeekDay=mConflictedCourses.get(index).getWeekDay();
        }
        if(startWeekDay!=7){
            if(start!=12){
                Course emptyCourse=new Course(startWeekDay,start+1,12);
                mEmptyCourse.add(emptyCourse);
                mCourseOnTable.add(emptyCourse);
            }
            for(int i=startWeekDay+1;i<=7;i++){
                Course emptyCourse=new Course(startWeekDay,1,12);
                mEmptyCourse.add(emptyCourse);
                mCourseOnTable.add(emptyCourse);
            }
        }

    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(mCourseWidthPx,mCourseHeigthtPx*viewType);
        View view= LayoutInflater.from(mContext).inflate(R.layout.single_course_view,parent,false);
        view.setLayoutParams(layoutParams);
        CourseHolder mCourseHolder= new CourseHolder(view);
        return  mCourseHolder;
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {
        holder.bind(mCourseOnTable.get(position));

    }

    @Override
    public int getItemCount() {
        return mCourseOnTable.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mCourseOnTable.get(position).getEndNumber()-mCourseOnTable.get(position).getStartNumber()+1;
    }

    private void setCourseColor(Course course){
        course.setColor(mColor[mColorIndex%9]);
        mColorIndex++;
    }
}
