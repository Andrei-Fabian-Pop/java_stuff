package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ADT.Pair;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.IntValue;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Acquire implements IStatement {
    private final String var;

    public Acquire(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Lock lock = state.getSemaphore().getLock();
        lock.lock();
        try {
            IntValue value = (IntValue) state.getSymTable().get(var);
            if (value == null) {
                throw new MyException("uhhh");
            }
            List<Integer> threads = state.getSemaphore().getSemaphoreTable().get(value.getValue()).second;
            if (state.getSemaphore().getSemaphoreTable().get(state.getSemaphore().getSemaphoreLocation()).first > threads.size()) {
                if (threads.contains(state.id)) {
                    throw new MyException("Thread already in semaphore");
                }
                threads.add(state.id);
                state.getSemaphore().getSemaphoreTable().put(
                        state.getSemaphore().getSemaphoreLocation(),
                        new Pair<>(state.getSemaphore().getSemaphoreTable().get(state.getSemaphore().getSemaphoreLocation()).first, threads)
                );
            } else {
                state.getExecStack().push(this);
            }
        } catch (MyException e) {
            System.out.println(e.toString());
        } finally {
            lock.unlock();
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        if (!typeTable.containsKey(var)) {
            throw new MyException("honestly idk");
        }

        return null;
    }

    @Override
    public String toString() {
        return String.format("acquire(%s)", var);
    }
}
