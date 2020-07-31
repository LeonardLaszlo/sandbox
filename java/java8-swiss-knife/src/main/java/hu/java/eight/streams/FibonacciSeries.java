package hu.java.eight.streams;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

final class FibonacciSeries {
    public List<Integer> generateFirstTenFibonacciNumbers() {
        final int limit = 10;

        return Stream.iterate(new int[]{1, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(limit)
                .map(t -> t[0])
                .collect(toList());
    }
}
