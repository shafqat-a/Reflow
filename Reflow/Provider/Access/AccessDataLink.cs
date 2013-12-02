using System;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.Linq;
using System.Text;

namespace Reflow.Provider.Access
{
    public class AccessDataLink : IDataLink
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
            List<ColumnDefinition> items = new List<ColumnDefinition>();
            OleDbCommand cmd = _connection.CreateCommand();
            cmd.CommandText = query;
            cmd.CommandType = CommandType.Text;
            IDataReader reader = cmd.ExecuteReader(CommandBehavior.SchemaOnly);
            DataTable dt = reader.GetSchemaTable();
            Provider.Access.AccessDbTypeTranslator translator = new AccessDbTypeTranslator();
            // ColumnName, ColumnSize, NumericPrecision, DataType, AllowDbNull, ProviderType, 
            // IsIdentity, IsAutoIncrement, ProviderSpecificDataType, DataTypeName
            foreach (DataRow row in dt.Rows)
            {
                ColumnDefinition col = new ColumnDefinition();
                col.ColumnName  = row["ColumnName"]  as string;
                col.Length      = (int)row["ColumnSize"];
                string dataType = (row["DataType"]   as Type).FullName;
                col.DataType = translator.GetDbTypeFromSystemTypeString(dataType);
                col.IsLong      = (bool)row["IsLong"];
                col.IsNullable = (bool)row["AllowDBNull"];
                items.Add(col);
            }
            return items.ToArray();
        }


        public bool CreateTable(string tableName, ColumnDefinition[] columns, bool shouldDropExisting)
        {
            throw new NotImplementedException();
        }
    }
}
