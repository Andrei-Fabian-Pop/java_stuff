package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ADT.Pair;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Release implements IStatement {
    private final String var;

    public Release(String var) {
        this.var = var;
    }


    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Lock lock = state.getSemaphore().getLock();
        lock.lock();
        try {
            IntValue value = (IntValue) state.getSymTable().get(var);
            if (value == null) {
                throw new MyException("value not found");
            }
            state.getSemaphore().getSemaphoreTable().get(state.getSemaphore().getSemaphoreLocation()).second.remove(state.id);
            state.getSemaphore().getSemaphoreTable().put(state.getSemaphore().getSemaphoreLocation(),
                    new Pair<>(
                            state.getSemaphore().getSemaphoreTable().get(state.getSemaphore().getSemaphoreLocation()).first,
                            state.getSemaphore().getSemaphoreTable().get(state.getSemaphore().getSemaphoreLocation()).second)
            );

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
            throw new MyException("Var not declared");
        }

        if (!(typeTable.get(var) instanceof IntType)) {
            throw new MyException("Var not of type int");
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("release(%s)", var);
    }
}
