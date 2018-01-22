//
// Translated by CS2J (http://www.cs2j.com): 12/6/2017 2:34:21 PM
//

package com.nuarca.etl.provider.sqlserver;

import com.nuarca.etl.helper.ColumnTypeTranslatorBase;

public class SqlTypeTranslator  extends ColumnTypeTranslatorBase 
{
    public char getOpeningIdentifier() throws Exception {
        return '[';
    }

    public char getClosingIdentifier() throws Exception {
        return ']';
    }

    public char getParameterIdentifier() throws Exception {
        return '@';
    }

    public char getParameterPlaceholderIdentifier() throws Exception {
        return '?';
    }

}


