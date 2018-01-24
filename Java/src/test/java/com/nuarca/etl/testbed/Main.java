package com.nuarca.etl.testbed;

import com.nuarca.etl.*;
import com.nuarca.etl.provider.IDataLink;
import com.nuarca.etl.provider.ILinkReader;
import com.nuarca.etl.provider.ILinkWriter;
import com.nuarca.etl.provider.sqlserver.SqlLinkProvider;
import com.nuarca.etl.provider.text.DelimitedReader;
import com.nuarca.etl.provider.text.TextProvider;
import com.nuarca.etl.tasks.DataFlowTask;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileInputStream;
import java.util.HashMap;

public class Main {

    public static void main ( String[] argv){

        //testTextDelimitedReader("/home/shafqat/Documents/employee.csv");
        //testScriptEngine ();
        testCsvImportBasic();

    }

    public static void testCsvImportBasic() {



        try {

            TextProvider tprov = new TextProvider();
            IDataLink lnkSrc =  tprov.createLink("@Type=Delimited;@File=/home/shafqat/git/reflow/Java/src/test/res/data.csv;FirstRowHasNames=true;");
            ILinkReader reader = tprov.createReader(lnkSrc, "Select * from data.csv");

            String sqlConnectionString = "jdbc:sqlserver://localhost:1401;databaseName=master;user=sa;password=Nuarca123!";
            SqlLinkProvider destProv = new SqlLinkProvider();
            IDataLink lnkDst = destProv.createLink(sqlConnectionString);
            ILinkWriter writer = destProv.createWriter(lnkDst, "abc");

            ReflowEngine engine = new ReflowEngine();

            DataFlowTask flow = new DataFlowTask();
            flow.setInput(reader);
            flow.setOutput(writer);
            flow.setIsAutoMap(true);
            flow.setTableName("abc");

            engine.getTasks().add(flow);

            ExecutionEventListener exec = new ExecutionEventListener();
            exec.setOnTaskExecutionEvent( (taskName, eventName, description) -> {
               System.out.println(taskName + " | " + eventName + " | "  + description);
            });

            engine.execute(exec);

        } catch ( Exception ex){
            System.out.println(ex);
            ex.printStackTrace();
        }


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

    public  static  void testScriptEngine()  {
        // create a script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();
        // create a JavaScript engine
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        // evaluate JavaScript code from String
        try {

            HashMap<String, String> h = new HashMap<String, String>();
            h.put("AAA", "This is a java string");
            engine.put("xmap",h);

            engine.eval("print('Hello, World'); print (xmap.get('AAA'))");
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

}
