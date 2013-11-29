using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Tasks
{
    public class TableCreateTask : TaskBase
    {
        public ColumnDefinition[] Columns { get; set; }
        public string TableName { get; set; }
        public IDataLink Link { get; set; }
        public bool ShouldDropExisting { get; set; }
        public override TaskResult OnExecute(TaskExecutionContext context)
        {
            TaskResult result = new TaskResult()
            {
                IsSuccess = false,
                Message = "Failed to create table " + TableName
            };
            if (Link != null)
            {
                if (!Link.IsConnected)
                {
                    Link.Connect();    
                }
                result.IsSuccess = Link.CreateTable( TableName, this.Columns, this.ShouldDropExisting);
                result.Message = "Successfully created table " + TableName;
                result.Output = new Dictionary<string, object>();                
            }
            return result;
        }
    }
}
