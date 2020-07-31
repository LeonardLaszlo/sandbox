package hu.java.eight.streams;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public final class PrimePartitionTest {
    private PrimePartition underTest;

    @BeforeMethod
    public void setUp() {
        this.underTest = new PrimePartition();
    }

    @Test
    public void shouldPartitionPrimesLessThanEleven() {
        final List<Integer> primes = Arrays.asList(2, 3, 5, 7);
        final List<Integer> nonPrimes = Arrays.asList(4, 6, 8, 9, 10);

        final Map<Boolean, List<Integer>> result = this.underTest.partitionPrimesLessThanEleven();

        assertEquals(result.get(true), primes);
        assertEquals(result.get(false), nonPrimes);
    }
}
