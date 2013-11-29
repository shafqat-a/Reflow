using Reflow.Tasks;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow
{
    public class ReflowEngine
    {
        private List<Tasks.ITask> _tasks = new List<Tasks.ITask>();
        public List<Tasks.ITask> Tasks { get { return _tasks; } }

        public ExecutionEventListener TaskMonitor { get; set; }

        public void Execute(ExecutionEventListener eventListener)
        {
            TaskExecutionContext context = new TaskExecutionContext();
            context.LastTaskResult = null;
            context.Events = eventListener;

            eventListener.Log(ExecutionEventListener.LogLevel.Info, "Main", "Start", "Reflow engine started execution");
            eventListener.Log(ExecutionEventListener.LogLevel.Verbose, "Main", "Start", 
                string.Format("There are {0} task(s) to be executed.", this.Tasks.Count));

            eventListener.Log(ExecutionEventListener.LogLevel.Debug, "Main", "Start", "Starting tasks ...");
            // Lots of TODO: here
            foreach (ITask task in this.Tasks)
            {
                eventListener.Log(ExecutionEventListener.LogLevel.Info, task.Name, "Begin", string.Format("Starting task {0}... ", task.Name));
                context.Current = task;
                TaskResult result = task.Execute(context);
                eventListener.Log(ExecutionEventListener.LogLevel.Info, task.Name, "End", string.Format("Task {0} Complete. ", task.Name));
                context.LastTaskResult = result;
            }
            context.Current = null;
            eventListener.Log(ExecutionEventListener.LogLevel.Info, "Main", "End", "Reflow engine execution complete");
        }
    }
}
