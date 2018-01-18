package com.nuarca.etl.testbed;

import com.nuarca.etl.*;
import com.nuarca.etl.provider.text.DelimitedReader;

import java.io.FileInputStream;

public class Main {

    public static void main ( String[] argv){

        testTextDelimitedReader("/home/shafqat/Documents/employee.csv");
    }

    public static void testTextDelimitedReader (String filename){

        try {
            FileInputStream fis = new FileInputStream(filename);
            DelimitedReader reader = new DelimitedReader(fis);
            reader.setColumnSeperator(",");
            reader.setRowSeperator("\n");
            reader.setFirstRowHasNames(true);
            reader.open();
            reader.read();
            reader.read();
        } catch ( Exception ex){
            System.out.println(ex.toString());
        }
    }
}
