package com.nuarca.etl.provider.text;


import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public class RowDelimitedObjectDefinition {


    @FunctionalInterface
    public interface IsValidRecordHandler {
        public boolean IsValidRecord(String rowContent);
    }

    @FunctionalInterface
    public interface CustomParser {
        public String[] ParseCustom(String rowContent);
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

    public class FieldDefinition {
        public String Name;
        public int Start;
        public int End;

        public FieldDefinition(){}
        public FieldDefinition (String name, int start, int end){
            Name = name;
            Start = start;
            End = end;
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

    public String[] parse(String content) {
        if (this.customParser!=null){
            return this.customParser.ParseCustom(content);
        } else {
            return parseDefault(content);
        }
    }

    String[] parseDefault (String content) {
        List<String> values = new ArrayList<>();
        for(FieldDefinition def : this.fieldDefinitions){
            String value = content.substring(def.Start-1, def.End - def.Start);
            values.add(value);
        }
        return (String[]) values.toArray();
    }

}
