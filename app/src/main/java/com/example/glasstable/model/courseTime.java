package com.example.glasstable.model;

import java.util.ArrayList;

public class courseTime {
    int number;
    int startHour;
    int startMinute;
    int endHour;
    int endMinute;

    private courseTime(int number, int startHour, int startMinute, int endHour, int endMinute) {
        this.number=number;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public static ArrayList<courseTime> getInstance(){
        ArrayList<courseTime> TimebarData=new ArrayList<>();
        for(int i=0;i<4;i++)
            TimebarData.add(new courseTime(i+1,i+8,i%2==0?30:25,i+9,i%2==0?15:10));
        for(int i=4;i<8;i++)
            TimebarData.add(new courseTime(i+1,i+10,i%2==0?30:25,i+11,i%2==0?15:10));
        TimebarData.add(new courseTime(9,19,0,19,45));
        TimebarData.add(new courseTime(10,19,55,20,40));
        TimebarData.add(new courseTime(11,20,50,21,35));
        TimebarData.add(new courseTime(12,21,45,22,30));
        return TimebarData;
    }

    public int getNumber() {
        return number;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }
}
