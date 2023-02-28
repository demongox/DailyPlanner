package com.hirkanico.dailyplanner.classes;

public class DailyTask {

    public String id;
    public String taskName;
    public String taskDate;
    public String taskTime;
    public String planDuration;
    public String planRepeat;
    public String isDone;
    public String planPriority;

    public DailyTask(String id, String taskName, String taskDate, String taskTime, String isDone, String planDuration, String planPriority)
    {
        this.id = id;
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.planDuration = planDuration;
        this.isDone = isDone;
        this.planPriority = planPriority;
    }

    public DailyTask(String id, String taskName, String taskDate, String taskTime, String isDone, String planDuration, String planRepeat, String planPriority)
    {
        this.id = id;
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.planDuration = planDuration;
        this.planRepeat = planRepeat;
        this.isDone = isDone;
        this.planPriority = planPriority;
    }

}
