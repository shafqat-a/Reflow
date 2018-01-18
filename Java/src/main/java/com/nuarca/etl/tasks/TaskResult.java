package com.nuarca.etl.tasks;

import java.util.HashMap;

public class TaskResult {


    private boolean __IsSuccess;
    public boolean getIsSuccess() {
        return __IsSuccess;
    }

    public void setIsSuccess(boolean value) {
        __IsSuccess = value;
    }

    private String __Message;
    public String getMessage() {
        return __Message;
    }

    public void setMessage(String value) {
        __Message = value;
    }

    private HashMap<String,Object> __Output;
    public HashMap<String,Object> getOutput() {
        return __Output;
    }

    public void setOutput(HashMap<String,Object> value) {
        __Output = value;
    }

}
