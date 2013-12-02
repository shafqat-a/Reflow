using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Provider.Odbc
{
    public class OdbcLinkProvider : ILinkProvider
    {
        public IDataLink CreateLink(string connectionString)
        {
            OdbcDataLink link = new OdbcDataLink();
            link.Initialize(connectionString);
            return link;
        }

        public ILinkReader CreateReader(IDataLink link, string query)
        {
            OdbcDataReader reader = new OdbcDataReader();
            reader.Initialize(link);
            reader.Command = query;
            return reader;
        }

        public ILinkWriter CreateWriter(IDataLink link, string table)
        {
            throw new NotImplementedException();
        }
    }
}
