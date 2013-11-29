using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow
{
    public interface ILinkProvider
    {
        IDataLink CreateLink(string connectionString);
        ILinkReader CreateReader(IDataLink link, string query);
        ILinkWriter CreateWriter(IDataLink link, string table);
    }
}
