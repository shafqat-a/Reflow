package com.nuarca.etl.provider.text;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Metadata implements ResultSetMetaData{

    List<MetaRow> _cols = new ArrayList<MetaRow>();
    String _tableName = "";


    public List<MetaRow> getList (){
        return _cols;
    }

    private int indexBase = -1;

    @Override
    public int getColumnCount() throws SQLException {
        return _cols.size();
    }

    @Override
    public boolean isAutoIncrement(int i) throws SQLException {
        return _cols.get(i+indexBase).isAutoIncrement;
    }

    @Override
    public boolean isCaseSensitive(int i) throws SQLException {
        return true;
    }

    @Override
    public boolean isSearchable(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean isCurrency(int i) throws SQLException {
        return false;
    }

    @Override
    public int isNullable(int i) throws SQLException {
        return _cols.get(i+indexBase).isNullable;
    }

    @Override
    public boolean isSigned(int i) throws SQLException {
        return false;
    }

    @Override
    public int getColumnDisplaySize(int i) throws SQLException {
        return 0;
    }

    @Override
    public String getColumnLabel(int i) throws SQLException {
        return null;
    }

    @Override
    public String getColumnName(int i) throws SQLException {
        return _cols.get(i+indexBase).name;
    }

    @Override
    public String getSchemaName(int i) throws SQLException {
        return null;
    }

    @Override
    public int getPrecision(int i) throws SQLException {
        return _cols.get(i+indexBase).precision;
    }

    @Override
    public int getScale(int i) throws SQLException {
        return _cols.get(i+indexBase).scale;
    }

    @Override
    public String getTableName(int i) throws SQLException {
        return _tableName;
    }

    @Override
    public String getCatalogName(int i) throws SQLException {
        return null;
    }

    @Override
    public int getColumnType(int i) throws SQLException {
        return _cols.get(i+indexBase).type.getVendorTypeNumber();
    }

    @Override
    public String getColumnTypeName(int i) throws SQLException {
         return _cols.get(i+indexBase).type.getName();
    }

    @Override
    public boolean isReadOnly(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean isWritable(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(int i) throws SQLException {
        return false;
    }

    @Override
    public String getColumnClassName(int i) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }
}
