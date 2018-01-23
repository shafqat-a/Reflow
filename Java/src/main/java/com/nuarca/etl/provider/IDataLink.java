package com.nuarca.etl.provider;

import com.nuarca.etl.ColumnDefinition;

public interface IDataLink {

    boolean initialize(String connectionString) throws Exception;

    boolean getIsConnected() throws Exception;

    boolean connect() throws Exception;

    boolean disconnect() throws Exception;

    ColumnDefinition[] getSchema(String query) throws Exception;

    boolean createTable(String tableName, ColumnDefinition[] columns, boolean shouldDropExisting) throws Exception;
}


