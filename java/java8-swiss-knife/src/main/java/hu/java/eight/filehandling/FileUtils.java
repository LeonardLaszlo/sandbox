package hu.java.eight.filehandling;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

final class FileUtils {
    private static final String MESSAGE_TEMPLATE = "Could not parse file path: {0}";

    URI convertUrlToUri(final URL fileUrl) throws FileNotFoundException {
        try {
            return fileUrl.toURI();
        } catch (URISyntaxException e) {
            final String errorMessage = MessageFormat.format(MESSAGE_TEMPLATE, fileUrl);
            throw new FileNotFoundException(errorMessage);
        }
    }

    List<String> readLinesOfFile(final URI fileUri) throws FileNotFoundException {
        try {
            final Stream<String> lines = Files.lines(Paths.get(fileUri), Charset.defaultCharset());
            return lines.collect(toList());
        } catch (IOException e) {
            final String errorMessage = MessageFormat.format(MESSAGE_TEMPLATE, fileUri.toString());
            throw new FileNotFoundException(errorMessage);
        }
    }
}
