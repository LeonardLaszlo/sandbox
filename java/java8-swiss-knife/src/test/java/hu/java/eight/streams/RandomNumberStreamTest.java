package hu.java.eight.streams;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

public final class RandomNumberStreamTest {
    private RandomNumberStream underTest;

    @BeforeMethod
    public void setUp() {
        this.underTest = new RandomNumberStream();
    }

    @Test
    public void shouldGenerateFiveRandomNumbers() {
        List<Double> actualOutput = this.underTest.generateFiveRandomNumbers();
        actualOutput.forEach(i -> assertTrue(i >= 0 && i <= 1));
    }
}
