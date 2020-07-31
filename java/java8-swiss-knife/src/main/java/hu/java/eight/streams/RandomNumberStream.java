package hu.java.eight.streams;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

final class RandomNumberStream {
    public List<Double> generateFiveRandomNumbers() {
        final int limit = 5;
        return Stream.generate(Math::random)
                .limit(limit)
                .collect(toList());
    }
}
