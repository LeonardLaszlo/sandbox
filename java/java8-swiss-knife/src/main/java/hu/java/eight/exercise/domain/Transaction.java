package hu.java.eight.exercise.domain;

public final class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;

    public Transaction(final Trader traderName, final int yearOfTransaction, final int valueOfTransaction) {
        this.trader = traderName;
        this.year = yearOfTransaction;
        this.value = valueOfTransaction;
    }

    public Trader getTrader() {
        return trader;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{" + this.trader + ", year: " + this.year + ", value: " + this.value + "}";
    }
}
