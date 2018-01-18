package com.nuarca.etl.tasks;

public interface ITask {

    String getName() throws Exception ;

    void setName(String value) throws Exception ;

    TaskResult execute(TaskExecutionContext context) throws Exception ;

    TaskExecutionEventHandler getOnBeforeExecution() throws Exception ;

    void setOnBeforeExecution(TaskExecutionEventHandler value) throws Exception ;

    TaskExecutionEventHandler getOnAfterExecution() throws Exception ;

    void setOnAfterExecution(TaskExecutionEventHandler value) throws Exception ;

}
