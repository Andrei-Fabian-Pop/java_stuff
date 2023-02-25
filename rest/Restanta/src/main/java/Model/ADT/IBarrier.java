package Model.ADT;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public interface IBarrier {
    Map<Integer, Pair<Integer, List<Integer>>> getBarrierTable();
    Integer getLocation();
    Lock getLock();

    boolean full();
}
