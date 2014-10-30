using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Reflow.WebDemo.Models
{
    [Serializable]
    public class MappingInfo
    {
        public MappingInfo() { }
        public MappingInfo(string fieldName) { this.FieldName = fieldName; }
        public string FieldName         { get; set; }
        public string TargetExpression  { get; set; }
    }
}