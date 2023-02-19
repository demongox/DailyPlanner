package com.hirkanico.dailyplanner.appWidget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.hirkanico.dailyplanner.MainActivity;
import com.hirkanico.dailyplanner.R;

import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetScreen extends AppWidgetProvider {

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object which defines the view of out widget
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_screen);
        // Instruct the widget manager to update the widget
        setRemoteAdapter(context, views);

        //Log.v("Number 1", "Executed");

        String number = String.format("%03d", (new Random().nextInt(900) + 100));
        views.setTextViewText(R.id.textView6, number);

        /** PendingIntent to launch the MainActivity when the widget was clicked **/
        Intent launchMain  = new Intent(context, MainActivity.class);
        PendingIntent pendingMainIntent = PendingIntent.getActivity(context, 0, launchMain, PendingIntent.FLAG_MUTABLE);
        views.setOnClickPendingIntent(R.id.widget, pendingMainIntent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_listView);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /** Set the Adapter for out widget **/

    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {

        //Log.v("Number 2", "Executed");
        views.setRemoteAdapter(R.id.widget_listView,
                new Intent(context, WidgetService.class));
    }


    /** Deprecated method, don't create this if you are not planning to support devices below 4.0 **/
    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_listView,
                new Intent(context, WidgetService.class));
    }
}