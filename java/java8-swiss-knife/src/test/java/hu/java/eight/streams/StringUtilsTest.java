package hu.java.eight.streams;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public final class StringUtilsTest {
    private StringUtility underTest;

    @BeforeMethod
    public void setUp() {
        this.underTest = new StringUtility();
    }

    @Test
    public void shouldSortListOfStringsByLength() {
        final List<String> input = Arrays.asList("Cseresznye", "Alma", "Korte");
        final List<String> expectedOutput = Arrays.asList("Alma", "Korte", "Cseresznye");

        final List<String> actualOutput = this.underTest.sortStringsByLength(input);

        assertTrue(actualOutput.equals(expectedOutput), "Should sort list by length of contained strings");
    }

    @Test
    public void shouldSortListOfStringsIgnoringCases() {
        final List<String> input = Arrays.asList("cseresznye", "Alma", "Korte");
        final List<String> expectedOutput = Arrays.asList("Alma", "cseresznye", "Korte");

        final List<String> actualOutput = this.underTest.sortStringsIgnoringCases(input);

        assertTrue(actualOutput.equals(expectedOutput), "Should sort list of strings ignoring cases");
    }

    @Test
    public void shouldSortListOfStringsUsingNaturalComparator() {
        final List<String> input = Arrays.asList("cseresznye", "Korte", "alma");
        final List<String> expectedOutput = Arrays.asList("Korte", "alma", "cseresznye");

        final List<String> actualOutput = this.underTest.sortStringsWithNaturalComparator(input);

        assertTrue(actualOutput.equals(expectedOutput), "Should sort list of strings by natural order");
    }

    @Test
    public void shouldFilterListOfStrings() {
        final List<String> input = Arrays.asList("Cseresznye", "Alma", "Korte");
        final List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("Cseresznye");
        final int lowerLengthBound = 5;

        final List<String> actualOutput = this.underTest.filterHigherThan(lowerLengthBound, input);

        assertTrue(actualOutput.equals(expectedOutput), "Should filter elements shorter or long as the bound");
    }

    @Test
    public void shouldSumLengthOfStrings() {
        final List<String> input = Arrays.asList("Cseresznye", "Alma", "Korte");
        final int expectedOutput = 19;

        final int actualOutput = this.underTest.sumLengthOfStrings(input);

        assertEquals(actualOutput, expectedOutput, "Should filter elements shorter or long as the bound");
    }
}
