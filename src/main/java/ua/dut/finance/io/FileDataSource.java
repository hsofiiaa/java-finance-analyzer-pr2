package ua.dut.finance.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class FileDataSource implements DataSource {
    private final Path path;

    public FileDataSource(Path path) {
        this.path = path;
    }

    @Override
    public InputStream openStream() throws IOException {
        return new FileInputStream(path.toFile());
    }

    @Override
    public String name() {
        return path.toString();
    }
}
