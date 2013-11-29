using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;

namespace Reflow.Tasks
{
    public class DataFlowTask : TaskBase
    {
        public ILinkReader Input { get; set; }
        public ILinkWriter Output { get; set; }
        public ColumnMappings Mapping { get; set; }
        public bool IsAutoMap { get; set; }
        public string TableName { get; set; }

        public override TaskResult OnExecute(TaskExecutionContext context)
        {
            Input.Open();
            Output.Write(Input.Reader, TableName, context);
            return null;
        }
    }
}
