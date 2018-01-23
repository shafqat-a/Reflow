//
// Translated by CS2J (http://www.cs2j.com): 12/6/2017 2:34:22 PM
//

package com.nuarca.etl.tasks;

import CS2JNet.System.StringSupport;
import com.nuarca.etl.ColumnMap;
import com.nuarca.etl.ColumnMappings;
import com.nuarca.etl.provider.ILinkReader;
import com.nuarca.etl.provider.ILinkWriter;
import com.nuarca.etl.tasks.TaskBase;
import com.nuarca.etl.tasks.TaskExecutionContext;
import com.nuarca.etl.tasks.TaskResult;
import com.nuarca.etl.transformation.TransformedReader;

public class DataFlowTask  extends TaskBase 
{
    private ILinkReader __Input;
    public ILinkReader getInput() {
        return __Input;
    }

    public void setInput(ILinkReader value) {
        __Input = value;
    }

    private ILinkWriter __Output;
    public ILinkWriter getOutput() {
        return __Output;
    }

    public void setOutput(ILinkWriter value) {
        __Output = value;
    }

    private ColumnMappings __Mapping;
    public ColumnMappings getMapping() {
        return __Mapping;
    }

    public void setMapping(ColumnMappings value) {
        __Mapping = value;
    }

    private boolean __IsAutoMap;
    public boolean getIsAutoMap() {
        return __IsAutoMap;
    }

    public void setIsAutoMap(boolean value) {
        __IsAutoMap = value;
    }

    private String __TableName;
    public String getTableName() {
        return __TableName;
    }

    public void setTableName(String value) {
        __TableName = value;
    }

    public DataFlowTask() throws Exception {
        this.setMapping(new ColumnMappings());
    }

    public TaskResult onExecute(TaskExecutionContext context) throws Exception {
        getInput().open();
        TransformedReader reader = new TransformedReader(getInput().getReader());
        reader.setMaps(this.getMapping());
        reader.setScriptingLanguage("VBScript");
        reader.setTransformationScript(buildScript());
        reader.init();
        getOutput().write(reader,getTableName(),context);
        return null;
    }

    private String buildScript() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Object __dummyForeachVar0 : this.getMapping())
        {
            ColumnMap map = (ColumnMap)__dummyForeachVar0;
            if (StringSupport.equals(map.getSourceColumn(), map.getDestination()))
            {
            }
            else // skip;
            if (map.getTransformExpression() != null)
            {
                if (!StringSupport.isNullOrEmpty(map.getDestination()))
                {
                    sb.append((String.format(StringSupport.CSFmtStrToJFmtStr("Record.Item(\"{0}\") = {1}"),map.getDestination(),map.getTransformExpression().getCode())) + System.getProperty("line.separator"));
                }
                 
            }
              
        }
        return sb.toString();
    }

}


