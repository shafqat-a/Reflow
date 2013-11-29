using Reflow.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.OleDb
{
    internal class OleDbTypeTranslator : ColumnTypeTranslatorBase
    {
        public override char OpeningIdentifier
        {
            get { return '['; }
        }

        public override char ClosingIdentifier
        {
            get { return ']'; }
        }

        public override char ParameterIdentifier
        {
            get { return '@'; }
        }

        public override char ParameterPlaceholderIdentifier
        {
            get { return '?'; }
        }
    }
}
