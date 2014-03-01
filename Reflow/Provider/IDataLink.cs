using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Reflow.Provider
{
    public interface IDataLink
    {
        bool Initialize(string connectionString);
        bool IsConnected { get; }
        bool Connect();
        bool Disconnect();
        ColumnDefinition[] GetSchema(string query);
        bool CreateTable(string tableName, ColumnDefinition[] columns, bool shouldDropExisting);
    }
}
