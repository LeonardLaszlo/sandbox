package hu.java.eight.threading;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.MessageFormat;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;

public final class ThreadSafeCounterTest {
    private static final Logger LOGGER = Logger.getLogger(ThreadSafeCounterTest.class.getName());
    private static final Level LOG_LEVEL = Level.FINE;
    private static final String CURRENT_THREAD_ID_MESSAGE_TEMPLATE = "Current thread id: {0}";

    private ThreadSafeCounter underTest;

    @BeforeMethod
    public void setUp() {
        this.underTest = new ThreadSafeCounter();
    }

    @Test
    public void shouldExecuteThreadSafeOperation() throws InterruptedException {
        final Supplier<String> msgSupplier = () ->
                MessageFormat.format(CURRENT_THREAD_ID_MESSAGE_TEMPLATE, Thread.currentThread().getId());

        LOGGER.log(LOG_LEVEL, msgSupplier);

        this.underTest.increment();

        final Thread t = new Thread(() -> {
            LOGGER.log(LOG_LEVEL, msgSupplier);
            this.underTest.increment();
        });

        t.start();
        t.join();

        assertEquals(this.underTest.getCount(), 2, "Counter value should be 2");
    }
}
