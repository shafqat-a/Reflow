package com.nuarca.etl.provider;

import com.nuarca.etl.tasks.TaskExecutionContext;

import java.sql.ResultSet;

public interface ILinkWriter {

    void write(ResultSet source, String table, TaskExecutionContext context) throws Exception ;

    void initialize(IDataLink link) throws Exception ;
}
