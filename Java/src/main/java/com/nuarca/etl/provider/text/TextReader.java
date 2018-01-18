package com.nuarca.etl.provider.text;

import java.lang.reflect.Method;
import java.rmi.activation.Activator;
import java.sql.ResultSet;
import java.util.HashMap;

import CS2JNet.JavaSupport.Collections.Generic.CollectionSupport;
import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.DoubleSupport;
import CS2JNet.System.IO.FileAccess;
import CS2JNet.System.IO.FileMode;
import CS2JNet.System.IO.FileStreamSupport;
import CS2JNet.System.ObjectSupport;
import com.nuarca.etl.helper.ConversionHelper;
import com.nuarca.etl.helper.ReflectionHelper;
import com.nuarca.etl.provider.IDataLink;
import com.nuarca.etl.provider.ILinkReader;
import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;

public class TextReader   implements ILinkReader
{
    TextLink _link = null;
    ResultSet _reader = null;
    public void initialize(IDataLink link) throws Exception {
        _link = link instanceof TextLink ? (TextLink)link : (TextLink)null;
    }

    private String __Command;
    public String getCommand() {
        return __Command;
    }

    public void setCommand(String value) {
        __Command = value;
    }

    public boolean open() throws Exception {
        if (_link != null)
        {
            HashMap<String,String> props = _link.getConnectionProperties();
            String typeName = "com.nuarca.etl.provider.text." + props.get("@Type") + "Reader";
            Class readerType =  Class.forName(typeName);
            String file = props.get("@File");

            FileStreamSupport fs = new FileStreamSupport(file, FileMode.Open, FileAccess.Read);
            _reader = (ResultSet) ReflectionHelper.CreateInstance(readerType);
            for (String key : CollectionSupport.mk(props.keySet()))
            {
                if (!key.startsWith("@"))
                {
                    String valueString = props.get(key);

                    Object value = getValueFromString(valueString);
                    Method method = ReflectionHelper.getSetterMethodForProperty( key, readerType);
                    method.invoke(_reader, value);
                }

            }
            _reader.next();
        }

        return false;
    }

    private Object getValueFromString(String valueString) throws Exception {
        if (valueString.toLowerCase().equals("true") || valueString.toLowerCase().equals("false"))
        {
            return Boolean.valueOf(valueString);
        }
        else
        {
            int intVal = ConversionHelper.tryParseInteger(valueString);
            if ( Integer.MIN_VALUE != intVal){
                return  intVal;
            }
        }
        return valueString;
    }

    public boolean open(String command) throws Exception {
        this.setCommand(command);
        return this.open();
    }

    public java.sql.ResultSet getReader() throws Exception {
        return _reader;
    }

}



