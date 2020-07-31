package hu.java.eight.future;

import hu.java.eight.future.domain.Shop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

final class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private final List<Shop> shops;
    private final Executor executor;

    Client() {
        this.shops = Arrays.asList(new Shop("BestPrice"),
                new Shop("LetsSaveBig"),
                new Shop("MyFavouriteShop"),
                new Shop("BiShop"),
                new Shop("BuyItAll"));
        this.executor = createExecutor(this.shops.size());
    }

    private Executor createExecutor(final int collectionSize) {
        final double cpuCores = Runtime.getRuntime().availableProcessors();
        final double targetCpuUtilization = 1.0; // between 0 and 1
        final double waitTimeToComputeTimeRatio = 24.0; // 4% computation
        final double numberOfThreads = cpuCores * targetCpuUtilization * (1 + waitTimeToComputeTimeRatio);
        return Executors.newFixedThreadPool(Math.min(collectionSize, (int) numberOfThreads), r -> {
            final Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
    }

    public Future<Double> getPriceFromFirstShop(final String product) {
        final Future<Double> futurePrice = shops.get(0).getPriceAsync(product);

        doSomethingElse();

        return futurePrice;
    }

    public List<String> findPrices(final String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() ->
                        String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)), executor
                ))
                .collect(toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    private void doSomethingElse() {
        LOGGER.info("Do something else...");
    }
}
