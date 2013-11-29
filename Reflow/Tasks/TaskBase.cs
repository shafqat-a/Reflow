using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Tasks
{
    public abstract class TaskBase : ITask
    {
        public virtual string Name { get; set; }
        
        public virtual TaskResult Execute(TaskExecutionContext context)
        {
            if (this.OnBeforeExecution != null)
            {
                this.OnBeforeExecution(context, null);
            }
            TaskResult result = this.OnExecute ( context);
            if (this.OnAfterExecution != null)
            {
                this.OnAfterExecution(context, result);
            }
            context.LastTaskResult = result;
            return result;
        }

        public abstract TaskResult OnExecute(TaskExecutionContext context);


        public virtual TaskExecutionEventHandler OnBeforeExecution
        {
            get;
            set;
        }

        public virtual TaskExecutionEventHandler OnAfterExecution
        {
            get;
            set;
        }
    }
}
