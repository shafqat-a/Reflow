package com.nuarca.etl.helper;

import java.lang.reflect.Array;
import java.util.Arrays;

public class StringHelper {
    public static String SubString ( String content, int startIndex, int length, int indexBase ){
        try {
            char[] chars = Arrays.copyOfRange(content.toCharArray(), startIndex - indexBase, startIndex + length - indexBase);
            return new String(chars);
        } catch ( Exception ex){
            throw ex;
        }
    }
}
