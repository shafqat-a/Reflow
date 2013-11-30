using Reflow.Transformation;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow
{
    public class ColumnMap
    {
        //public ColumnDefinition SourceColumn { get; set; }
        //public ColumnDefinition Destination { get; set; }

        public string SourceColumn { get; set; }
        public string Destination { get; set; }
        public Expression TransformExpression { get; set; }
    }
}
