using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Tasks
{
    public class TaskResult
    {
        public bool                         IsSuccess           { get; set; }
        public string                       Message             { get; set; }
        public Dictionary<string, object>   Output              { get; set; }
    }
}
