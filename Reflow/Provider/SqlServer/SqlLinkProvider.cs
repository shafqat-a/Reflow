using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Provider.SqlServer
{
    public class SqlLinkProvider : ILinkProvider
    {
        public IDataLink CreateLink(string connectionString)
        {
            SqlDataLink link = new SqlDataLink();
            link.Initialize(connectionString);
            return link;
        }

        public ILinkReader CreateReader(IDataLink link, string query)
        {
            throw new NotImplementedException();
        }

        public ILinkWriter CreateWriter(IDataLink link, string table)
        {
            SqlDataWriter writer = new SqlDataWriter();
            writer.Initialize(link);
            return writer;
        }
    }
}
