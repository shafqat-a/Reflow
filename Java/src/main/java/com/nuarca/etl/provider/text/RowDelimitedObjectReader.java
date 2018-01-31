package com.nuarca.etl.provider.text;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RowDelimitedObjectReader extends DelimitedReader {
    public RowDelimitedObjectReader(InputStream stream) throws Exception {
        super(stream);
    }

    List<RowDelimitedObjectDefinition> _objectDefitions = new ArrayList<>();

    public List<RowDelimitedObjectDefinition> getObjectDefinitions () {
        return  _objectDefitions;
    }

    private boolean isStreamingMode = false;
    public boolean getStreamingMode () {
        return isStreamingMode;
    }

    public void setStreamingMode (boolean mode){
        isStreamingMode = mode;
    }

    public RowDelimitedObject readNextObject () throws SQLException{
        boolean successfulRead = this.next();
        while (successfulRead==true){
            if ( _currentObjectType!=null){
                // Read a row. and recognized the record type
                 RowDelimitedObject rdo =_currentObjectType.parse(_currentStringData);
                 return rdo;
            } else {
                // Read a row. Did not recognize the record type
                successfulRead = this.next();
            }
        }
        return null;
    }

    private RowDelimitedObjectDefinition _currentObjectType = null;
    private String _currentStringData = null;

    @Override
    public String[] processRowElement(String content, String[] fieldSeperator) throws Exception {
        for ( RowDelimitedObjectDefinition objDef : _objectDefitions){
            try{
            if (objDef.getOnTaskExecutionEvent().IsValidRecord(content)){
                // Found object
                System.out.println("Found object : " + objDef.getName());
                _currentObjectType = objDef;
                _currentStringData = content;
                return null;
            }}catch ( Exception e){
                // Do not let external code fail the loop
            }
        }
        _currentObjectType = null;
        _currentStringData = null;
        return null;
    }

    ScriptEngineManager engineManager = new ScriptEngineManager();
    ScriptEngine engine = engineManager.getEngineByName("nashorn");


    public boolean loadRowDelimitedObjectDefintions ( String jsonContent) throws Exception{
        engineManager = new ScriptEngineManager();
        engine = engineManager.getEngineByName("nashorn");

        JSObject obj =  (JSObject)engine.eval(jsonContent);

        List<RowDelimitedObjectDefinition> defs = new ArrayList<>();

        // Get each items from array
        Object[] jsRdoDefs =  obj.values().toArray();
        for(Object objRdoDef: jsRdoDefs){
            JSObject jsRdoDef = (JSObject) objRdoDef;
            RowDelimitedObjectDefinition def = new RowDelimitedObjectDefinition();
            def.setName( jsRdoDef.getMember("name").toString());
            defs.add(def);
            Object[] objFields = ((JSObject)jsRdoDef.getMember("fields")).values().toArray();
            for (Object objField : objFields){
                JSObject jsField = (JSObject)objField;
                RowDelimitedObjectDefinition.FieldDefinition fdef = new RowDelimitedObjectDefinition.FieldDefinition();
                fdef.Name = jsField.getMember("name").toString();
                fdef.Start = Integer.parseInt( jsField.getMember("start").toString());
                fdef.Length = Integer.parseInt( jsField.getMember("length").toString());
                def.getFieldDefinitions().add(fdef);
            }

            def.setJsonObject(jsRdoDef);
            def.setIsValidRecordHandler( (String content)-> {
                boolean result =false;
                try {
                     result= (boolean) ((ScriptObjectMirror) def.getJsonObject()).callMember("isValid", content);
                } catch ( Exception ex){
                    //return false;
                }
                return result;
            });

        }
        this.getObjectDefinitions().clear();
        this.getObjectDefinitions().addAll(defs);
        return true;
    }


}
