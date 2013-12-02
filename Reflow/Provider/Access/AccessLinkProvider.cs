using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Provider.Access
{
    public class AccessLinkProvider : ILinkProvider
    {
        public IDataLink CreateLink(string connectionString)
        {
            AccessDataLink link = new AccessDataLink();
            link.Initialize(connectionString);
            return link;
        }

        public ILinkReader CreateReader(IDataLink link, string query)
        {
            AccessDataReader reader = new AccessDataReader();
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
