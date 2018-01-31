package com.nuarca.etl.provider.text;

import java.util.HashMap;

public class RowDelimitedObject extends HashMap<String , String> {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectString () {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for ( String prop: this.keySet()){
            sb.append(prop);
            sb.append(":\"");
            sb.append(this.get(prop));
            sb.append("\",");
        }
        sb.append("}");
        return sb.toString();
    }

}

