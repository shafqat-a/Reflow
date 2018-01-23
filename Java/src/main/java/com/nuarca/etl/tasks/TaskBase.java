//
// Translated by CS2J (http://www.cs2j.com): 12/6/2017 2:34:22 PM
//

package com.nuarca.etl.tasks;

import com.nuarca.etl.tasks.ITask;
import com.nuarca.etl.tasks.TaskExecutionContext;
import com.nuarca.etl.tasks.TaskExecutionEventHandler;
import com.nuarca.etl.tasks.TaskResult;

public abstract class TaskBase   implements ITask
{
    private String __Name;
    public String getName() {
        return __Name;
    }

    public void setName(String value) {
        __Name = value;
    }

    public TaskResult execute(TaskExecutionContext context) throws Exception {
        if (this.getOnBeforeExecution() != null)
        {
            this.getOnBeforeExecution().invoke(context,null);
        }
         
        TaskResult result = this.onExecute(context);
        if (this.getOnAfterExecution() != null)
        {
            this.getOnAfterExecution().invoke(context,result);
        }
         
        context.setLastTaskResult(result);
        return result;
    }

    public abstract TaskResult onExecute(TaskExecutionContext context) throws Exception ;

    private TaskExecutionEventHandler __OnBeforeExecution;
    public TaskExecutionEventHandler getOnBeforeExecution() {
        return __OnBeforeExecution;
    }

    public void setOnBeforeExecution(TaskExecutionEventHandler value) {
        __OnBeforeExecution = value;
    }

    private TaskExecutionEventHandler __OnAfterExecution;
    public TaskExecutionEventHandler getOnAfterExecution() {
        return __OnAfterExecution;
    }

    public void setOnAfterExecution(TaskExecutionEventHandler value) {
        __OnAfterExecution = value;
    }

}


