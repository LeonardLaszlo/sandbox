package hu.java.eight.streams;

import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

final class StringUtility {

    // Pass method as parameter in Java 8
    public List<String> sortStringsByLength(final List<String> list) {
        list.sort(comparing(String::length));

        return list;
    }

    // Multi-thread filtering in Java 8
    public List<String> filterHigherThan(final int lowerLengthBound, final List<String> input) {
        return input.parallelStream()
                .filter((String word) -> word.length() > lowerLengthBound)
                .collect(toList());
    }

    // Sort strings with specific comparator in Java 8
    public List<String> sortStringsIgnoringCases(final List<String> input) {
        input.sort(String::compareToIgnoreCase);
        return input;
    }

    // Sort strings with natural comparator in Java 8
    public List<String> sortStringsWithNaturalComparator(final List<String> input) {
        input.sort(Comparator.naturalOrder());
        return input;
    }

    // Map-reduce example in Java 8
    public int sumLengthOfStrings(final List<String> input) {
        return input.stream()
                .map(String::length)
                .reduce(0, (x, y) -> x + y);
    }
}
