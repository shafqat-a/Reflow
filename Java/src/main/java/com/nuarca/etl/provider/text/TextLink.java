package com.nuarca.etl.provider.text;


import CS2JNet.System.StringSupport;
import com.nuarca.etl.ColumnDefinition;
import com.nuarca.etl.provider.IDataLink;

import java.sql.JDBCType;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        //throw new Exception("Not implemented");
        TextReader reader = new TextReader();
        reader.initialize(this);
        reader.open();
        reader.getReader().next();
        ResultSetMetaData meta = reader.getReader().getMetaData();
        List<ColumnDefinition> items = new ArrayList<ColumnDefinition>();
        int colCount = meta.getColumnCount();
        for (int i=0; i<colCount;i++)
        {
            ColumnDefinition col = new ColumnDefinition();
            col.setColumnName(meta.getColumnName(i));
            col.setLength(meta.getPrecision(i));
            col.setDataType(JDBCType.VARCHAR);
            col.setIsLong(false);
            col.setIsNullable(true);
            items.add(col);
        }
        reader.getReader().close();
        return ((ColumnDefinition[]) items.toArray());
    }



    public boolean createTable(String tableName, ColumnDefinition[] columns, boolean shouldDropExisting) throws Exception
    {
        throw new Exception("Not Implemented");
    }

}

