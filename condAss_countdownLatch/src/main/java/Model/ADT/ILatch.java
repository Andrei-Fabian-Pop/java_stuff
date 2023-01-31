package Model.ADT;

import java.util.Map;
import java.util.concurrent.locks.Lock;

public interface ILatch {
    Map<Integer, Integer> getLatchTable();
    Integer getLocation();
    Lock getLock();
    void CountDown();
}
