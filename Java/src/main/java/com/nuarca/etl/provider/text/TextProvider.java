package com.nuarca.etl.provider.text;

//
// Translated by CS2J (http://www.cs2j.com): 12/6/2017 2:34:22 PM
//


import CS2JNet.System.NotImplementedException;
import com.nuarca.etl.provider.IDataLink;
import com.nuarca.etl.provider.ILinkProvider;
import com.nuarca.etl.provider.ILinkReader;
import com.nuarca.etl.provider.ILinkWriter;
import com.nuarca.etl.provider.text.TextLink;
import com.nuarca.etl.provider.text.TextReader;

public class TextProvider   implements ILinkProvider
{
    public IDataLink createLink(String connectionString) throws Exception {
        TextLink tl = new TextLink();
        tl.initialize(connectionString);
        return tl;
    }

    public ILinkReader createReader(IDataLink link, String query) throws Exception {
        // Query is irrevalent - the text file name is in the query string
        TextReader reader = new TextReader();
        reader.initialize(link);
        return reader;
    }

    public ILinkWriter createWriter(IDataLink link, String table) throws Exception {
        throw new NotImplementedException();
    }

}


