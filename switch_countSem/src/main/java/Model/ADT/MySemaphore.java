package Model.ADT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MySemaphore implements ISemaphore {
    private final Map<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    private final Integer location;
    private final static Lock lock = new ReentrantLock();

    public MySemaphore() {
        this.semaphoreTable = new HashMap<>();
        location = new Random().nextInt() % 10000;
    }

    @Override
    public Integer getSemaphoreLocation() {
        return location;
    }

    @Override
    public Lock getLock() {
        return lock;
    }

    @Override
    public Map<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() {
        return this.semaphoreTable;
    }
}
