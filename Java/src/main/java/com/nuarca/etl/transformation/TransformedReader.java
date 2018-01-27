//
// Translated by CS2J (http://www.cs2j.com): 12/6/2017 2:34:22 PM
//

package com.nuarca.etl.transformation;

//import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.NotImplementedException;
import CS2JNet.System.StringSupport;

import java.io.Console;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;
//import java.util.Date;

import com.nuarca.etl.ColumnMappings;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
//import com.nuarca.etl.Transformation.ScriptFactory;

public class TransformedReader  implements java.sql.ResultSet
{

    ResultSet _source = null;
    public TransformedReader(ResultSet reader) throws Exception {
        _source = reader;
    }
    @Override
    public boolean next() throws SQLException {

        boolean result = _source.next();
        if (result == true)
        {
            // TODO: Implement scripting transformation here
            _rec.clear();
            StringBuilder sb = new StringBuilder();
            for (int i = 0;i < _fields.size();i++)
            {

                _rec.put(_fields.get(i), _source.getObject(i));
                //sb.AppendLine(string.Format("{0} = {1}",_fields[i], GetStringExpression(_source.GetValue(i))));
                sb.append((String.format(StringSupport.CSFmtStrToJFmtStr("{0} = {1};\n"),_fields.get(i),"Record.get(\"" + _fields.get(i) + "\")")) + System.getProperty("line.separator"));
            }
            //Console.WriteLine(sb.ToString());
            String strToEval = sb.toString() + "\r\n" + this.getTransformationScript();
            try {
                _eng.eval("aaa=1;");
                _eng.eval(strToEval);
                int a =1;
            }
            catch ( ScriptException exJ){
                throw new SQLException(exJ);
            }
        }

        return result;


    }

    @Override
    public void close() throws SQLException {
        _source.close();
    }

    @Override
    public boolean wasNull() throws SQLException {
        return _source.wasNull();
    }

    @Override
    public String getString(int i) throws SQLException {
        String columnfName = _fields.get(i-1);
        Object obj =_rec.get(columnfName);
        if ( obj!=null){
            return obj.toString();
        } else {
            return  null;
        }
    }

    @Override
    public boolean getBoolean(int i) throws SQLException {
        return false;
    }

    @Override
    public byte getByte(int i) throws SQLException {
        return 0;
    }

    @Override
    public short getShort(int i) throws SQLException {
        return 0;
    }

    @Override
    public int getInt(int i) throws SQLException {
        return 0;
    }

    @Override
    public long getLong(int i) throws SQLException {
        return 0;
    }

    @Override
    public float getFloat(int i) throws SQLException {
        return 0;
    }

    @Override
    public double getDouble(int i) throws SQLException {
        return 0;
    }

    @Override
    public BigDecimal getBigDecimal(int i, int i1) throws SQLException {
        return null;
    }

    @Override
    public byte[] getBytes(int i) throws SQLException {
        return new byte[0];
    }

    @Override
    public java.sql.Date getDate(int i) throws SQLException {
        return null;
    }

    @Override
    public Time getTime(int i) throws SQLException {
        return null;
    }

    @Override
    public Timestamp getTimestamp(int i) throws SQLException {
        return null;
    }

    @Override
    public InputStream getAsciiStream(int i) throws SQLException {
        return null;
    }

    @Override
    public InputStream getUnicodeStream(int i) throws SQLException {
        return null;
    }

    @Override
    public InputStream getBinaryStream(int i) throws SQLException {
        return null;
    }

    @Override
    public String getString(String s) throws SQLException {
        return null;
    }

    @Override
    public boolean getBoolean(String s) throws SQLException {
        return false;
    }

    @Override
    public byte getByte(String s) throws SQLException {
        return 0;
    }

    @Override
    public short getShort(String s) throws SQLException {
        return 0;
    }

    @Override
    public int getInt(String s) throws SQLException {
        return 0;
    }

    @Override
    public long getLong(String s) throws SQLException {
        return 0;
    }

    @Override
    public float getFloat(String s) throws SQLException {
        return 0;
    }

    @Override
    public double getDouble(String s) throws SQLException {
        return 0;
    }

    @Override
    public BigDecimal getBigDecimal(String s, int i) throws SQLException {
        return null;
    }

    @Override
    public byte[] getBytes(String s) throws SQLException {
        return new byte[0];
    }

    @Override
    public java.sql.Date getDate(String s) throws SQLException {
        return null;
    }

    @Override
    public Time getTime(String s) throws SQLException {
        return null;
    }

    @Override
    public Timestamp getTimestamp(String s) throws SQLException {
        return null;
    }

    @Override
    public InputStream getAsciiStream(String s) throws SQLException {
        return null;
    }

    @Override
    public InputStream getUnicodeStream(String s) throws SQLException {
        return null;
    }

    @Override
    public InputStream getBinaryStream(String s) throws SQLException {
        return null;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public String getCursorName() throws SQLException {
        return null;
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return _source.getMetaData();
    }

    @Override
    public Object getObject(int i) throws SQLException {
        return null;
    }

    @Override
    public Object getObject(String s) throws SQLException {
        return null;
    }

    @Override
    public int findColumn(String s) throws SQLException {
        return 0;
    }

    @Override
    public Reader getCharacterStream(int i) throws SQLException {
        return null;
    }

    @Override
    public Reader getCharacterStream(String s) throws SQLException {
        return null;
    }

    @Override
    public BigDecimal getBigDecimal(int i) throws SQLException {
        return null;
    }

    @Override
    public BigDecimal getBigDecimal(String s) throws SQLException {
        return null;
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        return false;
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        return false;
    }

    @Override
    public boolean isFirst() throws SQLException {
        return false;
    }

    @Override
    public boolean isLast() throws SQLException {
        return false;
    }

    @Override
    public void beforeFirst() throws SQLException {

    }

    @Override
    public void afterLast() throws SQLException {

    }

    @Override
    public boolean first() throws SQLException {
        return false;
    }

    @Override
    public boolean last() throws SQLException {
        return false;
    }

    @Override
    public int getRow() throws SQLException {
        return 0;
    }

    @Override
    public boolean absolute(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean relative(int i) throws SQLException {
        return false;
    }

    @Override
    public boolean previous() throws SQLException {
        return false;
    }

    @Override
    public void setFetchDirection(int i) throws SQLException {

    }

    @Override
    public int getFetchDirection() throws SQLException {
        return 0;
    }

    @Override
    public void setFetchSize(int i) throws SQLException {

    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getType() throws SQLException {
        return 0;
    }

    @Override
    public int getConcurrency() throws SQLException {
        return 0;
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        return false;
    }

    @Override
    public boolean rowInserted() throws SQLException {
        return false;
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        return false;
    }

    @Override
    public void updateNull(int i) throws SQLException {

    }

    @Override
    public void updateBoolean(int i, boolean b) throws SQLException {

    }

    @Override
    public void updateByte(int i, byte b) throws SQLException {

    }

    @Override
    public void updateShort(int i, short i1) throws SQLException {

    }

    @Override
    public void updateInt(int i, int i1) throws SQLException {

    }

    @Override
    public void updateLong(int i, long l) throws SQLException {

    }

    @Override
    public void updateFloat(int i, float v) throws SQLException {

    }

    @Override
    public void updateDouble(int i, double v) throws SQLException {

    }

    @Override
    public void updateBigDecimal(int i, BigDecimal bigDecimal) throws SQLException {

    }

    @Override
    public void updateString(int i, String s) throws SQLException {

    }

    @Override
    public void updateBytes(int i, byte[] bytes) throws SQLException {

    }

    @Override
    public void updateDate(int i, java.sql.Date date) throws SQLException {

    }

    @Override
    public void updateTime(int i, Time time) throws SQLException {

    }

    @Override
    public void updateTimestamp(int i, Timestamp timestamp) throws SQLException {

    }

    @Override
    public void updateAsciiStream(int i, InputStream inputStream, int i1) throws SQLException {

    }

    @Override
    public void updateBinaryStream(int i, InputStream inputStream, int i1) throws SQLException {

    }

    @Override
    public void updateCharacterStream(int i, Reader reader, int i1) throws SQLException {

    }

    @Override
    public void updateObject(int i, Object o, int i1) throws SQLException {

    }

    @Override
    public void updateObject(int i, Object o) throws SQLException {

    }

    @Override
    public void updateNull(String s) throws SQLException {

    }

    @Override
    public void updateBoolean(String s, boolean b) throws SQLException {

    }

    @Override
    public void updateByte(String s, byte b) throws SQLException {

    }

    @Override
    public void updateShort(String s, short i) throws SQLException {

    }

    @Override
    public void updateInt(String s, int i) throws SQLException {

    }

    @Override
    public void updateLong(String s, long l) throws SQLException {

    }

    @Override
    public void updateFloat(String s, float v) throws SQLException {

    }

    @Override
    public void updateDouble(String s, double v) throws SQLException {

    }

    @Override
    public void updateBigDecimal(String s, BigDecimal bigDecimal) throws SQLException {

    }

    @Override
    public void updateString(String s, String s1) throws SQLException {

    }

    @Override
    public void updateBytes(String s, byte[] bytes) throws SQLException {

    }

    @Override
    public void updateDate(String s, java.sql.Date date) throws SQLException {

    }

    @Override
    public void updateTime(String s, Time time) throws SQLException {

    }

    @Override
    public void updateTimestamp(String s, Timestamp timestamp) throws SQLException {

    }

    @Override
    public void updateAsciiStream(String s, InputStream inputStream, int i) throws SQLException {

    }

    @Override
    public void updateBinaryStream(String s, InputStream inputStream, int i) throws SQLException {

    }

    @Override
    public void updateCharacterStream(String s, Reader reader, int i) throws SQLException {

    }

    @Override
    public void updateObject(String s, Object o, int i) throws SQLException {

    }

    @Override
    public void updateObject(String s, Object o) throws SQLException {

    }

    @Override
    public void insertRow() throws SQLException {

    }

    @Override
    public void updateRow() throws SQLException {

    }

    @Override
    public void deleteRow() throws SQLException {

    }

    @Override
    public void refreshRow() throws SQLException {

    }

    @Override
    public void cancelRowUpdates() throws SQLException {

    }

    @Override
    public void moveToInsertRow() throws SQLException {

    }

    @Override
    public void moveToCurrentRow() throws SQLException {

    }

    @Override
    public Statement getStatement() throws SQLException {
        return null;
    }

    @Override
    public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
        return null;
    }

    @Override
    public Ref getRef(int i) throws SQLException {
        return null;
    }

    @Override
    public Blob getBlob(int i) throws SQLException {
        return null;
    }

    @Override
    public Clob getClob(int i) throws SQLException {
        return null;
    }

    @Override
    public Array getArray(int i) throws SQLException {
        return null;
    }

    @Override
    public Object getObject(String s, Map<String, Class<?>> map) throws SQLException {
        return null;
    }

    @Override
    public Ref getRef(String s) throws SQLException {
        return null;
    }

    @Override
    public Blob getBlob(String s) throws SQLException {
        return null;
    }

    @Override
    public Clob getClob(String s) throws SQLException {
        return null;
    }

    @Override
    public Array getArray(String s) throws SQLException {
        return null;
    }

    @Override
    public java.sql.Date getDate(int i, Calendar calendar) throws SQLException {
        return null;
    }

    @Override
    public java.sql.Date getDate(String s, Calendar calendar) throws SQLException {
        return null;
    }

    @Override
    public Time getTime(int i, Calendar calendar) throws SQLException {
        return null;
    }

    @Override
    public Time getTime(String s, Calendar calendar) throws SQLException {
        return null;
    }

    @Override
    public Timestamp getTimestamp(int i, Calendar calendar) throws SQLException {
        return null;
    }

    @Override
    public Timestamp getTimestamp(String s, Calendar calendar) throws SQLException {
        return null;
    }

    @Override
    public URL getURL(int i) throws SQLException {
        return null;
    }

    @Override
    public URL getURL(String s) throws SQLException {
        return null;
    }

    @Override
    public void updateRef(int i, Ref ref) throws SQLException {

    }

    @Override
    public void updateRef(String s, Ref ref) throws SQLException {

    }

    @Override
    public void updateBlob(int i, Blob blob) throws SQLException {

    }

    @Override
    public void updateBlob(String s, Blob blob) throws SQLException {

    }

    @Override
    public void updateClob(int i, Clob clob) throws SQLException {

    }

    @Override
    public void updateClob(String s, Clob clob) throws SQLException {

    }

    @Override
    public void updateArray(int i, Array array) throws SQLException {

    }

    @Override
    public void updateArray(String s, Array array) throws SQLException {

    }

    @Override
    public RowId getRowId(int i) throws SQLException {
        return null;
    }

    @Override
    public RowId getRowId(String s) throws SQLException {
        return null;
    }

    @Override
    public void updateRowId(int i, RowId rowId) throws SQLException {

    }

    @Override
    public void updateRowId(String s, RowId rowId) throws SQLException {

    }

    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return _source.isClosed();
    }

    @Override
    public void updateNString(int i, String s) throws SQLException {

    }

    @Override
    public void updateNString(String s, String s1) throws SQLException {

    }

    @Override
    public void updateNClob(int i, NClob nClob) throws SQLException {

    }

    @Override
    public void updateNClob(String s, NClob nClob) throws SQLException {

    }

    @Override
    public NClob getNClob(int i) throws SQLException {
        return null;
    }

    @Override
    public NClob getNClob(String s) throws SQLException {
        return null;
    }

    @Override
    public SQLXML getSQLXML(int i) throws SQLException {
        return null;
    }

    @Override
    public SQLXML getSQLXML(String s) throws SQLException {
        return null;
    }

    @Override
    public void updateSQLXML(int i, SQLXML sqlxml) throws SQLException {

    }

    @Override
    public void updateSQLXML(String s, SQLXML sqlxml) throws SQLException {

    }

    @Override
    public String getNString(int i) throws SQLException {
        return null;
    }

    @Override
    public String getNString(String s) throws SQLException {
        return null;
    }

    @Override
    public Reader getNCharacterStream(int i) throws SQLException {
        return null;
    }

    @Override
    public Reader getNCharacterStream(String s) throws SQLException {
        return null;
    }

    @Override
    public void updateNCharacterStream(int i, Reader reader, long l) throws SQLException {

    }

    @Override
    public void updateNCharacterStream(String s, Reader reader, long l) throws SQLException {

    }

    @Override
    public void updateAsciiStream(int i, InputStream inputStream, long l) throws SQLException {

    }

    @Override
    public void updateBinaryStream(int i, InputStream inputStream, long l) throws SQLException {

    }

    @Override
    public void updateCharacterStream(int i, Reader reader, long l) throws SQLException {

    }

    @Override
    public void updateAsciiStream(String s, InputStream inputStream, long l) throws SQLException {

    }

    @Override
    public void updateBinaryStream(String s, InputStream inputStream, long l) throws SQLException {

    }

    @Override
    public void updateCharacterStream(String s, Reader reader, long l) throws SQLException {

    }

    @Override
    public void updateBlob(int i, InputStream inputStream, long l) throws SQLException {

    }

    @Override
    public void updateBlob(String s, InputStream inputStream, long l) throws SQLException {

    }

    @Override
    public void updateClob(int i, Reader reader, long l) throws SQLException {

    }

    @Override
    public void updateClob(String s, Reader reader, long l) throws SQLException {

    }

    @Override
    public void updateNClob(int i, Reader reader, long l) throws SQLException {

    }

    @Override
    public void updateNClob(String s, Reader reader, long l) throws SQLException {

    }

    @Override
    public void updateNCharacterStream(int i, Reader reader) throws SQLException {

    }

    @Override
    public void updateNCharacterStream(String s, Reader reader) throws SQLException {

    }

    @Override
    public void updateAsciiStream(int i, InputStream inputStream) throws SQLException {

    }

    @Override
    public void updateBinaryStream(int i, InputStream inputStream) throws SQLException {

    }

    @Override
    public void updateCharacterStream(int i, Reader reader) throws SQLException {

    }

    @Override
    public void updateAsciiStream(String s, InputStream inputStream) throws SQLException {

    }

    @Override
    public void updateBinaryStream(String s, InputStream inputStream) throws SQLException {

    }

    @Override
    public void updateCharacterStream(String s, Reader reader) throws SQLException {

    }

    @Override
    public void updateBlob(int i, InputStream inputStream) throws SQLException {

    }

    @Override
    public void updateBlob(String s, InputStream inputStream) throws SQLException {

    }

    @Override
    public void updateClob(int i, Reader reader) throws SQLException {

    }

    @Override
    public void updateClob(String s, Reader reader) throws SQLException {

    }

    @Override
    public void updateNClob(int i, Reader reader) throws SQLException {

    }

    @Override
    public void updateNClob(String s, Reader reader) throws SQLException {

    }

    @Override
    public <T> T getObject(int i, Class<T> aClass) throws SQLException {
        return null;
    }

    @Override
    public <T> T getObject(String s, Class<T> aClass) throws SQLException {
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


    public void setScriptingLanguage(String vbScript) {
    }

    public void setTransformationScript(String s) {
        __TransformationScript = s;
    }

    List<String> _fields = new ArrayList<String>();

    public void init() throws Exception{
        ScriptEngineManager factory = new ScriptEngineManager();
        // create a JavaScript engine
        _eng =factory.getEngineByName(this.getScriptingLanguage());
        ResultSetMetaData meta = _source.getMetaData();
        int colCount = meta.getColumnCount();
        for (int i = 1;i <=colCount;i++)
        {
            _fields.add(meta.getColumnName(i));
        }
        _eng.put("Record", _rec);
        _eng.put("Console", Console.class);
    }

    HashMap<String,Object> _rec = new HashMap<String,Object>();
    private ColumnMappings __Maps;
    public ColumnMappings getMaps() {
        return __Maps;
    }

    public void setMaps(ColumnMappings value) {
        __Maps = value;
    }



    private String __TransformationScript;
    public String getTransformationScript() {
        return __TransformationScript == null ? "" : __TransformationScript;
    }



    private String __ScriptingLanguage="JavaScript";
    public String getScriptingLanguage() {
        return __ScriptingLanguage;
    }



    ScriptEngine _eng = null;

    private String getStringExpression(Object value) throws Exception {
        if (value instanceof String || value instanceof Date || value instanceof UUID)
        {
            return "\"" + value.toString().replace("\"", "\"\"").replace("\n", "\" + chr(13) +\"") + "\"";
        }
        else if (value == null)
        {
            return "Null";
        }
        else if (value == null)
        {
            return "Null";
        }
        else
        {
            if (StringSupport.isNullOrEmpty(value.toString()))
            {
                int a = 1;
                return "";
            }
            else
            {
                return value.toString();
            }
        }


    }

    /*




    public boolean read() throws Exception {
        boolean result = _source.Read();
        if (result == true)
        {
            // TODO: Implement scripting transformation here
            _rec.clear();
            StringBuilder sb = new StringBuilder();
            for (int i = 0;i < _source.FieldCount;i++)
            {
                _rec.put(_fields.get(i), _source.GetValue(i));
                //sb.AppendLine(string.Format("{0} = {1}",_fields[i], GetStringExpression(_source.GetValue(i))));
                sb.append((String.format(StringSupport.CSFmtStrToJFmtStr("{0} = {1}"),_fields.get(i),"Record.Item(\"" + _fields.get(i) + "\")")) + System.getProperty("line.separator"));
            }
            //Console.WriteLine(sb.ToString());
            _eng.Execute(sb.toString() + "\r\n" + this.getTransformationScript());
        }
         
        return result;
    }



    public Object getValue(int i) throws Exception {
        Object obj = _rec.get(_fields.get(i));
        return obj;
    }

    //throw new NotImplementedException();
    public int getRecordsAffected() throws Exception {
        return _source.RecordsAffected;
    }

    public void dispose() throws Exception {
        throw new NotImplementedException();
    }

    public int getFieldCount() throws Exception {
        if (this.getMaps() != null)
        {
            return this.getMaps().Count;
        }
         
        return _source.FieldCount;
    }

    public boolean getBoolean(int i) throws Exception {
        throw new NotImplementedException();
    }

    public byte getByte(int i) throws Exception {
        throw new NotImplementedException();
    }

    public long getBytes(int i, long fieldOffset, byte[] buffer, int bufferoffset, int length) throws Exception {
        throw new NotImplementedException();
    }

    public char getChar(int i) throws Exception {
        throw new NotImplementedException();
    }

    public long getChars(int i, long fieldoffset, char[] buffer, int bufferoffset, int length) throws Exception {
        throw new NotImplementedException();
    }

    public IDataReader getData(int i) throws Exception {
        throw new NotImplementedException();
    }

    public String getDataTypeName(int i) throws Exception {
        throw new NotImplementedException();
    }

    public Date getDateTime(int i) throws Exception {
        throw new NotImplementedException();
    }

    public double getDecimal(int i) throws Exception {
        throw new NotImplementedException();
    }

    public double getDouble(int i) throws Exception {
        throw new NotImplementedException();
    }

    public Class getFieldType(int i) throws Exception {
        throw new NotImplementedException();
    }

    public float getFloat(int i) throws Exception {
        throw new NotImplementedException();
    }

    public UUID getGuid(int i) throws Exception {
        throw new NotImplementedException();
    }

    public short getInt16(int i) throws Exception {
        throw new NotImplementedException();
    }

    public int getInt32(int i) throws Exception {
        throw new NotImplementedException();
    }

    public long getInt64(int i) throws Exception {
        throw new NotImplementedException();
    }

    public String getName(int i) throws Exception {
        throw new NotImplementedException();
    }

    public int getOrdinal(String name) throws Exception {
        throw new NotImplementedException();
    }

    public String getString(int i) throws Exception {
        throw new NotImplementedException();
    }

    public int getValues(Object[] values) throws Exception {
        throw new NotImplementedException();
    }

    public boolean isDBNull(int i) throws Exception {
        throw new NotImplementedException();
    }

    public Object get___idx(String name) throws Exception {
        throw new NotImplementedException();
    }

    public Object get___idx(int i) throws Exception {
        throw new NotImplementedException();
    }*/

}


