using Reflow.Tasks;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Reflow
{
    public interface ILinkWriter
    {
        void Write(IDataReader source, string table, TaskExecutionContext context);
        void Initialize(IDataLink link );

    }
}
