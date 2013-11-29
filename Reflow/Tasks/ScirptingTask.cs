using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Tasks
{
    public class ScirptingTask : TaskBase
    {
        public string Language  { get; set; }
        public string Code      { get; set; }

        public override TaskResult OnExecute(TaskExecutionContext context)
        {
            throw new NotImplementedException();
        }
    }
}
