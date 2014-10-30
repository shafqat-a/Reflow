using Reflow.Tasks;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;

namespace Reflow.Provider.SqlServer
{
    public class SqlDataWriter : ILinkWriter
    {

        private IDataLink _link = null;

        public void Initialize(IDataLink link)
        {
            _link = link;
        }

        public void Write(IDataReader source, string table, TaskExecutionContext context)
        {
            SqlConnection conn = ((SqlDataLink)_link).Connection as SqlConnection;
            if ( conn.State== ConnectionState.Closed)
            {
                conn.Open();
            }
            SqlBulkCopy sc = new SqlBulkCopy(conn);
            sc.DestinationTableName = table;
            sc.SqlRowsCopied += delegate(object sender, SqlRowsCopiedEventArgs e)
            {
                context.Events.Log (ExecutionEventListener.LogLevel.Verbose, 
                    context.Current.Name, "Progress",string.Format("{0} row(s) copied", e.RowsCopied));
            };
            sc.NotifyAfter = 100;
            sc.WriteToServer(source);
        }
    }
}
