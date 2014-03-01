using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;

namespace Reflow.Provider.Text
{
    public class TextReader : ILinkReader
    {
        TextLink _link = null;
        IDataReader _reader = null;
        public void Initialize(IDataLink link)
        {
            _link = link as TextLink;
        }

        public string Command
        {
            get;
            set;
        }

        public bool Open()
        {
            if ( _link!=null)
            {
                Dictionary<string, string> props = _link.ConnectionProperties;
                string typeName = "Reflow.Provider.Text." + props["@Type"] + "Reader";
                Type readerType = Type.GetType(typeName);
                string file = props["@File"];
                FileStream fs = new FileStream (file,FileMode.Open);
                _reader = (IDataReader)Activator.CreateInstance(readerType,fs);
                foreach (string key in props.Keys)
                {
                    if (!key.StartsWith("@"))
                    {
                        string valueString = props[key];
                        object value = GetValueFromString(valueString);
                        PropertyInfo pinfo = readerType.GetProperty(key);
                        pinfo.SetValue(_reader, value);   
                    }
                }
                _reader.NextResult();
            }
            return false;
        }

        private object GetValueFromString (string valueString)
        {
            if (valueString.Equals("true",StringComparison.InvariantCultureIgnoreCase) || 
                valueString.Equals("false", StringComparison.InvariantCultureIgnoreCase))
            {
                return bool.Parse(valueString);
            }
            else
            {
                int intVal = 0;
                if (int.TryParse(valueString, out intVal))
                {
                    return intVal;
                }
            }
            return valueString;
        }

        public bool Open(string command)
        {
            this.Command = command;
            return this.Open();
        }

        public System.Data.IDataReader Reader
        {
            get { return _reader; }
        }
    }
}
