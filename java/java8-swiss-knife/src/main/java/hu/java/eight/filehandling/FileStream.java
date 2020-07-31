package hu.java.eight.filehandling;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;

final class FileStream {
    private static final String SEPARATOR = "\\s+";
    private static final String MESSAGE_TEMPLATE = "Could not find file: {0}";

    private final FileUtils fileUtility;

    FileStream() {
        this.fileUtility = new FileUtils();
    }

    public long countUniqueWordsFromFile(final String fileName) throws FileNotFoundException {
        final Optional<URL> url = Optional.ofNullable(FileStream.class.getResource(fileName));
        final URL fileUrl = url.orElseThrow(() -> {
            final String errorMessage = MessageFormat.format(MESSAGE_TEMPLATE, fileName);
            return new FileNotFoundException(errorMessage);
        });
        final URI fileUri = this.fileUtility.convertUrlToUri(fileUrl);

        return this.fileUtility.readLinesOfFile(fileUri).stream()
                .flatMap(line -> Arrays.stream(line.split(SEPARATOR)))
                .distinct()
                .count();
    }
}
