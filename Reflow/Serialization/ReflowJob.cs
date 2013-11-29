using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Serialization
{
    public class ReflowJob
    {
        private List<Tasks.ITask> _tasks = new List<Tasks.ITask>();
        public List<Tasks.ITask> Tasks { get { return _tasks; } }

        
    }
}
