using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow
{
    public class ExecutionEventListener
    {
        public delegate void ExecutionEventHandler ( string taskname, string eventName, string description);
        public ExecutionEventHandler OnTaskExecutionEvent {get;set;}
        public enum LogLevel
        {
            None=0,
            Error = 1,
            Warning =2,
            Info = 3,
            Verbose =4,
            Debug = 5
        }
        
        public LogLevel LoggingLevel {get;set;}
        public void Log (  LogLevel level, string taskname, string eventName, string description)
        {
            if (level <= this.LoggingLevel)
            {
                if (this.OnTaskExecutionEvent != null)
                {
                    this.OnTaskExecutionEvent(taskname, eventName, description);
                }
            }
        }
    }
}
