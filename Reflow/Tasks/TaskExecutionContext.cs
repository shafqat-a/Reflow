using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Tasks
{
    public class TaskExecutionContext
    {
        internal TaskExecutionContext()
        {
            this.ExecutionStore = new Dictionary<string, object>();
        }

        public TaskResult                   LastTaskResult { get; set; }
        public Dictionary<string, object>   ExecutionStore { get; set; }

        public ExecutionEventListener Events { get; set; }

        public ITask Current { get; set; }
    }
}
