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

    private boolean streamingMode = false;
    public boolean getStreamingMode () {
        return streamingMode;
    }

    public void setStreamingMode (boolean mode){
        streamingMode = mode;
    }

    public RowDelimitedObject readNextObject (){

    }


    @Override
    public String[] processRowElement(String content, String[] fieldSeperator) throws Exception {
        for ( RowDelimitedObjectDefinition objDef : _objectDefitions){
            if (objDef.getOnTaskExecutionEvent().IsValidRecord(content)){
                // Found object
                System.out.println("Found object : " + objDef.getName());
            }
        }
        return null;
    }




}
