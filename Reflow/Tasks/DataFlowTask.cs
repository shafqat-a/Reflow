using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Tasks
{
    public class DataFlowTask : ITask
    {
        ILinkReader Input { get; set; }
        ILinkWriter Output { get; set; }
        ColumnMappings Mapping { get; set; }

        public bool Execute(TaskExecutionContext context)
        {
            return false;
        }
    }
}
