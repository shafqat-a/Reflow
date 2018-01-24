package com.nuarca.etl.provider.text;

import java.security.PublicKey;
import java.sql.JDBCType;

class MetaRow {

    public String name;
    public int scale;
    public int precision;
    public JDBCType type;
    public boolean isAutoIncrement = false;
    public int isNullable = 1;
}
