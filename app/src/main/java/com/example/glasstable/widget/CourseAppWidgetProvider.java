package com.example.glasstable.widget;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.glasstable.R;


public class CourseAppWidgetProvider extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){

        Intent intent=new Intent(context, AppWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        ComponentName componentName=new ComponentName(context,CourseAppWidgetProvider.class);
        RemoteViews mRemoteViews=new RemoteViews(context.getPackageName(), R.layout.course_widget_view);
        mRemoteViews.setRemoteAdapter(R.id.widget_list,intent);

        appWidgetManager.updateAppWidget(appWidgetId,mRemoteViews);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId:appWidgetIds)
            updateAppWidget(context,appWidgetManager,appWidgetId);
    }

    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.d("Q", "用户将widget添加桌面了");
    }



}
