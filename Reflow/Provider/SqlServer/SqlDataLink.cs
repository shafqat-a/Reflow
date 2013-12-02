using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;

namespace Reflow.Provider.SqlServer
{
    public class SqlDataLink : IDataLink
    {
        SqlConnection _connection = null;
        public bool Initialize(string connectionString)
        {
            _connection = new SqlConnection(connectionString);
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
            SqlCommand cmd = _connection.CreateCommand();
            cmd.CommandText = query;
            cmd.CommandType = CommandType.Text;
            IDataReader reader = cmd.ExecuteReader(CommandBehavior.SchemaOnly);
            DataTable dt = reader.GetSchemaTable();
            // ColumnName, ColumnSize, NumericPrecision, DataType, AllowDbNull, ProviderType, 
            // IsIdentity, IsAutoIncrement, ProviderSpecificDataType, DataTypeName
            foreach (DataRow row in dt.Rows)
            {
                ColumnDefinition col = new ColumnDefinition();
                col.ColumnName = row["ColumnName"] as string;
                col.Length = (int)row["ColumnSize"];
                //string systemDbTypeName = col[""] as string;
                items.Add(col);
            }
            return items.ToArray();
        }


        public bool CreateTable(string tableName, ColumnDefinition[] columns, bool shouldDropExisting)
        {
            Helper.TableGenerator tgen = new Helper.TableGenerator(new SqlTypeTranslator());
            string script = tgen.GenerateTableScript(tableName, columns);
            SqlCommand cmd = _connection.CreateCommand();
            cmd.CommandType = CommandType.Text;
            if (shouldDropExisting)
            {
                cmd.CommandText = "DROP TABLE " + tableName;
                try
                {
                    cmd.ExecuteNonQuery();
                }
                catch (SqlException ex) { }
            }
            cmd.CommandText = script;
            
            cmd.ExecuteNonQuery();
            return true;
        }
    }
}
