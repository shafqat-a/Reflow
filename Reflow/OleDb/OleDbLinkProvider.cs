using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.OleDb
{
    public class OleDbLinkProvider : ILinkProvider
    {
        public IDataLink CreateLink(string connectionString)
        {
            OleDbDataLink link = new OleDbDataLink();
            link.Initialize(connectionString);
            return link;
        }

        public ILinkReader CreateReader(IDataLink link, string query)
        {
            OleDbDataReader reader = new OleDbDataReader();
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
