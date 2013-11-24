using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Tasks
{
    public interface ITask
    {
        bool Execute(TaskExecutionContext context);
    }
}
