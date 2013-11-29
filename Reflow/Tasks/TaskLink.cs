using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;

namespace Reflow.Tasks
{
    public class TaskLink
    {
        private Dictionary<string, string> _taskPipe = new Dictionary<string, string>();
        public ITask LastTask  { get; set; }
        public ITask NextTask { get; set; }
        public Dictionary<string, string> TaskPipe { get { return _taskPipe; }}
        
        public void Bind()
        {
            LastTask.OnAfterExecution += this.OnExecute;
        }

        private void OnExecute(TaskExecutionContext context, TaskResult result)
        {
            if (result.Output != null)
            {
                Type type = NextTask.GetType();
                foreach(  string key in TaskPipe.Keys)
                {
                    PropertyInfo pi = type.GetProperty(key);
                    pi.SetValue(NextTask, result.Output[TaskPipe[key]], (object[])null);
                }
            }
        }
    }
}
