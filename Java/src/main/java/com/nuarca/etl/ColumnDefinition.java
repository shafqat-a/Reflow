package com.nuarca.etl;

import java.sql.JDBCType;
import java.sql.SQLType;
import java.sql.Types;

public class ColumnDefinition {

    private String _ColumnName;
    public String getColumnName() {
        return _ColumnName;
    }

    public void setColumnName(String value) {
        _ColumnName = value;
    }

    private String _Description;
    public String getDescription() {
        return _Description;
    }

    public void setDescription(String value) {
        _Description = value;
    }

    private int _Length;
    public int getLength() {
        return _Length;
    }

    public void setLength(int value) {
        _Length = value;
    }

    private int _Precision;
    public int getPrecision() {
        return _Precision;
    }

    public void setPrecision(int value) {
        _Precision = value;
    }

    private int _Scale;
    public int getScale() {
        return _Scale;
    }

    public void setScale(int value) {
        _Scale = value;
    }

    private Object _DefaultValue;
    public Object getDefaultValue() {
        return _DefaultValue;
    }

    public void setDefaultValue(Object value) {
        _DefaultValue = value;
    }

    private boolean _IsIdentity;
    public boolean getIsIdentity() {
        return _IsIdentity;
    }

    public void setIsIdentity(boolean value) {
        _IsIdentity = value;
    }

    private boolean _IsLong;
    public boolean getIsLong() {
        return _IsLong;
    }

    public void setIsLong(boolean value) {
        _IsLong = value;
    }

    private boolean _IsNullable;
    public boolean getIsNullable() {
        return _IsNullable;
    }

    public void setIsNullable(boolean value) {
        _IsNullable = value;
    }

    private boolean _IsPrimary;
    public boolean getIsPrimary() {
        return _IsPrimary;
    }

    public void setIsPrimary(boolean value) {
        _IsPrimary = value;
    }

    // TODO: Put a proper data type here based on JDBC

    private JDBCType _DataType = JDBCType.VARCHAR
    public JDBCType getDataType() {
        return _DataType;
    }

    public void setDataType(JDBCType value) {
        _DataType = value;
    }

    private boolean _IsUnique;
    public boolean getIsUnique() {
        return _IsUnique;
    }

    public void setIsUnique(boolean value) {
        _IsUnique = value;
    }
}
