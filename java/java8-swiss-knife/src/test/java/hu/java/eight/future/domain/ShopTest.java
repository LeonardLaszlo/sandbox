package hu.java.eight.future.domain;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public final class ShopTest {
    private Shop underTest;

    @BeforeMethod
    public void setUp() {
        this.underTest = new Shop("BiShop");
    }

    @Test
    public void shouldInterruptCalculatePrice() throws InterruptedException {
        final Thread t = new Thread(() -> this.underTest.getPrice("Cartesian product"));

        t.start();

        if (!t.isInterrupted()) {
            t.interrupt();
        }

        t.join();

        assert t.getState().equals(Thread.State.TERMINATED);
    }
}
