package hu.java.eight.exercise.domain;

public final class Trader {

    private final String city;
    private final String name;

    public Trader(final String cityName, final String traderName) {
        this.city = cityName;
        this.name = traderName;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Trader: " + this.name + " in " + this.city;
    }
}
