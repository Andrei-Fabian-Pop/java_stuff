package Model.ADT;

import Model.Values.Values;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public interface ISemaphore {
    Map<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable();
    Integer getSemaphoreLocation();
    Lock getLock();
}
