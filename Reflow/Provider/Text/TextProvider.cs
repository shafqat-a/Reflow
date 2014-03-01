using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reflow.Provider.Text
{
    public class TextProvider: ILinkProvider
    {
        public IDataLink CreateLink(string connectionString)
        {
            TextLink tl = new TextLink();
            tl.Initialize(connectionString);
            return tl;
        }

        public ILinkReader CreateReader(IDataLink link, string query)
        {
            // Query is irrevalent - the text file name is in the query string
            TextReader reader = new TextReader();
            reader.Initialize(link);
            return reader;
        }

        public ILinkWriter CreateWriter(IDataLink link, string table)
        {
            throw new NotImplementedException();
        }
    }
}