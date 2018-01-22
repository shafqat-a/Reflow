package com.nuarca.etl.helper;

public class Convert {

    public static String ToString (Object obj){
        if (obj==null){
            return "";

        } else {

            return  obj.toString();
        }
    }
}
