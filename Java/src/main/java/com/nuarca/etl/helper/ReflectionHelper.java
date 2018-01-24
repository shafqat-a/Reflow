package com.nuarca.etl.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.c;


public class ReflectionHelper {

    public static Method getGetterMethodForProperty (String propertyName, Class type) throws NoSuchMethodException, SecurityException{
        Method method =  type.getDeclaredMethod("get" + propertyName);
        return method;
    }

    public static Method getSetterMethodForProperty (String propertyName, Class type) throws NoSuchMethodException, SecurityException{;
        String setterName = "set" + propertyName;
        String classname = type.getName();
        //Method method =  type.getDeclaredMethod( setterName, type);

        Method[] methods =  type.getMethods();
        for (Method method: methods){
            if (method.getName().equals(setterName)){
                return method;
            }
        }

        return null;
    }

    public static Object CreateInstance ( Class type) throws Exception{
        Constructor constructor = type.getConstructor();
        return constructor.newInstance();
    }


    public static Object CreateInstance ( Class type, Object parm1) throws Exception{
        Constructor[] constructors = type.getDeclaredConstructors();
        for (Constructor c : constructors){
            if (c.getParameterCount()==1){
                return c.newInstance(parm1);
            }
        }
        return null;
    }
}
