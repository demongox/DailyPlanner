package com.hirkanico.dailyplanner.appWidget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        //Log.v("Number 3", "Executed");
        return new WidgetRemoteViewsFactory(WidgetService.this,intent);
    }
}