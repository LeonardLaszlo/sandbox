package hu.java.eight.exercise;

import hu.java.eight.exercise.domain.Trader;
import hu.java.eight.exercise.domain.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

final class Exercise {
    public List<Transaction> findAllTransactions(final List<Transaction> transactions, final int year) {
        return transactions.stream()
                .filter(t -> t.getYear() == year)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());
    }

    public List<String> findAllCities(final List<Transaction> transactions) {
        return transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(toList());
    }

    public List<Trader> findTradersInCambridge(final List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getTrader)
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());
    }

    public String findAllTraders(final List<Transaction> transactions) {
        return transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .reduce((a, b) -> a + " " + b)
                .orElse("");
    }

    public boolean tellIfThereAreAnyTradersBasedInMilan(final List<Transaction> transactions) {
        return transactions.stream()
                .anyMatch(t -> "Milan".equals(t.getTrader().getCity()));
    }

    public List<Integer> printTransactionValuesFromTradersLivingInCambridge(final List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue)
                .collect(toList());
    }

    public int findTheHighestValueOfAllTransactions(final List<Transaction> transactions) {
        return transactions.parallelStream()
                .map(Transaction::getValue)
                .reduce(Integer::max)
                .orElse(0);
    }

    public Optional<Transaction> findTheTransactionWithTheSmallestValue(final List<Transaction> transactions) {
        return transactions.parallelStream()
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
    }

    public Map<Integer, Map<String, List<Transaction>>> groupByYearAndTrader(final List<Transaction> transactions) {
        return transactions.stream()
                .collect(groupingBy(Transaction::getYear, groupingBy(t -> t.getTrader().getName())));
    }
}
