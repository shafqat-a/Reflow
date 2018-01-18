package com.nuarca.etl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExecutionEventListener {

    @FunctionalInterface
    public interface ExecutionEventHandler {
        public void OnExecutionEvent(String taskName, String eventName, String description);
    }


    public enum LogLevel
    {
        None(0), Error(1), Warning(2), Info(3), Verbose(4), Debug(5);

        private final int _value;
        private LogLevel (int value){
            this._value = value;
        }

        public int toInt (){
            return _value;
        }
    }

    private LogLevel __LoggingLevel = LogLevel.None;
    public LogLevel getLoggingLevel() {
        return __LoggingLevel;
    }
    public void setLoggingLevel(LogLevel value) {
        __LoggingLevel = value;
    }

    private ExecutionEventHandler _OnTaskExecutionEvent = null;

    public ExecutionEventHandler getOnTaskExecutionEvent() {
        return _OnTaskExecutionEvent;
    }

    public void setOnTaskExecutionEvent (ExecutionEventHandler handler){
        _OnTaskExecutionEvent = handler;
    }

    public void log(LogLevel level, String taskname, String eventName, String description) throws Exception {
        if (level.toInt() <= this.getLoggingLevel().toInt())
        {
            if (this.getOnTaskExecutionEvent() != null)
            {
                this.getOnTaskExecutionEvent().OnExecutionEvent(taskname, eventName, description);
            }

        }

    }

}
