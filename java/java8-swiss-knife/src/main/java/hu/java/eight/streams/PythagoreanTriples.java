package hu.java.eight.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class PythagoreanTriples {
    public List<int[]> generatePythagoreanTriples() {
        final List<int[]> result = new ArrayList<>();
        final int startInclusive = 1;
        final int endInclusive = 100;
        final int limit = 5;

        final Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(startInclusive, endInclusive)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, endInclusive)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(t -> t[2] % 1.0 == 0)
                        .map(t -> new int[]{(int) t[0], (int) t[1], (int) t[2]})
                );

        pythagoreanTriples.limit(limit)
                .forEach(result::add);

        return result;
    }
}
