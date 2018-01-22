//
// Translated by CS2J (http://www.cs2j.com): 12/6/2017 2:34:20 PM
//

package com.nuarca.etl.helper;

import com.nuarca.etl.ColumnDefinition;

import java.sql.SQLType;

public interface IColumnTypeTranslator   
{
    SQLType getDbTypeFromSystemTypeString(String systemType) throws Exception ;

    String getDataTypeExpression(ColumnDefinition dataType) throws Exception ;

    String getAnsiStringExpression(ColumnDefinition dataType) throws Exception ;

    String getAnsiStringFixedLengthExpression(ColumnDefinition dataType) throws Exception ;

    String getBinaryExpression(ColumnDefinition dataType) throws Exception ;

    String getBooleanExpression(ColumnDefinition dataType) throws Exception ;

    String getByteExpression(ColumnDefinition dataType) throws Exception ;

    String getCurrencyExpression(ColumnDefinition dataType) throws Exception ;

    String getDateTimeExpression(ColumnDefinition dataType) throws Exception ;

    String getDecimalExpression(ColumnDefinition dataType) throws Exception ;

    String getDoubleExpression(ColumnDefinition dataType) throws Exception ;

    String getInt16Expression(ColumnDefinition dataType) throws Exception ;

    String getInt32Expression(ColumnDefinition dataType) throws Exception ;

    String getInt64Expression(ColumnDefinition dataType) throws Exception ;

    String getSingleExpression(ColumnDefinition dataType) throws Exception ;

    String getStringExpression(ColumnDefinition dataType) throws Exception ;

    String getStringFixedLengthExpression(ColumnDefinition dataType) throws Exception ;

    String getUInt16Expression(ColumnDefinition dataType) throws Exception ;

    String getUInt32Expression(ColumnDefinition dataType) throws Exception ;

    String getUInt64Expression(ColumnDefinition dataType) throws Exception ;

    String getGuidExpression(ColumnDefinition dataType) throws Exception ;

    char getOpeningIdentifier() throws Exception ;

    char getClosingIdentifier() throws Exception ;

    char getParameterIdentifier() throws Exception ;

    char getParameterPlaceholderIdentifier() throws Exception ;

    String getNextStatementIdentifier() throws Exception ;

    String getIdentityExpression() throws Exception ;

    boolean getSupportsIdentifierAroundTypename() throws Exception ;

    String getDefaultExpression(String defaultValue, boolean needsQuote) throws Exception ;

}


