using Microsoft.ClearScript;
using Reflow.Transformation;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Reflow.Transformation
{
    public class TransformedReader : IDataReader
    {
        IDataReader _source = null;
        public TransformedReader(IDataReader reader)
        {
            _source = reader;
        }

        public string TransformationScript { get; set; }
        public string ScriptingLanguage { get; set; }

        ScriptEngine _eng = null;
        List<string> _fields = new List<string>();
        public void Init()
        {
            _eng = ScriptFactory.GetScriptEngine(this.ScriptingLanguage);
            for (int i = 0; i < _source.FieldCount; i++)
            {
                _fields.Add(_source.GetName(i));
            }
            _eng.AddHostObject("Record", _rec);
            _eng.AddHostType("Console", typeof(Console));
            
        }

        public void Close()
        {
            _source.Close();
        }

        public int Depth
        {
            get 
            {
                return _source.Depth;
            }
        }

        public DataTable GetSchemaTable()
        {
            return _source.GetSchemaTable();
        }

        public bool IsClosed
        {
            get { return _source.IsClosed; }
        }

        public bool NextResult()
        {
            return _source.NextResult();
        }

        Dictionary<string, object> _rec = new Dictionary<string, object>();
        public bool Read()
        {
            bool result = _source.Read();
            if ( result== true)
            {
                // TODO: Implement scripting transformation here
                _rec.Clear();
                for (int i = 0; i < _source.FieldCount; i++)
                {
                    _rec[_fields[i]] = _source.GetValue(i);
                }

                _eng.Execute(this.TransformationScript);
            }
            return result;
        }

        public object GetValue(int i)
        {
            object obj= _rec[_fields[i]];
            return obj;
            //throw new NotImplementedException();
        }

        public int RecordsAffected
        {
            get { return _source.RecordsAffected; }
        }

        public void Dispose()
        {
            throw new NotImplementedException();
        }

        public int FieldCount
        {
            get { return _source.FieldCount; }
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

        public IDataReader GetData(int i)
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
            throw new NotImplementedException();
        }

        public int GetOrdinal(string name)
        {
            throw new NotImplementedException();
        }

        public string GetString(int i)
        {
            throw new NotImplementedException();
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
