package com.nuarca.etl.provider.text;


import com.nuarca.etl.helper.StringHelper;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.parser.JSONParser;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.print.DocFlavor;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.List;

public class RowDelimitedObjectDefinition {


    @FunctionalInterface
    public interface IsValidRecordHandler {
        public boolean IsValidRecord(String rowContent);
    }

    @FunctionalInterface
    public interface CustomParser {
        public RowDelimitedObject ParseCustom(String rowContent);
    }


    private String name;
    public String getName () {
        return name;
    }

    public void setName ( String nameToSet){
        name = nameToSet;
    }

    private IsValidRecordHandler _onRowAvailable = null;

    public IsValidRecordHandler getOnTaskExecutionEvent() {
        return _onRowAvailable;
    }

    public void setIsValidRecordHandler (IsValidRecordHandler handler){
        _onRowAvailable = handler;
    }

    public static class FieldDefinition {
        public String Name;
        public int Start;
        public int Length;

        public FieldDefinition(){}
        public FieldDefinition (String name, int start, int length){
            Name = name;
            Start = start;
            Length = length;
        }
    }

    private CustomParser customParser = null;

    public CustomParser getCustomParser() {
        return customParser;
    }

    public void setCustomParser (CustomParser parser){
        customParser = parser;
    }


    private List<FieldDefinition> fieldDefinitions = new ArrayList<>();
    public List<FieldDefinition> getFieldDefinitions (){
        return fieldDefinitions;
    }

    public void addFieldDefinition (String name, int start, int end ){
        FieldDefinition def = new FieldDefinition(name, start,end);
        fieldDefinitions.add(def);

    }

    public RowDelimitedObject parse(String content) {
        if (this.customParser!=null){
            return this.customParser.ParseCustom(content);
        } else {
            return parseDefault(content);
        }
    }

    RowDelimitedObject parseDefault (String content) {
        RowDelimitedObject rdo = new RowDelimitedObject();
        rdo.setName(this.getName());
        for(FieldDefinition def : this.fieldDefinitions){
            String value = StringHelper.SubString(content, def.Start, def.Length, 0);
            //content.substring(def.Start, def.End);
            rdo.put(def.Name, value);
        }

        return rdo;
    }

    private JSObject JsonObject;

    public JSObject getJsonObject() {
        return JsonObject;
    }

    public void setJsonObject(JSObject jsonObject) {
        JsonObject = jsonObject;
    }
}
