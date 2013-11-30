using Reflow.Transformation;
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

        public DataFlowTask()
        {
            this.Mapping = new ColumnMappings();
        }

        public override TaskResult OnExecute(TaskExecutionContext context)
        {
            Input.Open();
            TransformedReader reader = new TransformedReader(Input.Reader);
            reader.ScriptingLanguage = "VBScript";
            reader.TransformationScript = BuildScript();
            reader.Init();
            Output.Write(reader, TableName, context);
            return null;
        }

        private string BuildScript()
        {
            StringBuilder sb = new StringBuilder();
            foreach (ColumnMap map in this.Mapping)
            {
                if (map.SourceColumn == map.Destination)
                {
                    // skip;
                }
                else if (map.TransformExpression!=null)
                {
                    if (!string.IsNullOrEmpty(map.Destination))
                    {
                        sb.AppendLine(string.Format("Record.Item(\"{0}\") = {1}", map.Destination, map.TransformExpression.Code));
                    }
                }
            }
            return sb.ToString();
        }
    }
}
