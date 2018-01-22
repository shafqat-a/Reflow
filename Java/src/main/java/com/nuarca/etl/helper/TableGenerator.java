//
// Translated by CS2J (http://www.cs2j.com): 12/6/2017 2:34:21 PM
//

package com.nuarca.etl.helper;

import CS2JNet.System.StringSupport;
import CS2JNet.System.Text.StringBuilderSupport;
import com.nuarca.etl.ColumnDefinition;
import com.nuarca.etl.Helper.IColumnTypeTranslator;

public class TableGenerator   
{
    private IColumnTypeTranslator _translator = null;
    public TableGenerator(IColumnTypeTranslator translator) throws Exception {
        _translator = translator;
    }

    /**
    * Drops the table
    * 
    *  @param objectType 
    *  @return
    */
    public String generateTableDropScript(String tableName) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(StringSupport.CSFmtStrToJFmtStr(" DROP TABLE {0}{1}{2}"),_translator.getOpeningIdentifier(),tableName,_translator.getClosingIdentifier()));
        return sb.toString();
    }

    /**
    * Generates table creation script for a database
    * 
    *  @param objectType 
    *  @return
    */
    public String generateTableScript(String tableName, ColumnDefinition[] columns) throws Exception {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbAfterTable = new StringBuilder();
        StringBuilder sbBeforeTable = new StringBuilder();
        StringBuilder sbPostGeneration = new StringBuilder();
        sb.append(String.format(StringSupport.CSFmtStrToJFmtStr(" CREATE TABLE {0}{1}{2}"),_translator.getOpeningIdentifier(),tableName,_translator.getClosingIdentifier()));
        sb.append(" ( \n ");
        for (ColumnDefinition column : columns)
        {
            StringBuilder sbField = new StringBuilder();
            //ProcessColumnForConstraint(column, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
            String sql = GetCreateSQLForColumn(column);
            if (!sql.equals(""))
            {
                sb.append("\t");
                sb.append(sql);
                if (sbField.length() > 0)
                {
                    sb.append(" " + sbField.toString() + " ");
                }
                 
                sb.append(",\n");
            }
             
        }
        sb.delete(sb.length() - 2, (sb.length() - 2)+(1));
        // TODO : Later
        //ProcessTypeForConstraints(objectType, sbBeforeTable, null, sbAfterTable, sbPostGeneration);
        StringBuilder sbFinal = new StringBuilder();
        if (sbBeforeTable.length() > 0)
        {
            sbFinal.append(sbBeforeTable.toString());
        }
         
        sbFinal.append(sb.toString());
        if (sbAfterTable.length() > 0)
        {
            sbFinal.append(sbAfterTable.toString());
        }
         
        sbFinal.append(" ) \n");
        if (sbPostGeneration.length() > 0)
        {
            sbFinal.append(sbPostGeneration.toString());
        }
         
        return sbFinal.toString();
    }

    /*
            public void ProcessTypeForConstraints(Type typ, StringBuilder sbBeforeTable, StringBuilder sbField, StringBuilder sbAfterTable, StringBuilder sbPostGeneration)
            {
                ProcessTypeForConstraint<UniqueFieldsConstraintAttribute>(typ, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
                ProcessTypeForConstraint<IndexAttribute>(typ, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
            }
            public bool ProcessTypeForConstraint<T>(Type typ, StringBuilder sbBeforeTable, StringBuilder sbField, StringBuilder sbAfterTable, StringBuilder sbPostGeneration)
                where T : ConstraintAttribute
            {
                T constraint = (T)Util.CommonHelper.GetAttribute(typ, typeof(T));
                if (constraint != null)
                {
                    constraint.GenerateSql(_provider, typ, null, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
                    return true;
                }
                return false;
            }*/
    private void processColumnForConstraint(ColumnDefinition pinfo, StringBuilder sbBeforeTable, StringBuilder sbField, StringBuilder sbAfterTable, StringBuilder sbPostGeneration) throws Exception {
    }

    //ProcessColumnForConstraint<PrimaryKeyAttribute>(pinfo, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
    //ProcessColumnForConstraint<ForiegnKeyAttribute>(pinfo, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
    /**
    * Generate the constatn string
    * 
    *  @param pinfo 
    *  @param sbBeforeTable 
    *  @param sbField 
    *  @param sbAfterTable 
    *  @return
    */
    //private bool ProcessColumnForConstraint<T>(ColumnDefinition pinfo, StringBuilder sbBeforeTable, StringBuilder sbField, StringBuilder sbAfterTable, StringBuilder sbPostGeneration) where T : ConstraintAttribute
    //{
    // T constraint = (T)Util.CommonHelper.GetAttribute(pinfo, typeof(T));
    // if (constraint != null)
    // {
    //     constraint.GenerateSql(_provider, pinfo.DeclaringType, pinfo, sbBeforeTable, sbField, sbAfterTable, sbPostGeneration);
    //     return true;
    //}
    //    return false;
    //}
    /*
            private void GetExtraSqlForType(Type objectType, StringBuilder sb)
            {
                object[] attributes = objectType.GetCustomAttributes(typeof(UniqueFieldsConstraintAttribute), true);
                if (attributes.Length > 0)
                {
                    UniqueFieldsConstraintAttribute attrib = attributes[0] as UniqueFieldsConstraintAttribute;
                    string[] properties = attrib.Properties;
                    sb.Append("UNIQUE (");
                    ISqlDataTypeTranslator translator = _provider.GetDataTypeTranslator();
                    int i = 0;
                    foreach (string propertyName in properties)
                    {
                        PropertyInfo pinfo = objectType.GetProperty(propertyName);
                        string fieldName = propertyName;
                        if (i > 0)
                        {
                            sb.Append(",");
                        }
                        sb.Append(string.Format("{0}{1}{2}", translator.OpeningIdentifier, fieldName, translator.ClosingIdentifier));
                        i++;
                    }
                    sb.Append(")");
                }
            }*/

    private String getCreateSQLForColumn(ColumnDefinition pinfo) throws Exception {
        String columnName = String.format(StringSupport.CSFmtStrToJFmtStr("{0}{1}{2}"),_translator.getOpeningIdentifier(),pinfo.getColumnName(),_translator.getClosingIdentifier());
        return columnName + " " + _translator.GetDataTypeExpression(pinfo);
    }

}


//if (dta == null)
//{
//    dta = GetDefaultDataTypeProperty(pinfo);
//}
// return columnName + " " + dta.SqlTypeString;
/*
        private DataTypeAttribute GetDefaultDataTypeProperty(PropertyInfo pinfo)
        {
            StringBuilder sb = new StringBuilder();
            if (!
                    (
                    pinfo.PropertyType.IsPrimitive
                    | pinfo.PropertyType.Equals(typeof(string))
                    | pinfo.PropertyType.Equals(typeof(DateTime))
                    | pinfo.PropertyType.Equals(typeof(System.Guid))
                    )
                )
            {
                return new AnsiStringAttribute(2045);
            }
            else
            {
                switch (pinfo.PropertyType.FullName)
                {
                    case "System.String":
                        return new AnsiStringAttribute(2045);
                        break;
                    case "System.Char":
                        return new AnsiStringAttribute(100);
                        break;
                    case "System.Int32":
                        if (IsPrimary(pinfo))
                        {
                            return new Int32Attribute(true, false, true);
                        }
                        else
                        {
                            return new Int32Attribute();
                        }
                        break;
                    case "System.Int64":
                        if (IsPrimary(pinfo))
                        {
                            return new Int64Attribute(false, true, 0);
                        }
                        else
                        {
                            return new Int64Attribute();
                        }
                        break;
                    case "System.Decimal":
                        if (IsPrimary(pinfo))
                        {
                            return new DecimalAttribute(15, 0);
                        }
                        else
                        {
                            return new DecimalAttribute(15, 3);
                        }
                        break;
                    case "System.Double":
                        throw new InvalidOperationException("Double value now allowed yet");
                        break;
                    case "System.DateTime":
                        return new DateTimeAttribute(true);
                        break;
                    case "System.int64":
                        return new Int16Attribute();
                        break;
                    case "System.Byte[]":
                        return new BinaryAttribute(1024, true);
                        break;
                    case "System.Guid":
                        return new GuidAttribute(true);
                        break;
                    case "System.Boolean":
                        return new BooleanAttribute(true);
                        break;
                    default:
                        throw new InvalidOperationException("Data type not supported");
                }
            }
        }
        private bool IsPrimary(PropertyInfo pinfo)
        {
            PrimaryKeyAttribute pk = CommonHelper.GetAttribute(pinfo,
                typeof(PrimaryKeyAttribute)) as PrimaryKeyAttribute;
            if (pk == null)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
         */