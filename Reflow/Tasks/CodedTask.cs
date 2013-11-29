using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Tasks
{
    public class CodedTask : TaskBase
    {
        public delegate TaskResult TaskExecutionHandler(TaskExecutionContext context);

        public TaskExecutionHandler OnCodedExecute { get; set; }

        public override TaskResult OnExecute(TaskExecutionContext context)
        {
            if (this.OnCodedExecute != null)
            {
                return this.OnCodedExecute(context);
            }
            return new TaskResult() { IsSuccess = true, Message = "Blank coded task executed", Output = null };
        }
    }
}
