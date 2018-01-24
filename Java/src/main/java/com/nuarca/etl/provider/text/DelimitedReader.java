package com.nuarca.etl.provider.text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import CS2JNet.System.IO.StreamReader;
import CS2JNet.System.StringSplitOptions;
import CS2JNet.System.StringSupport;
import CS2JNet.System.Text.EncodingSupport;

public class DelimitedReader implements java.sql.ResultSet {

    private InputStream _stream;
    private String[] _values;
    BufferedReader _reader;
    int _pos = 0;
    boolean _headerFound = false;
    StringBuilder _sbRecord = new StringBuilder();

    public DelimitedReader(InputStream stream) throws Exception {
        _stream = stream;
        this.setBufferSize(255);
    }

    private boolean _FirstRowHasNames;
    public boolean getFirstRowHasNames() {
        return _FirstRowHasNames;
    }

    public void setFirstRowHasNames(boolean value) {
        _FirstRowHasNames = value;
    }

    private String _ColumnSeperator=",";
    public String getColumnSeperator() {
        return _ColumnSeperator;
    }

    public void setColumnSeperator(String value) {
        _ColumnSeperator = value;
    }

    private String _RowSeperator="\n";
    public String getRowSeperator() {
        return _RowSeperator;
    }

    public void setRowSeperator(String value) {
        _RowSeperator = value;
    }

    private boolean _QuotedIdentifier;
    public boolean getQuotedIdentifier() {
        return _QuotedIdentifier;
    }

    public void setQuotedIdentifier(boolean value) {
        _QuotedIdentifier = value;
    }

    private String[] _fieldNames;
    public String[] getFieldNames() throws Exception {
        if (_headerFound == false)
        {
            this.readInternal(true);
        }

        return _fieldNames;
    }

    public void setFieldNames(String[] value) throws Exception {
        _fieldNames = value;
    }

    public Object[] getValues() throws Exception {
        return _values;
    }

    private int _BufferSize;
    public int getBufferSize() {
        return _BufferSize;
    }

    public void setBufferSize(int value) {
        _BufferSize = value;
    }

    public boolean open() throws Exception {
        // TODO: Do some error handling here
        _reader = new BufferedReader(StreamReader.make(new BufferedInputStream(_stream), new EncodingSupport("UTF-8")));
        return true;
    }

    public boolean read() throws Exception {
        return readInternal(false);
    }

    private boolean readInternal(boolean headerOnly) throws Exception {
        /*
         *  What cannot be done :
         *  While we read, cannot use Readline, since record delimiter might
         *  be different. Also we cannot use ReadToEnd since the file could be very
         *  large. Also we cannot read one char by one since that would degrade the
         *  performance of the reader.
         *
         *  Implementation:
         *  1. We can safely assume one single record will not will be very large.
         *  2. We can use a StringBuilder to read in a buffered way
         *  3. BufferSize will figure out much we read from stream at each go.
         *  4. After each read we will check the stringbuilder for line delimiter
         *  5. If LD found then we will split that into fields
         *  6. Also then remove than from the string builder.
         *
         *  Gist:
         *  In order to not hog the memory down, we will manually read.
         *
         *  Note:
         *  Instead of binary reader we user StreamReader to ensure no issues with
         *  encoding.
         */
        char[] buffer = new char[this.getBufferSize()];
        String[] fieldSeperator = new String[]{ this.getColumnSeperator() };
        boolean readComplete = false;

        int lastBytesRead = 0;
        while (readComplete == false && lastBytesRead != -1)
        {
            // Keep on reading until found a Line limiter or EOF
            // This is the amount we have already read
            String alreadyRead = _sbRecord.toString();
            // See if line limiter is in the buffer
            int posLine = alreadyRead.indexOf(this.getRowSeperator());
            if (posLine > -1)
            {
                // Yes it is. So just get the line
                String line = alreadyRead.substring(0, (0) + (posLine));
                // Do we need to read the header?
                if (getFirstRowHasNames() && !_headerFound)
                {
                    String[] fields = StringSupport.Split(line, fieldSeperator, StringSplitOptions.None);
                    // Might need to remove quotes from the field names
                    this.setFieldNames(processFieldNames(fields));
                    _headerFound = true;
                    if (headerOnly)
                    {
                        readComplete = true;
                    }

                }
                else
                {
                    // This is a row. Read the row and read field values
                    _values = processValues(line,fieldSeperator);
                    // We compleated reading the line from buffer and procees the values
                    readComplete = true;
                }
                // Remove from buffer what we have already processed
                _sbRecord.delete(0, (0)+(line.length() + this.getRowSeperator().length()));
            }
            else
            {
                lastBytesRead = _reader.read(buffer, 0, this.getBufferSize());
                String textRead = new String(buffer);
                _sbRecord.append(textRead);
                _pos = _pos + lastBytesRead;
            }
        }
        return true && lastBytesRead != -1;
    }

    private String[] processFieldNames(String[] fields) throws Exception {
        ArrayList<String> names = new ArrayList<String>();
        for (String field : fields)
        {
            names.add(field.replace("\"", ""));
        }
        return names.toArray(new String[names.size()]);
    }

    //throw new NotImplementedException();
    private String[] processValues(String line, String[] fieldSeperator) throws Exception {
        return processFieldNames(StringSupport.Split(line.toString(), fieldSeperator, StringSplitOptions.None));
    }

    @Override
    public boolean next() throws SQLException {
        try {
            if (this.isClosed()){
                this.open();
            }

            return read();
        } catch ( Exception ex){
            throw  new SQLException(ex);
        }
    }

    //throw new NotImplementedException();
    @Override
    public void close() throws SQLException {
        try
        {
            _reader.close();
            _stream = null;
            _reader = null;
            _sbRecord.setLength(0);
            _sbRecord = null;
            _values = null;
            setFieldNames(null);
        }
        catch (Exception ex)
        {
            throw new SQLException(ex);
        }

    }

    @Override
    public boolean wasNull() throws SQLException {
        return false;
    }

    @Override
    public String getString(int i) throws SQLException {
        try {
            return _values[i];
        } catch (Exception ex){
            throw new SQLException(ex);
        }
    }

    @Override
    public boolean getBoolean(int i) throws SQLException {
        try {
            return Boolean.parseBoolean( _values[i]);
        } catch (Exception ex){
            throw new SQLException(ex);
        }
    }

    @Override
    public byte getByte(int i) throws SQLException {
        try {
            return Byte.parseByte( _values[i]);
        } catch (Exception ex){
            throw new SQLException(ex);
        }
    }

    @Override
    public short getShort(int i) throws SQLException {
        try {
            return Short.parseShort( _values[i]);
        } catch (Exception ex){
            throw new SQLException(ex);
        }
    }

    @Override
    public int getInt(int i) throws SQLException {
        try {
            return Integer.parseInt(_values[i]);
        } catch (Exception ex){
            throw new SQLException(ex);
        }
    }

    @Override
    public long getLong(int i) throws SQLException {
        try {
            return Long.parseLong( _values[i]);
        } catch (Exception ex){
            throw new SQLException(ex);
        }

    }

    @Override
    public float getFloat(int i) throws SQLException {
        try {
            return Float.parseFloat( _values[i]);
        } catch (Exception ex){
            throw new SQLException(ex);
        }
    }

    @Override
    public double getDouble(int i) throws SQLException {
        try {
            return Double.parseDouble( _values[i]);
        } catch (Exception ex){
            throw new SQLException(ex);
        }
    }

    @Override
    public BigDecimal getBigDecimal(int i, int i1) throws SQLException {
        throw new SQLException("Not implemented");
    }

    @Override
    public byte[] getBytes(int i) throws SQLException {
        throw new SQLException("Not implemented");
    }

    @Override
    public java.sql.Date getDate(int i) throws SQLException {
        try {
            return Date.valueOf(_values[i]);
        } catch (Exception ex){
            throw new SQLException(ex);
        }
    }

    @Override
    public Time getTime(int i) throws SQLException {
        try {
            return Time.valueOf( _values[i]);
        } catch (Exception ex){
            throw new SQLException(ex);
        }

    }

    @Override
    public Timestamp getTimestamp(int i) throws SQLException {
        try {
            return Timestamp.valueOf( _values[i]);
        } catch (Exception ex){
            throw new SQLException(ex);
        }

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


        Metadata data = new Metadata();
        try {
            for (String field : this.getFieldNames()) {
                MetaRow row = new MetaRow();
                row.type = JDBCType.VARCHAR;
                row.precision = 200;
                row.name = field;
                data.getList().add(row);
            }
        } catch (Exception e){
            throw new SQLException(e);
        }
        return data;

    }

    @Override
    public Object getObject(int i) throws SQLException {
        return _values[i];
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
        if (_reader == null) return true;
        return false;
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

    public int getDepth() throws Exception {
       return 0;
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
