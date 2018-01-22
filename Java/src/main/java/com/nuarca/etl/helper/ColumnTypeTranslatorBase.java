//
// Translated by CS2J (http://www.cs2j.com): 12/6/2017 2:34:20 PM
//

package com.nuarca.etl.helper;

import CS2JNet.System.StringSupport;
import com.nuarca.etl.ColumnDefinition;
import com.nuarca.etl.helper.IColumnTypeTranslator;

import java.sql.JDBCType;
import java.sql.SQLType;

public abstract class ColumnTypeTranslatorBase   implements IColumnTypeTranslator
{
    public String getDataTypeExpression(ColumnDefinition dataType) throws Exception {

        JDBCType jdbdType = dataType.getDataType();

        if (jdbdType.equals(JDBCType.VARCHAR))
        {
            return this.getAnsiStringExpression(dataType);
        }
        else if (jdbdType.equals(JDBCType.CHAR))
        {
            return this.getAnsiStringFixedLengthExpression(dataType);
        }
        else if (jdbdType.equals(JDBCType.BINARY))
        {
            return this.getBinaryExpression(dataType);
        }
        else if (jdbdType.equals(JDBCType.BOOLEAN))
        {
            return this.getBooleanExpression(dataType);
        }
        //else if (jdbdType.equals(JDBCType))
        //{
        //    return this.GetByteExpression(dataType);
        //}
        //else if (jdbdType.equals(JDBCType.DECIMAL))
        //{
        //    return this.GetCurrencyExpression(dataType);
        //}
        else if (jdbdType.equals(JDBCType.DATE))
        {
            return this.getDateTimeExpression(dataType);
        }
        else if (jdbdType.equals(JDBCType.DECIMAL))
        {
            return this.getDecimalExpression(dataType);
        }
        else if (jdbdType.equals(JDBCType.DOUBLE))
        {
            return this.getDoubleExpression(dataType);
        }
        //else if (jdbdType.equals(DbType.Guid))
        //{
         //   return this.GetGuidExpression(dataType);
        //}
        else if (jdbdType.equals(JDBCType.SMALLINT))
        {
            return this.getInt16Expression(dataType);
        }
        else if (jdbdType.equals(JDBCType.INTEGER))
        {
            return this.getInt32Expression(dataType);
        }
        else if (jdbdType.equals(JDBCType.BIGINT))
        {
            return this.getInt64Expression(dataType);
        }
        //else if (jdbdType.equals(DbType.))
        //{
        //    return this.GetSingleExpression(dataType);
        //}
        else if (jdbdType.equals(JDBCType.VARCHAR))
        {
            return this.getStringExpression(dataType);
        }
        else if (jdbdType.equals(JDBCType.NVARCHAR))
        {
            return this.getStringExpression(dataType);
        }

        else if (jdbdType.equals(JDBCType.CHAR))
        {
            return this.getStringFixedLengthExpression(dataType);
        }
        else if (jdbdType.equals(JDBCType.NCHAR))
        {
            return this.getStringFixedLengthExpression(dataType);
        }
        else if (jdbdType.equals(JDBCType.SMALLINT))
        {
            return this.getUInt16Expression(dataType);
        }
        else if (jdbdType.equals(JDBCType.INTEGER))
        {
            return this.getUInt32Expression(dataType);
        }
        else if (jdbdType.equals(JDBCType.BIGINT))
        {
            return this.getUInt64Expression(dataType);
        }
                           
        throw new Exception("Data type not supported");
    }

    public String getNextStatementIdentifier() throws Exception {
        return System.getProperty("line.separator");
    }

    public String getGuidExpression(ColumnDefinition dataType) throws Exception {
        return pGetStringExpression("UNIQUEIDENTIFIER",0,dataType.getIsNullable(),null);
    }

    public String getAnsiStringExpression(ColumnDefinition dataType) throws Exception {
        if (dataType.getLength() > 0)
        {
            return this.pGetStringExpression("VARCHAR",dataType.getLength(),dataType.getIsNullable(),dataType.getDefaultValue() instanceof String ? (String)dataType.getDefaultValue() : (String)null,true,dataType.getIsIdentity(),false,dataType.getIsUnique(),dataType.getIsLong());
        }
        else
        {
            return this.pGetStringExpression("TEXT",0,dataType.getIsNullable(),dataType.getDefaultValue() instanceof String ? (String)dataType.getDefaultValue() : (String)null,true);
        } 
    }

    public String getAnsiStringFixedLengthExpression(ColumnDefinition dataType) throws Exception {
        return this.pGetStringExpression("CHAR",dataType.getLength(),dataType.getIsNullable(),dataType.getDefaultValue() instanceof String ? (String)dataType.getDefaultValue() : (String)null,true,dataType.getIsIdentity(),dataType.getIsPrimary());
    }

    public String getBinaryExpression(ColumnDefinition dataType) throws Exception {
        return this.pGetStringExpression("BINARY", dataType.getLength(), dataType.getIsNullable(), Convert.ToString(dataType.getDefaultValue()));
    }

    public String getBooleanExpression(ColumnDefinition dataType) throws Exception {
        if (dataType.getDefaultValue() == null)
        {
            return this.pGetStringExpression("BIT",0,dataType.getIsNullable(),null);
        }
        else
        {
            if ((Boolean)dataType.getDefaultValue() == true)
            {
                return this.pGetStringExpression("BIT",0,dataType.getIsNullable(),"1");
            }
            else
            {
                return this.pGetStringExpression("BIT",0,dataType.getIsNullable(),"0");
            } 
        } 
    }

    public String getByteExpression(ColumnDefinition dataType) throws Exception {
        return this.pGetStringExpression("IMAGE",0,dataType.getIsNullable(),null);
    }

    public String getCurrencyExpression(ColumnDefinition dataType) throws Exception {
        return this.pGetStringExpression("MONEY", 0, dataType.getIsNullable(), Convert.ToString(dataType.getDefaultValue()));
    }

    public String getDateTimeExpression(ColumnDefinition dataType) throws Exception {
        return this.pGetStringExpression("DATETIME", 0, dataType.getIsNullable(), Convert.ToString(dataType.getDefaultValue()), true);
    }

    public String getDecimalExpression(ColumnDefinition dataType) throws Exception {
        return pGetStringExpression("DECIMAL",dataType.getPrecision(),dataType.getScale(),dataType.getIsNullable(),Convert.ToString(dataType.getDefaultValue()));
    }

    public String getDoubleExpression(ColumnDefinition dataType) throws Exception {
        return this.pGetStringExpression("REAL", 0, dataType.getIsNullable(), Convert.ToString(dataType.getDefaultValue()), false, dataType.getIsIdentity(), dataType.getIsPrimary());
    }

    public String getInt16Expression(ColumnDefinition dataType) throws Exception {
        return this.pGetStringExpression("SMALLINT", 0, dataType.getIsNullable(), Convert.ToString(dataType.getDefaultValue()), false, dataType.getIsIdentity(), dataType.getIsPrimary());
    }

    public String getInt32Expression(ColumnDefinition dataType) throws Exception {
        return this.pGetStringExpression("INT", 0, dataType.getIsNullable(), Convert.ToString(dataType.getDefaultValue()), false, dataType.getIsIdentity(), dataType.getIsPrimary());
    }

    public String getInt64Expression(ColumnDefinition dataType) throws Exception {
        return this.pGetStringExpression("BIGINT", 0, dataType.getIsNullable(), Convert.ToString(dataType.getDefaultValue()), false, dataType.getIsIdentity(), dataType.getIsPrimary());
    }

    public String getSingleExpression(ColumnDefinition dataType) throws Exception {
        return this.pGetStringExpression("SINGLE", 0, dataType.getIsNullable(), Convert.ToString(dataType.getDefaultValue()), false, dataType.getIsIdentity(), dataType.getIsPrimary());
    }

    public String getStringExpression(ColumnDefinition dataType) throws Exception {
        if (dataType.getLength() > 0)
        {
            return this.pGetStringExpression("NVARCHAR",dataType.getLength(),dataType.getIsNullable(),dataType.getDefaultValue() instanceof String ? (String)dataType.getDefaultValue() : (String)null,true,dataType.getIsIdentity(),dataType.getIsPrimary());
        }
        else
        {
            return this.pGetStringExpression("NTEXT",0,dataType.getIsNullable(),dataType.getDefaultValue() instanceof String ? (String)dataType.getDefaultValue() : (String)null,true,dataType.getIsIdentity(),dataType.getIsPrimary());
        } 
    }

    public String getStringFixedLengthExpression(ColumnDefinition dataType) throws Exception {
        return this.pGetStringExpression("NCHAR",dataType.getLength(),dataType.getIsNullable(),dataType.getDefaultValue() instanceof String ? (String)dataType.getDefaultValue() : (String)null,true,dataType.getIsIdentity(),dataType.getIsPrimary());
    }

    public String getUInt16Expression(ColumnDefinition dataType) throws Exception {
        return this.getInt16Expression(dataType);
    }

    public String getUInt32Expression(ColumnDefinition dataType) throws Exception {
        return this.getInt32Expression(dataType);
    }

    public String getUInt64Expression(ColumnDefinition dataType) throws Exception {
        return this.getInt64Expression(dataType);
    }

    public abstract char getOpeningIdentifier() throws Exception ;

    public abstract char getClosingIdentifier() throws Exception ;

    public abstract char getParameterIdentifier() throws Exception ;

    public abstract char getParameterPlaceholderIdentifier() throws Exception ;

    private String pGetStringExpression(String typeName, int length, boolean isNullable, String defaultValue) throws Exception {
        return this.pGetStringExpression(typeName,length,isNullable,defaultValue,false,false,false);
    }

    private String pGetStringExpression(String typeName, int length, boolean isNullable, boolean isPrimary, String defaultValue) throws Exception {
        return this.pGetStringExpression(typeName,length,isNullable,defaultValue,false,false,isPrimary);
    }

    private String pGetStringExpression(String typeName, int length, boolean isNullable, String defaultValue, boolean needsQuote) throws Exception {
        return this.pGetStringExpression(typeName,length,isNullable,defaultValue,needsQuote,false,false);
    }

    private String pGetStringExpression(String typeName, int length, boolean isNullable, String defaultValue, boolean needsQuote, boolean isIdentity, boolean isPrimary) throws Exception {
        return pGetStringExpression(typeName,length,isNullable,defaultValue,needsQuote,isIdentity,isPrimary,false,false);
    }

    public String getIdentityExpression() throws Exception {
        return "IDENTITY";
    }

    public String getDefaultExpression(String defaultValue, boolean needsQuote) throws Exception {
        String quoteString = "";
        if (!StringSupport.isNullOrEmpty(defaultValue))
        {
            if (needsQuote)
            {
                quoteString = "'";
            }
             
            String defaultExpression = String.format(StringSupport.CSFmtStrToJFmtStr("DEFAULT ({1}{0}{1})"),defaultValue,quoteString);
            return defaultExpression;
        }
        else
        {
            return "";
        } 
    }

    private String pGetStringExpression(String typeName, int length, boolean isNullable, String defaultValue, boolean needsQuote, boolean isIdentity, boolean isPrimary, boolean unique, boolean isLong) throws Exception {
        String lengthExpression = "";
        String nullExpression = "NULL";
        String defaultExpression = "";
        String quoteString = "";
        String uniqueString = "";
        if (needsQuote)
        {
            quoteString = "'";
        }
         
        if (!isIdentity)
        {
            if (defaultValue != null)
            {
                if (defaultValue.length() > 0)
                {
                    defaultExpression = getDefaultExpression(defaultValue,needsQuote);
                }
                 
            }
             
        }
        else
        {
            //if (!isPrimary)
            //{
            defaultExpression = getIdentityExpression();
        } 
        //}
        //else
        //{
        //    defaultExpression = string.Format( "PRIMARY KEY {0}", IdentityExpression);
        //}
        if (isLong)
        {
            lengthExpression = "(MAX)";
        }
        else if (length > 0)
        {
            lengthExpression = String.format(StringSupport.CSFmtStrToJFmtStr("({0})"),length);
        }
          
        if (!isNullable)
        {
            nullExpression = "NOT NULL";
        }
         
        if (unique)
        {
            uniqueString = "UNIQUE";
        }
         
        char openingIdentifier = this.getOpeningIdentifier();
        char closingIdentifier = this.getClosingIdentifier();
        if (!getSupportsIdentifierAroundTypename())
        {
            openingIdentifier = ' ';
            closingIdentifier = ' ';
        }
         
        return String.format("%1$s%2$s%3$s %4$s %5$s %6$s%7$s", openingIdentifier, typeName, closingIdentifier, lengthExpression, nullExpression, defaultExpression, uniqueString);
    }

    public boolean getSupportsIdentifierAroundTypename() throws Exception {
        return true;
    }

    private String pGetStringExpression(String typeName, int precison, int scale, boolean isNullable, Object defaultValue) throws Exception {
        String nullExpression = "NULL";
        String defaultValueString = defaultValue.toString();
        String defaultExpression = getDefaultExpression(defaultValueString,false);
        //string defaultString = string.Empty;
        if (!isNullable)
        {
            nullExpression = "NOT NULL";
        }
         
        char openingIdentifier = this.getOpeningIdentifier();
        char closingIdentifier = this.getClosingIdentifier();
        if (!getSupportsIdentifierAroundTypename())
        {
            openingIdentifier = ' ';
            closingIdentifier = ' ';
        }
         
        return String.format("%1$s%2$s%3$s (%4$s,%5$s) %6$s %7$s", openingIdentifier, typeName, closingIdentifier, precison, scale, nullExpression, defaultExpression);
    }

    public JDBCType getDbTypeFromSystemTypeString(String systemType) throws Exception {
        String __dummyScrutVar1 = systemType;
        if (__dummyScrutVar1.equals("System.String"))
        {
            return JDBCType.VARCHAR;
        }
        else if (__dummyScrutVar1.equals("System.Char"))
        {
            return JDBCType.CHAR;
        }
        else if (__dummyScrutVar1.equals("System.Int32"))
        {
            return JDBCType.INTEGER;
        }
        else if (__dummyScrutVar1.equals("System.Int64"))
        {
            return JDBCType.BIGINT;
        }
        else if (__dummyScrutVar1.equals("System.Decimal"))
        {
            return JDBCType.DOUBLE;
        }
        else if (__dummyScrutVar1.equals("System.Double"))
        {
            return JDBCType.DOUBLE;
        }
        else if (__dummyScrutVar1.equals("System.DateTime"))
        {
            return JDBCType.DATE;
        }
        else if (__dummyScrutVar1.equals("System.int16"))
        {
            return JDBCType.SMALLINT;
        }
        else if (__dummyScrutVar1.equals("System.Byte[]"))
        {
            return JDBCType.BINARY;
        }
        else if (__dummyScrutVar1.equals("System.Guid"))
        {
            return JDBCType.BINARY;
        }
        else if (__dummyScrutVar1.equals("System.Boolean"))
        {
            return JDBCType.BOOLEAN;
        }
                   
        return JDBCType.VARCHAR;
    }

}


