using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Text;

namespace Reflow.Provider.Text
{
    public class DelimitedReader : System.Data.IDataReader
    {
        private Stream _stream;
        private string[] _values;
        StreamReader _reader;
        int _pos = 0;
        bool _headerFound = false;
        StringBuilder _sbRecord = new StringBuilder();

        public DelimitedReader(Stream stream)
        {
            _stream = stream;
            this.BufferSize = 255;
        }

        public bool FirstRowHasNames
        {
            get;
            set;
        }

        public string ColumnSeperator
        {
            get;
            set;
        }

        public string RowSeperator
        {
            get;
            set;
        }

        public bool QuotedIdentifier
        {
            get;
            set;
        }

        private string[] _fieldNames;

        public string[] FieldNames
        {
            get
            {
                if (_headerFound==false)
                {
                    this.ReadInternal(true);
                }
                return _fieldNames;
            }
            internal set { _fieldNames = value; }
        }

        public object[] GetValues()
        {
            return _values;
        }

        public int BufferSize
        {
            get;
            set;
        }


        public bool Open()
        {
            // TODO: Do some error handling here 
            _reader = new StreamReader(_stream);
            return true;
        }

        public bool Read ()
        {
            return ReadInternal(false);
        }

        private bool ReadInternal (bool headerOnly)
        {
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

            char[] buffer = new char[this.BufferSize];
            string[] fieldSeperator = new string[] { this.ColumnSeperator };
            bool readComplete = false;
            // Keep on reading until found a Line limiter or EOF
            while (readComplete == false && _reader.EndOfStream==false)
            {
                // This is the amount we have already read
                string alreadyRead = _sbRecord.ToString();
                // See if line limiter is in the buffer
                int posLine = alreadyRead.IndexOf(this.RowSeperator);
                    
                if (posLine > -1)
                {
                    // Yes it is. So just get the line
                    string line = alreadyRead.Substring(0, posLine);
                    
                    // Do we need to read the header?
                    if (FirstRowHasNames && !_headerFound)
                    {
                        string[] fields = line.Split(fieldSeperator, StringSplitOptions.None);
                        // Might need to remove quotes from the field names
                        this.FieldNames = ProcessFieldNames(fields);
                        _headerFound = true;
                        if (headerOnly)
                        {
                            readComplete = true;
                        }
                    }
                    else
                    {
                        // This is a row. Read the row and read field values
                        _values = ProcessValues(line, fieldSeperator);
                        // We compleated reading the line from buffer and procees the values
                        readComplete = true;
                    }
                    // Remove from buffer what we have already processed
                    _sbRecord.Remove(0, line.Length + this.RowSeperator.Length);
                    
                }
                else
                {
                    int bytesRead = _reader.ReadBlock(buffer, 0, this.BufferSize);
                    string textRead = new string(buffer);
                    _sbRecord.Append(textRead);
                    _pos = _pos + bytesRead;
                }
            }
            return true && _reader.EndOfStream==false;

        }

        private string[] ProcessFieldNames(string[] fields)
        {
            List<string> names = new List<string>();
            foreach ( string field in fields)
            {
                names.Add(field.Replace("\"", ""));
            }
            return names.ToArray();
            //throw new NotImplementedException();
        }

        private string[] ProcessValues(string line, string[] fieldSeperator)
        {
            return ProcessFieldNames(line.ToString().Split(fieldSeperator, StringSplitOptions.None));
            //throw new NotImplementedException();
        }


        public void Close()
        {
            try
            {
                _reader.Close();
            }
            catch (Exception ex)
            {

            }
            _stream = null;
            _reader = null;
            _sbRecord.Clear();
            _sbRecord = null;
            _values = null;
            FieldNames = null;
        }

        public int Depth
        {
            get { throw new NotImplementedException(); }
        }

        public System.Data.DataTable GetSchemaTable()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("ColumnName", typeof(string));
            dt.Columns.Add("ColumnSize", typeof(int));
            foreach (string field in this.FieldNames)
            {
                DataRow row =  dt.NewRow();
                row.SetField<string> ("ColumnName", field);
                row.SetField<int> ("ColumnSize", 100);
                dt.Rows.Add(row);
            }
            return dt;
        }

        public bool IsClosed
        {
            get { return _stream == null; }
        }

        public bool NextResult()
        {
            return this.Open();
        }

        public int RecordsAffected
        {
            get { throw new NotImplementedException(); }
        }

        public void Dispose()
        {
            this.Close();
        }

        public int FieldCount
        {
            get { return this.FieldNames.Length; }
        }

        public bool GetBoolean(int i)
        {
            throw new NotImplementedException();
        }

        public byte GetByte(int i)
        {
            throw new NotImplementedException();
        }

        public long GetBytes(int i, long fieldOffset, byte[] buffer, int bufferoffset, int length)
        {
            throw new NotImplementedException();
        }

        public char GetChar(int i)
        {
            throw new NotImplementedException();
        }

        public long GetChars(int i, long fieldoffset, char[] buffer, int bufferoffset, int length)
        {
            throw new NotImplementedException();
        }

        public System.Data.IDataReader GetData(int i)
        {
            throw new NotImplementedException();
        }

        public string GetDataTypeName(int i)
        {
            throw new NotImplementedException();
        }

        public DateTime GetDateTime(int i)
        {
            throw new NotImplementedException();
        }

        public decimal GetDecimal(int i)
        {
            throw new NotImplementedException();
        }

        public double GetDouble(int i)
        {
            throw new NotImplementedException();
        }

        public Type GetFieldType(int i)
        {
            throw new NotImplementedException();
        }

        public float GetFloat(int i)
        {
            throw new NotImplementedException();
        }

        public Guid GetGuid(int i)
        {
            throw new NotImplementedException();
        }

        public short GetInt16(int i)
        {
            throw new NotImplementedException();
        }

        public int GetInt32(int i)
        {
            throw new NotImplementedException();
        }

        public long GetInt64(int i)
        {
            throw new NotImplementedException();
        }

        public string GetName(int i)
        {
            return this.FieldNames[i];
        }

        public int GetOrdinal(string name)
        {
            throw new NotImplementedException();
        }

        public string GetString(int i)
        {
            throw new NotImplementedException();
        }

        public object GetValue(int i)
        {
            return this._values[i];
        }

        public int GetValues(object[] values)
        {
            throw new NotImplementedException();
        }

        public bool IsDBNull(int i)
        {
            throw new NotImplementedException();
        }

        public object this[string name]
        {
            get { throw new NotImplementedException(); }
        }

        public object this[int i]
        {
            get { throw new NotImplementedException(); }
        }
    }
}
