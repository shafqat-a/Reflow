package com.nuarca.etl.provider.text;


public class RowDelimitedObjectDefinition {


    @FunctionalInterface
    public interface IsValidRecordHandler {
        public boolean IsValidRecord(String rowContent);
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


}
