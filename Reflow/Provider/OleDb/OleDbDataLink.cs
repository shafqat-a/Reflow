using System;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.Linq;
using System.Text;

namespace Reflow.Provider.OleDb
{
    public class OleDbDataLink : IDataLink
    {
        OleDbConnection _connection = null;
        public bool Initialize(string connectionString)
        {
            _connection = new OleDbConnection(connectionString);
            return true;
        }

        public bool IsConnected
        {
            get 
            {
                if (_connection != null)
                {
                    return _connection.State == ConnectionState.Open;
                }
                return false;
            }
        }

        public bool Connect()
        {
            _connection.Open();
            return true;
        }

        public bool Disconnect()
        {
            _connection.Close();
            return false;
        }

        public IDbConnection Connection
        {
            get
            {
                return _connection;
            }
        }

        public ColumnDefinition[] GetSchema(string query)
        {
            throw new InvalidOperationException("Generic OleDb does not support auto generation of schema");
        }


        public bool CreateTable(string tableName, ColumnDefinition[] columns, bool shouldDropExisting)
        {
            throw new InvalidOperationException("Generic OleDb does not support table creation");
        }
    }
}
