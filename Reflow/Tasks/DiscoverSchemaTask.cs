using Reflow.Provider;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Reflow.Tasks
{
    public class DiscoverSchemaTask : TaskBase
    {
        public string Query { get; set; }
        public IDataLink Link { get; set; }
        public override TaskResult OnExecute(TaskExecutionContext context)
        {
            if (!this.Link.IsConnected)
            {
                this.Link.Connect();
            }
            
            ColumnDefinition[]  columns =  this.Link.GetSchema(Query);
            TaskResult result = new TaskResult()
            {
                IsSuccess = true,
                Message = "Discovered schema for " + this.Query,
                Output = new Dictionary<string,object>()
            };
            result.Output["Columns"] = columns;
            return result;
        }
    }
}
