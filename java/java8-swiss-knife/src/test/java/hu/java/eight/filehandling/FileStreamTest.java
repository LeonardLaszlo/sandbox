package hu.java.eight.filehandling;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public final class FileStreamTest {
    private static final String FILE_NAME = "/data.txt";
    private static final String NON_EXISTENT_FILE_NAME = "/some_data.txt";

    private FileStream underTest;

    @BeforeMethod
    public void setUp() {
        this.underTest = new FileStream();
    }

    @Test
    public void shouldCountUniqueWordsFromFile() throws Exception {
        final long expectedResult = 3L;
        final long actualResult = this.underTest.countUniqueWordsFromFile(FILE_NAME);

        assertEquals(actualResult, expectedResult);
    }

    @Test(expectedExceptions = IOException.class)
    public void shouldFailReadingNonExistentFile() throws Exception {
        final long expectedResult = 3L;
        final long actualResult = this.underTest.countUniqueWordsFromFile(NON_EXISTENT_FILE_NAME);

        assertEquals(actualResult, expectedResult);
    }
}
