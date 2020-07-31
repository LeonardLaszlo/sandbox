package hu.java.eight.future.domain;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public final class Shop {
    private static final long DELAY = 1000L;
    private static final Logger LOGGER = Logger.getLogger(Shop.class.getName());

    private final String name;

    public Shop(final String shopName) {
        this.name = shopName;
    }

    private static void delay() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            LOGGER.warning(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public Future<Double> getPriceAsync(final String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    public double getPrice(final String product) {
        return calculatePrice(product);
    }

    public String getName() {
        return name;
    }

    private double calculatePrice(final String product) {
        delay();
        return Math.random() * product.charAt(0) + product.charAt(1);
    }
}
