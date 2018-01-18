package com.nuarca.etl.provider;

public interface ILinkProvider {

    IDataLink createLink(String connectionString) throws Exception ;

    ILinkReader createReader(IDataLink link, String query) throws Exception ;

    ILinkWriter createWriter(IDataLink link, String table) throws Exception ;
}
