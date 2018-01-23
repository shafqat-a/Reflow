//
// Translated by CS2J (http://www.cs2j.com): 12/6/2017 2:34:21 PM
//

package com.nuarca.etl.provider.sqlserver;

import CS2JNet.System.StringSupport;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.microsoft.sqlserver.jdbc.SQLServerBulkCopyOptions;
import com.nuarca.etl.ExecutionEventListener;
import com.nuarca.etl.ExecutionEventListener.LogLevel;
import com.nuarca.etl.provider.IDataLink;
import com.nuarca.etl.provider.ILinkWriter;
import com.nuarca.etl.provider.sqlserver.SqlDataLink;
import com.nuarca.etl.tasks.TaskExecutionContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;



public class SqlDataWriter   implements ILinkWriter
{
    private IDataLink _link = null;
    public void initialize(IDataLink link) throws Exception {
        _link = link;
    }

    public void write(ResultSet source, String table, TaskExecutionContext context) throws Exception {

        Connection conn = ((SqlDataLink)_link).getConnection(); // instanceof Connection ? (Connection)((SqlDataLink)_link).getConnection() : null;

        SQLServerBulkCopy sc = new SQLServerBulkCopy(conn);
        sc.setDestinationTableName( table);
        // FIX: Delegate
        //sc.SqlRowsCopied += ;
        SQLServerBulkCopyOptions options = new SQLServerBulkCopyOptions();
        options.setBatchSize(100);
        sc.setBulkCopyOptions(options);
        sc.writeToServer(source);
    }

}


