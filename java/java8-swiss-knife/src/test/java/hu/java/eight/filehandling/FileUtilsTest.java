package hu.java.eight.filehandling;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.Properties;

public final class FileUtilsTest {
    private static final String MESSAGE_TEMPLATE = "Could not find file: {0}";
    private FileUtils underTest;

    @BeforeMethod
    public void setUp() {
        this.underTest = new FileUtils();
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void testConvertUrlToUri() throws MalformedURLException, FileNotFoundException {
        final String badUrl = "http:";
        final URL fileUrl = new URL(badUrl);

        this.underTest.convertUrlToUri(fileUrl);
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void testReadLinesOfFile() throws URISyntaxException, IOException {
        final Properties properties = new Properties();
        final Optional<URL> url = Optional.ofNullable(FileUtilsTest.class.getResource("/test.properties"));
        final InputStream inputStream = url.orElseThrow(() -> {
            final String errorMessage = MessageFormat.format(MESSAGE_TEMPLATE, "test.properties");
            return new IOException(errorMessage);
        }).openStream();

        properties.load(inputStream);
        inputStream.close();

        final Path path = Paths.get(properties.getProperty("test.wrongFilePath"));
        final URI fileURI = path.toUri();

        this.underTest.readLinesOfFile(fileURI);
    }

}
