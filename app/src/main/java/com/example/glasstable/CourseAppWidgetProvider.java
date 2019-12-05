package com.example.glasstable;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RemoteViews;


public class CourseAppWidgetProvider extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        ComponentName componentName=new ComponentName(context,CourseAppWidgetProvider.class);
        RemoteViews mRemoteViews=new RemoteViews(context.getPackageName(),R.layout.course_widget_view);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId:appWidgetIds)
            updateAppWidget(context,appWidgetManager,appWidgetId);
    }
}
