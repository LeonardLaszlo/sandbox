package hu.java.eight.threading;

import java.util.concurrent.atomic.AtomicInteger;

final class ThreadSafeCounter {
    private final AtomicInteger count;

    ThreadSafeCounter() {
        this.count = new AtomicInteger(0);
    }

    public void increment() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}
