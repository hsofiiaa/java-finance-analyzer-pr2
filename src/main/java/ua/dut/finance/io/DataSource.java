package ua.dut.finance.io;

import java.io.IOException;
import java.io.InputStream;


public interface DataSource {
    InputStream openStream() throws IOException;
    String name();
}
