package com.nuarca.etl.provider;

import java.sql.ResultSet;

public interface ILinkReader {

    void initialize(IDataLink link) throws Exception ;

    String getCommand() throws Exception ;

    void setCommand(String value) throws Exception ;

    boolean open() throws Exception ;

    boolean open(String command) throws Exception ;

    ResultSet getReader() throws Exception ;
}

