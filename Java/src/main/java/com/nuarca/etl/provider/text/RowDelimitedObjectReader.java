package com.nuarca.etl.provider.text;

import java.io.InputStream;
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

    public RowDelimitedObject readNextObject (){

    }

    private RowDelimitedObjectDefinition _currentObjectType = null;
    private String _currentStringData = null;

    @Override
    public String[] processRowElement(String content, String[] fieldSeperator) throws Exception {
        for ( RowDelimitedObjectDefinition objDef : _objectDefitions){
            if (objDef.getOnTaskExecutionEvent().IsValidRecord(content)){
                // Found object
                System.out.println("Found object : " + objDef.getName());
                _currentObjectType = objDef;
                _currentStringData = content    ;
                return null;
            }
        }
        _currentObjectType = null;
        _currentStringData = null;
        return null;
    }




}
