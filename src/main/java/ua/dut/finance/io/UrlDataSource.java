package ua.dut.finance.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/** Джерело даних: віддалений URL. */
public class UrlDataSource implements DataSource {
    private final String url;

    public UrlDataSource(String url) {
        this.url = url;
    }

    @Override
    public InputStream openStream() throws IOException {
        return new URL(url).openStream();
    }

    @Override
    public String name() {
        return url;
    }
}
