package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.Values;

import java.util.concurrent.locks.Lock;

public class Await implements IStatement {
    private final String var;

    public Await(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Lock lock = state.getBarrierTable().getLock();
        lock.lock();
        try {
            if (!state.getBarrierTable().getBarrierTable().get(state.getBarrierTable().getLocation()).second.contains(state.id) && !state.getBarrierTable().full()) {
                state.getBarrierTable().getBarrierTable().get(state.getBarrierTable().getLocation()).second.add(state.id);
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
        if (!(typeTable.get(var) instanceof IntType)) {
            throw new MyException("Var is not int type");
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return "Await{" + "var='" + var + '\'' + '}';
    }
}
