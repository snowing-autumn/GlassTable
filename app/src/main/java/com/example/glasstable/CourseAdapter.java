package com.example.glasstable;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private ArrayList<Course> mCourses;
    private Context mContext;
    private ArrayList<Course> mCourseOnTable;
    private ArrayList<Course> mEmptyCourse;
    private ArrayList<ConflictedCourse> mConflictedCourses;


    public CourseAdapter(ArrayList<Course> mCourses, Context mContext){
        mConflictedCourses=new ArrayList<>();
        mCourseOnTable=new ArrayList<>();
        mEmptyCourse=new ArrayList<>();
        this.mCourses=mCourses;
        this.mContext=mContext;
        genetateTestData();
        initCourse();
    }

    class CourseHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        CardView mCardView;
        TextView mTextView;

        public CourseHolder(View itemView){
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.numberCardView);
            mTextView=(TextView)itemView.findViewById(R.id.courseInfo);
        }

        public void bind(Course course){
            if(course.getCourseName()!="") {
                String info = course.getCourseName() + "\n" + course.getClassroom();
                mTextView.setText(info);
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
        for(int i=0;i<7;i++)
            for(int j=0;j<12;j++){
                Course course=new Course(i+1,j+1,j+2);
                course.setCourseName("Test"+i);
                mCourses.add(course);
            }
    }

    private void findConflictedCourse(){
        int[] flag=new int[84];
        for(int i=0;i<84;i++)
            flag[i]=0;
        //课程冲突查找
        for(Course course:mCourses){
            ConflictedCourse tempConflictedCourse=new ConflictedCourse(course.getWeekDay(),course.getStartNumber(),course.getEndNumber(),course.getCourseName());
            tempConflictedCourse.addCourse(course);
            mConflictedCourses.add(tempConflictedCourse);
            boolean conflicted=false;
            int startPosition=course.getWeekDay()*12+course.getStartNumber();
            int endPosition=course.getWeekDay()*12+course.getEndWeek();
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
            if(endWeekDay-startWeekDay==0) {
                //同一天课间隔
                if (start - end > 0) {
                    Course emptyCourse = new Course(startWeekDay, start, end);
                    mEmptyCourse.add(emptyCourse);
                    mCourseOnTable.add(emptyCourse);
                }
            }else {
                //下一节课来到了第二天，甚至存在N整天没课
                for(int re=0;re<endWeekDay-startWeekDay;re++) {
                    Course emptyCourse;
                    if(re==0)
                         emptyCourse= new Course(startWeekDay+re, start, 12);
                    else
                        emptyCourse = new Course(startWeekDay+re, 1, 12);
                    mEmptyCourse.add(emptyCourse);
                    mCourseOnTable.add(emptyCourse);
                }
                start=0;
                if(end-start>0) {
                    startWeekDay = mConflictedCourses.get(index).getWeekDay();
                    Course emptyCourse = new Course(startWeekDay, start, 12);
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

    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CourseHolder mCourseHolder= new CourseHolder(LayoutInflater.from(mContext).inflate(R.layout.single_course_view,parent,false));
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
}
