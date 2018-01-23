package com.nuarca.etl.provider.text;


import CS2JNet.System.StringSupport;
import com.nuarca.etl.ColumnDefinition;
import com.nuarca.etl.provider.IDataLink;

import java.util.HashMap;

public class TextLink   implements IDataLink
{
    HashMap<String,String> _connectionProperties = new HashMap<String,String>();
    public boolean initialize(String connectionString)  // throws Exception
    {
        String[] props = connectionString.split(StringSupport.charAltsToRegex(";".toCharArray()));
        for (String prop : props)
        {
            String[] vals = prop.split(StringSupport.charAltsToRegex("=".toCharArray()));
            _connectionProperties.put(vals[0], vals[1]);
        }
        return true;
    }

    public HashMap<String,String> getConnectionProperties() throws Exception {
        return new HashMap<String,String>(_connectionProperties);
    }

    public boolean getIsConnected()   throws Exception
    {
        return true;
    }

    public boolean connect()  throws Exception
    {
        return true;
    }

    public boolean disconnect()   throws Exception
    {
        return true;
    }


    public ColumnDefinition[] getSchema(String query) throws Exception {
//        TextReader reader = new TextReader();
//        reader.initialize(this);
//        reader.open();
//        reader.getReader().Read();
//        DataTable tbl = reader.getReader().GetSchemaTable();
//        CSList<ColumnDefinition> items = new CSList<ColumnDefinition>();
//        for (Object __dummyForeachVar1 : tbl.Rows)
//        {
//            DataRow row = (DataRow)__dummyForeachVar1;
//            ColumnDefinition col = new ColumnDefinition();
//            col.setColumnName(row["ColumnName"] instanceof String ? (String)row["ColumnName"] : (String)null);
//            col.setLength((int)row["ColumnSize"]);
//            col.setDataType(DbType.String);
//            col.setIsLong(false);
//            col.setIsNullable(true);
//            items.add(col);
//        }
//        reader.getReader().Close();
//        return ((ColumnDefinition[]) items.toArray());
        return null;
    }



    public boolean createTable(String tableName, ColumnDefinition[] columns, boolean shouldDropExisting) throws Exception
    {
        throw new Exception("Not Implemented");
    }

}

