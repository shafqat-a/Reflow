using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow
{
    public class ColumnDefinition
    {
        public enum DataType
        {
            Int,
            Int64,
            Char, 
            NChar,
            VarChar,
            NVarChar,
            Float,
            Double,
            Binary,
            Text,
            NText
        }

        public string ColumnName   { get; set; }
        public string Description { get; set; }
        public DataType Type { get; set; }
        public int Length { get; set; }
        public int Precision { get; set; }

    }
}
