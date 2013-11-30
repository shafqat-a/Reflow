using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Transformation
{
    public class Expression
    {
        //public string Language { get; set; }
        // Hardcode now
        public string Language { get { return "VBScript"; } }
        public string Code { get; set; }
    }
}
