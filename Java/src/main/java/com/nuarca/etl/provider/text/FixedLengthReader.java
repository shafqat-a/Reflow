package com.nuarca.etl.provider.text;

import java.io.InputStream;

public class FixedLengthReader extends DelimitedReader {
    public FixedLengthReader(InputStream stream) throws Exception {
        super(stream);
    }
}
