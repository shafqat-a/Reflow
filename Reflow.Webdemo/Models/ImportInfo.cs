using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Reflow.WebDemo.Models
{
    [Serializable]
    public class ImportInfo
    {
        public string DestinationTableName { get; set; }

        public string ImportFileLocation { get; set; }
        public string RowDelimiter { get; set; }
        public string ColumnDelimiter { get; set; }

        public bool FirstRowHasColumnNames { get; set; }
        public MappingInfo[] Maps { get; set; }


        public string[] SourceColumns { get; set; }

        public ColumnDefinition[] DestinationColumns { get; set; }
    }
}