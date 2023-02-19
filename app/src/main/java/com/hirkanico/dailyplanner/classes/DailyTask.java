package com.hirkanico.dailyplanner.classes;

public class DailyTask {

    public String id;
    public String taskName;
    public String taskDate;
    public String taskTime;
    public String planDuration;
    public String planRepeat;
    public String isDone;

    public DailyTask(String id, String taskName, String taskDate, String taskTime, String isDone, String planDuration)
    {
        this.id = id;
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.planDuration = planDuration;
        this.isDone = isDone;
    }

    public DailyTask(String id, String taskName, String taskDate, String taskTime, String isDone, String planDuration, String planRepeat)
    {
        this.id = id;
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.planDuration = planDuration;
        this.planRepeat = planRepeat;
        this.isDone = isDone;
    }

}
