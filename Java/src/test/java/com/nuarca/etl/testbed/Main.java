package com.nuarca.etl.testbed;

import com.nuarca.etl.*;
import com.nuarca.etl.helper.StringHelper;
import com.nuarca.etl.provider.IDataLink;
import com.nuarca.etl.provider.ILinkReader;
import com.nuarca.etl.provider.ILinkWriter;
import com.nuarca.etl.provider.sqlserver.SqlLinkProvider;
import com.nuarca.etl.provider.text.*;
import com.nuarca.etl.tasks.DataFlowTask;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;


public class Main {

    public static void main ( String[] argv){

        //testTextDelimitedReader("/home/shafqat/Documents/employee.csv");
        //testTextDelimitedReader("/home/shafqat/git/reflow/Java/src/test/res/data.csv");
        //testScriptEngine ();
        //testCsvImportBasic();

        //String test = "012345678901234567890";

        //System.out.println(StringHelper.SubString( test, 0, 2, 0));
        //System.out.println(StringHelper.SubString( test, 1, 2, 1));


         testFixedLengthMultuObjectReader();
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

    public static void testFixedLengthMultuObjectReader() {

        try {

            TextProvider tprov = new TextProvider();
            IDataLink lnkSrc = tprov.createLink("@Type=RowDelimitedObject;@File=/home/shafqat/Downloads/s00001vt1.dat;FirstRowHasNames=false;");
            ILinkReader lnreader = tprov.createReader(lnkSrc, "");
            lnreader.open();
            ResultSet reader = lnreader.getReader();
            RowDelimitedObjectReader rdoReader = (RowDelimitedObjectReader)reader;

            String jsonContent = new String(Files.readAllBytes(Paths.get("/home/shafqat/git/reflow/Java/src/test/res/voted-unvoted-broadridge.json.js")));

            rdoReader.loadRowDelimitedObjectDefintions(jsonContent);
            RowDelimitedObject rdo = ((RowDelimitedObjectReader) reader).readNextObject();
            while ( rdo!=null){
                //System.out.println(">---- Reading row ----->");
                System.out.println( rdo.getObjectString());
                rdo = ((RowDelimitedObjectReader) reader).readNextObject();
            }

        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }

            /*

            RowDelimitedObjectDefinition fileHeader = new RowDelimitedObjectDefinition();
            fileHeader.setName("File Header");
            fileHeader.setIsValidRecordHandler((String content)-> {

                if ( content.length()==400){
                    if (content.substring(0,2).equals("00")){
                        return true;
                    }
                }
                return false;
            });

            rdoReader.getObjectDefinitions().add(fileHeader);

            RowDelimitedObjectDefinition detailRecord = new RowDelimitedObjectDefinition();
            detailRecord.setName("Detail Record");
            detailRecord.setIsValidRecordHandler((String content)-> {
                if (content.substring(0,2).equals("02")){
                    return true;
                } else
                    return false;
            });
            rdoReader.getObjectDefinitions().add(detailRecord);
            detailRecord.addFieldDefinition("record_type", 1,2);
            detailRecord.addFieldDefinition("control_number", 3,12);


            RowDelimitedObjectDefinition requestTrailer = new RowDelimitedObjectDefinition();
            requestTrailer.setName("Request Trailer");
            requestTrailer.setIsValidRecordHandler((String content)-> {
                if (content.substring(0,2).equals("03")){
                    return true;
                } else
                    return false;
            });
            rdoReader.getObjectDefinitions().add(requestTrailer);

            */

    }

    public static void testTextDelimitedReader (String filename){

        try {
            FileInputStream fis = new FileInputStream(filename);
            DelimitedReader reader = new DelimitedReader(fis);
            reader.setColumnSeperator(",");
            reader.setRowSeperator("\n");
            reader.setFirstRowHasNames(true);
            reader.open();
            int colCount = reader.getMetaData().getColumnCount();
            int row=1;
            while ( reader.read()){
                System.out.print(row);
                System.out.print(" -> ");
                for ( int i=0; i < colCount; i ++){
                    System.out.print(reader.getString(i));
                    if ( i+1!=colCount){
                        System.out.print(",");
                    }
                }
                System.out.println("");
                row++;
            }
        } catch ( Exception ex){
            //System.out.println(ex.toString());
            ex.printStackTrace();
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
