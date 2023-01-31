package Model.ADT;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLatch implements ILatch {
    private final Map<Integer, Integer> latchTable;
    private final Integer location;
    private final static Lock lock = new ReentrantLock();

    public MyLatch() {
        this.latchTable = new HashMap<>();
        this.location = new Random().nextInt() % 10000;
    }

    @Override
    public Map<Integer, Integer> getLatchTable() {
        return latchTable;
    }

    @Override
    public Integer getLocation() {
        return location;
    }

    @Override
    public Lock getLock() {
        return lock;
    }

    @Override
    public void CountDown() {
        latchTable.put(location, latchTable.get(location) - 1);
    }
}
