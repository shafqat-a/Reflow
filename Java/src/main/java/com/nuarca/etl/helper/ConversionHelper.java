package com.nuarca.etl.helper;

public class ConversionHelper {

    public static int tryParseInteger ( String value ) {
        int returnValue = Integer.MIN_VALUE;
        try {
             returnValue = Integer.parseInt(value);
        } catch ( NumberFormatException ex){

        }
        return returnValue;
    }
}
