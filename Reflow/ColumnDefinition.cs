using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow
{
    public class ColumnDefinition
    {

        public string ColumnName { get; set; }
            
        public string Description { get; set; }

        public int Length { get; set; }

        public int Precision { get; set; }

        public int Scale { get; set; }

        public object DefaultValue { get; set; }

        public bool IsIdentity { get; set; }

        public bool IsLong { get; set; }

        public bool IsNullable { get; set; }

        public bool IsPrimary { get; set; }

        public System.Data.DbType DataType { get; set; }

        public bool IsUnique { get; set; }

    }
}
