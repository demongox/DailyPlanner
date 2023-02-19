package com.hirkanico.dailyplanner.library;

import android.net.Uri;
import android.provider.BaseColumns;

public class StaticConsts  implements BaseColumns {

    public static final String TABLE_NAME = "todos_list";
    public static final String COL_TODO_TEXT = "task";

    public static final String SCHEMA = "content://";
    public static final String DAILY_PLANNER_PATH = "com.hirkanico.dailyplanner.library.DailyTaskContentProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEMA + DAILY_PLANNER_PATH);
    public static final String PATH_DAILY_PLANNER = "todos";
    public static final Uri PATH_TODOS_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_DAILY_PLANNER).build();
    public static final Uri DAILY_PLANNER_URI = Uri.parse(DAILY_PLANNER_PATH);

}
