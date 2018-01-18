package com.nuarca.etl;

import CS2JNet.System.StringSupport;
import com.nuarca.etl.tasks.ITask;
import com.nuarca.etl.tasks.TaskExecutionContext;
import com.nuarca.etl.tasks.TaskResult;

import java.util.ArrayList;

public class ReflowEngine {

    private ArrayList<ITask> _tasks = new ArrayList<ITask>();
    public ArrayList<ITask> getTasks() throws Exception {
        return _tasks;
    }

    private ExecutionEventListener _TaskMonitor;
    public ExecutionEventListener getTaskMonitor() {
        return _TaskMonitor;
    }

    public void setTaskMonitor(ExecutionEventListener value) {
        _TaskMonitor = value;
    }

    public void execute(ExecutionEventListener eventListener) throws Exception {
        TaskExecutionContext context = new TaskExecutionContext();
        context.setLastTaskResult(null);
        context.setEvents(eventListener);
        eventListener.log(ExecutionEventListener.LogLevel.Info,"Main","Start","com.nuarca.etl engine started execution");
            eventListener.log(ExecutionEventListener.LogLevel.Verbose,"Main","Start",String.format(StringSupport.CSFmtStrToJFmtStr("There are {0} task(s) to be executed."),this.getTasks().size()));
        eventListener.log(ExecutionEventListener.LogLevel.Debug,"Main","Start","Starting tasks ...");
        for (ITask task : this.getTasks())
        {
            // Lots of TODO: here
            eventListener.log(ExecutionEventListener.LogLevel.Info,task.getName(),"Begin",String.format(StringSupport.CSFmtStrToJFmtStr("Starting task {0}... "),task.getName()));
            context.setCurrent(task);
            TaskResult result = task.execute(context);
            eventListener.log(ExecutionEventListener.LogLevel.Info,task.getName(),"End",String.format(StringSupport.CSFmtStrToJFmtStr("Task {0} Complete. "),task.getName()));
            context.setLastTaskResult(result);
        }
        context.setCurrent(null);
        eventListener.log(ExecutionEventListener.LogLevel.Info,"Main","End","com.nuarca.etl engine execution complete");
    }

}
