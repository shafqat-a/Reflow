using System;
using System.Collections.Generic;
using System.Data;
using System.Text;

namespace Reflow.Provider.Text
{
    public class TextLink : IDataLink
    {
        Dictionary<string, string> _connectionProperties = new Dictionary<string, string>();
        public bool Initialize(string connectionString)
        {
            string[] props = connectionString.Split(";".ToCharArray());
            foreach ( string prop in props)
            {
                string[] vals = prop.Split("=".ToCharArray());
                _connectionProperties.Add(vals[0], vals[1]);
            }
            return true;
        }

        public Dictionary<string, string> ConnectionProperties
        {
            get
            {
                return new Dictionary<string, string>(_connectionProperties);
            }
        }

        public bool IsConnected
        {
            get { return true; }
        }

        public bool Connect()
        {
            return true;
        }

        public bool Disconnect()
        {
            return true;
        }

        public ColumnDefinition[] GetSchema(string query)
        {
            TextReader reader = new TextReader();
            reader.Initialize(this);
            reader.Open();
            reader.Reader.Read();
            DataTable tbl = reader.Reader.GetSchemaTable();
            List<ColumnDefinition> items = new List<ColumnDefinition>();

            foreach (DataRow row in tbl.Rows)
            {
                ColumnDefinition col = new ColumnDefinition();
                col.ColumnName = row["ColumnName"] as string;
                col.Length = (int)row["ColumnSize"];
                col.DataType = DbType.String;
                col.IsLong = false;
                col.IsNullable = true;
                items.Add(col);
            }
            reader.Reader.Close();
            return items.ToArray();


        }

        public bool CreateTable(string tableName, ColumnDefinition[] columns, bool shouldDropExisting)
        {
            throw new NotImplementedException();
        }
    }
}
