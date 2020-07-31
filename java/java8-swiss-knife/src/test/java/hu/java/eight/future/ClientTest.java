package hu.java.eight.future;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.MessageFormat;
import java.util.concurrent.Future;

public final class ClientTest {
    private static final int MILLION = 1_000_000;
    private Client underTest;

    @DataProvider(name = "products")
    public Object[][] createProducts() {
        return new Object[][]{
                {"MyPhone27s"},
                {"My favorite product"},
        };
    }

    @BeforeMethod
    public void setUp() {
        this.underTest = new Client();
    }

    @Test(dataProvider = "products")
    public void shouldFindPrices(final String product) {
        System.out.println("Get price from all shops");
        final long start = System.nanoTime();

        System.out.println(underTest.findPrices(product));

        final long duration = ((System.nanoTime() - start) / MILLION);
        System.out.println(MessageFormat.format("Done in {0} ms", duration));
    }

    @Test(dataProvider = "products")
    public void shouldGetPriceFromFirstShop(final String product) {
        System.out.println("Get price from the first shop");
        final long start = System.nanoTime();

        final Future<Double> futurePrice = this.underTest.getPriceFromFirstShop(product);

        final long invocationTime = ((System.nanoTime() - start) / MILLION);
        System.out.println(MessageFormat.format("Invocation returned after {0} ms", invocationTime));

        try {
            double price = futurePrice.get();
            System.out.println(MessageFormat.format("Price is: {0}", price));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final long retrievalTime = ((System.nanoTime() - start) / MILLION);

        System.out.println(MessageFormat.format("Price returned after {0} ms", retrievalTime));
    }
}
