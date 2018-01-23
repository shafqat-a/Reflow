//
// Translated by CS2J (http://www.cs2j.com): 12/6/2017 2:34:21 PM
//

package com.nuarca.etl.provider.sqlserver;

import CS2JNet.System.NotImplementedException;
import com.nuarca.etl.provider.*;

public class SqlLinkProvider   implements ILinkProvider
{
    public IDataLink createLink(String connectionString) throws Exception {
        SqlDataLink link = new SqlDataLink();
        link.initialize(connectionString);
        return link;
    }

    public ILinkReader createReader(IDataLink link, String query) throws Exception {
        throw new NotImplementedException();
    }

    public ILinkWriter createWriter(IDataLink link, String table) throws Exception {
        SqlDataWriter writer = new SqlDataWriter();
        writer.initialize(link);
        return writer;
    }

}


