//
// Translated by CS2J (http://www.cs2j.com): 12/6/2017 2:34:21 PM
//

package com.nuarca.etl.provider.sqlserver;

import com.nuarca.etl.provider.IDataLink;
import com.nuarca.etl.provider.ILinkReader;
import com.nuarca.etl.provider.sqlserver.SqlDataLink;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlDataReader   implements ILinkReader
{
    private ResultSet _reader = null;
    private IDataLink _link = null;
    public void initialize(IDataLink link) throws Exception {
        _link = link;
    }

    private String command;
    public String getCommand() {
        return command;
    }

    public void setCommand(String value) {
        command= value;
    }

    public boolean open() throws Exception {

        SqlDataLink link = _link instanceof SqlDataLink ? (SqlDataLink)_link : (SqlDataLink)null;
        //IDbCommand cmd = link.getConnection().CreateCommand();
        //cmd.CommandText = this.getCommand();
        //cmd.CommandType = CommandType.Text;
        //CallableStatement stmt = link.getConnection().prepareCall (this.getCommand());
        Statement stm= link.getConnection().createStatement();
        _reader =  stm.executeQuery(this.getCommand());
        return true;
    }

    public boolean open(String command) throws Exception {
        this.setCommand(command);
        return this.open();
    }

    public ResultSet getReader() throws Exception {
        return _reader;
    }

}


