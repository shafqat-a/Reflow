package com.nuarca.etl.transformation;

public class Expression {
    //public string Language { get; set; }
    // Hardcode now
    public String getLanguage() throws Exception {
        return "Java";
    }

    private String _Code;
    public String getCode() {
        return _Code;
    }

    public void setCode(String value) {
        _Code = value;
    }
}
