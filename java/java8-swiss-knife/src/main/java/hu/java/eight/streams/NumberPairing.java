package hu.java.eight.streams;

import java.util.List;

import static java.util.stream.Collectors.toList;

final class NumberPairing {
    public List<Integer[]> pairNumbers(final List<Integer> numbers, final List<Integer> otherNumbers) {
        return numbers.stream()
                .flatMap(i -> otherNumbers.stream()
                        .map(j -> new Integer[]{i, j}))
                .collect(toList());
    }
}
