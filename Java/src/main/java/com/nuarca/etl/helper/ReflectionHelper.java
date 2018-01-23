package com.nuarca.etl.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;



public class ReflectionHelper {

    public static Method getGetterMethodForProperty (String propertyName, Class type) throws NoSuchMethodException{
        Method method =  type.getMethod("get" + propertyName);
        return method;
    }

    public static Method getSetterMethodForProperty (String propertyName, Class type) throws NoSuchMethodException{
        Method method =  type.getMethod("set" + propertyName);
        return method;
    }

    public static Object CreateInstance ( Class type) throws Exception{
        Constructor constructor = type.getConstructor();
        return constructor.newInstance();
    }


}
