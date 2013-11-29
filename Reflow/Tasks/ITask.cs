using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Tasks
{
    public delegate void TaskExecutionEventHandler(TaskExecutionContext context, TaskResult result);
    public interface ITask
    {
        string Name { get; set; }
        TaskResult Execute(TaskExecutionContext context);
        
        TaskExecutionEventHandler OnBeforeExecution { get; set; }
        TaskExecutionEventHandler OnAfterExecution  { get; set; }
    }
}
