package com.hirkanico.dailyplanner.appWidget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.hirkanico.dailyplanner.MainActivity;
import com.hirkanico.dailyplanner.R;
import com.hirkanico.dailyplanner.library.DBManager;
import com.hirkanico.dailyplanner.library.DatabaseHelper;
import com.hirkanico.dailyplanner.library.StaticConsts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Cursor cursor;
    private Intent intent;



    //For obtaining the activity's context and intent
    public WidgetRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    private void initCursor(){
        if (cursor != null) {
            cursor.close();
        }

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        final long identityToken = Binder.clearCallingIdentity();
        /*
         This is done because the widget runs as a separate thread
         when compared to the current app and hence the app's data won't be accessible to it
         because I'm using a content provided *
         */
        DBManager database = new DBManager(context);
        database.open();
        cursor = database.fetchDailyPlanForWidget(today);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onCreate() {
        //Log.v("Factory","Factory");
        initCursor();
        if (cursor != null) {
            cursor.moveToFirst();
        }
    }

    @Override
    public void onDataSetChanged() {
        /** Listen for data changes and initialize the cursor again **/
        initCursor();
    }

    @Override
    public void onDestroy() {
        cursor.close();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @SuppressLint("Range")
    @Override
    public RemoteViews getViewAt(int i) {
        /*
        * Populate your widget's single list item *
        * */
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_items_layout);
        cursor.moveToPosition(i);
        remoteViews.setTextViewText(R.id.txtWidgetPlanName,"    " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_TITLE))+" ");
        remoteViews.setTextViewText(R.id.txtWidgetPlanTime,cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_TIME))+" ");
        remoteViews.setTextViewText(R.id.txtWidgetPlanDuration,cursor.getString(cursor.getColumnIndex(DatabaseHelper.NEW_PLAN_DURATION_TIME)));

        remoteViews.setInt(R.id.imgTimeIcon, "setBackgroundResource", R.drawable.time_icom);
        remoteViews.setInt(R.id.imgRemainIcon, "setBackgroundResource", R.drawable.duration_icon);

        switch (cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_PRIORITY))){
            case "low":
                remoteViews.setInt(R.id.imgPriorityIcon, "setBackgroundResource", R.drawable.low_priority);
                break;
            case "medium":
                remoteViews.setInt(R.id.imgPriorityIcon, "setBackgroundResource", R.drawable.medium_priority);
                break;
            case "high":
                remoteViews.setInt(R.id.imgPriorityIcon, "setBackgroundResource", R.drawable.high_priority);
                break;
        }

        if (cursor.getString(cursor.getColumnIndex(DatabaseHelper.PLAN_IS_DONE)).equals("true")) {
            remoteViews.setInt(R.id.imgTickIcon, "setBackgroundResource", R.drawable.tick_icon);
        } else {
            remoteViews.setInt(R.id.imgTickIcon, "setBackgroundResource", R.drawable.tick_icon_red);
        }

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}