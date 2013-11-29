using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow
{
    public class ColumnMap
    {
        public ColumnDefinition SourceColumn { get; set; }
        public ColumnDefinition Destination { get; set; }
        public IExpression TransformExpression { get; set; }
    }
}
