using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow
{
    public class ColumnMap
    {
        ColumnDefinition SourceColumn { get; set; }
        ColumnDefinition Destination { get; set; }
        IExpression TransformExpression { get; set; }
    }
}
