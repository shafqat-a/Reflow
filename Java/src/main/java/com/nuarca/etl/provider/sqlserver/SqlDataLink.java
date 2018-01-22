package com.nuarca.etl.provider.sqlserver;

//
// Translated by CS2J (http://www.cs2j.com): 12/6/2017 2:34:21 PM
//




import com.nuarca.etl.ColumnDefinition;
import com.nuarca.etl.helper.TableGenerator;
import com.nuarca.etl.provider.IDataLink;
import com.nuarca.etl.provider.sqlserver.SqlTypeTranslator;

import javax.tools.JavaCompiler;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlDataLink   implements IDataLink
{
    Connection _connection = null;
    public boolean initialize(String connectionString) throws Exception {

        Driver driver = (Driver) Class.forName("com.microsoft.sqlsserver.jdbc.SQLServerDriver").newInstance();
        Connection conn = driver.connect(connectionString, new Properties());
        _connection = conn;
        return true;
    }

    public boolean getIsConnected() throws Exception {
        if (_connection != null)
        {
            return !_connection.isClosed();
        }

        return false;
    }

    public boolean connect() throws Exception {
        return true;
    }

    public boolean disconnect() throws Exception {
        _connection.close();
        return false;
    }

    public Connection getConnection() throws Exception {
        return _connection;
    }

    public ColumnDefinition[] getSchema(String query) throws Exception {
        List<ColumnDefinition> items = new ArrayList<ColumnDefinition>();


        Statement cmd = _connection.prepareCall( query );
        ResultSet reader = cmd.executeQuery(query);
        ResultSetMetaData meta = reader.getMetaData();

        int colCount = meta.getColumnCount();

        for( int i = 0; i < colCount; i++){
            ColumnDefinition col = new ColumnDefinition();
            col.setColumnName(meta.getColumnName(i));
            col.setIsNullable(meta.isNullable(i)==1);
            col.setLength( meta.getPrecision(i));
            items.add(col);

        }
        return ((ColumnDefinition[]) items.toArray());
    }

    public boolean createTable(String tableName, ColumnDefinition[] columns, boolean shouldDropExisting) throws Exception {
        /*
        TableGenerator tgen = new TableGenerator(new SqlTypeTranslator());
        String script = tgen.GenerateTableScript(tableName, columns);
        SqlCommand cmd = _connection.CreateCommand();
        cmd.CommandType = CommandType.Text;
        if (shouldDropExisting)
        {
            cmd.CommandText = "DROP TABLE " + tableName;
            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (SqlException ex)
            {
            }

        }

        cmd.CommandText = script;
        cmd.ExecuteNonQuery();
        */
        return true;
    }

}


