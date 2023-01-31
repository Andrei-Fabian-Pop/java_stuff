package Model.ADT;

import java.util.Map;
import java.util.concurrent.locks.Lock;

public interface ILock {
    Map<Integer, Pair<Lock, Integer>> getLockTable();
    Lock getLock(Integer integer);
}
