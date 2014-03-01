using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Provider.Text
{
    public class TextWriter : ILinkWriter
    {
        public void Write(System.Data.IDataReader source, string table, Tasks.TaskExecutionContext context)
        {
            throw new NotImplementedException();
        }

        public void Initialize(IDataLink link)
        {
            throw new NotImplementedException();
        }
    }
}
