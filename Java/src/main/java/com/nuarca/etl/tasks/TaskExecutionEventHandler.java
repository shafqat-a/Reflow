package com.nuarca.etl.tasks;

import java.util.List;

public interface TaskExecutionEventHandler
{
    void invoke(TaskExecutionContext context, TaskResult result) throws Exception ;

    List<TaskExecutionEventHandler> getInvocationList() throws Exception ;

}
