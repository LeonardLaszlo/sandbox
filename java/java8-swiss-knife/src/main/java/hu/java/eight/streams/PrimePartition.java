package hu.java.eight.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;

final class PrimePartition {
    private static boolean isPrime(final int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    public Map<Boolean, List<Integer>> partitionPrimesLessThanEleven() {
        final int limit = 10;
        return IntStream.rangeClosed(2, limit)
                .boxed()
                .collect(partitioningBy(PrimePartition::isPrime));
    }
}
