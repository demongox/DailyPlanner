package com.hirkanico.dailyplanner.classes;

public class DailyTask {

    public String id;
    public String taskName;
    public String taskDate;
    public String taskTime;
    public String isDone;

    public DailyTask(String id, String taskName, String taskDate, String taskTime, String isDone)
    {
        this.id = id;
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.isDone = isDone;
    }

}
