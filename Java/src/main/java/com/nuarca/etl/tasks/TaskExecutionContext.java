package com.nuarca.etl.tasks;

import com.nuarca.etl.ExecutionEventListener;

import java.util.HashMap;

public class TaskExecutionContext
{
    public TaskExecutionContext() throws Exception {
        this.setExecutionStore(new HashMap<String,Object>());
    }

    private TaskResult __LastTaskResult;
    public TaskResult getLastTaskResult() {
        return __LastTaskResult;
    }

    public void setLastTaskResult(TaskResult value) {
        __LastTaskResult = value;
    }

    private HashMap<String,Object> __ExecutionStore;
    public HashMap<String,Object> getExecutionStore() {
        return __ExecutionStore;
    }

    public void setExecutionStore(HashMap<String,Object> value) {
        __ExecutionStore = value;
    }

    private ExecutionEventListener __Events;
    public ExecutionEventListener getEvents() {
        return __Events;
    }

    public void setEvents(ExecutionEventListener value) {
        __Events = value;
    }

    private ITask __Current;
    public ITask getCurrent() {
        return __Current;
    }

    public void setCurrent(ITask value) {
        __Current = value;
    }

}

