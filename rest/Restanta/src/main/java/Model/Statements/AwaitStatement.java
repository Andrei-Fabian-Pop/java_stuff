package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ADT.Pair;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;

import java.util.List;
import java.util.concurrent.locks.Lock;

public class AwaitStatement implements IStatement {
    private final String variable;

    public AwaitStatement(String var) {
        this.variable = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Lock lock = state.getBarrierTable().getLock();
        lock.lock();
        try {
            Pair<Integer, List<Integer>> location = state.getBarrierTable().getBarrierTable().get(state.getBarrierTable().getLocation());
            if (!location.second.contains(state.id) && !state.getBarrierTable().full()) {
                location.second.add(state.id);
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
        if (!(typeTable.get(variable) instanceof IntType)) {
            throw new MyException("Variable %s is not of int type".formatted(variable));
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("Await{ var='%s' }", variable);
    }
}
