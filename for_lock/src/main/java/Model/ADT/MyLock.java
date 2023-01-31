package Model.ADT;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class MyLock implements ILock {
    private final Map<Integer, Pair<Lock, Integer>> lockTable;

    public MyLock() {
        this.lockTable = new HashMap<>();
    }

    @Override
    public Map<Integer, Pair<Lock, Integer>> getLockTable() {
        return lockTable;
    }

    @Override
    public Lock getLock(Integer integer) {
        return lockTable.get(integer).first;
    }
}
