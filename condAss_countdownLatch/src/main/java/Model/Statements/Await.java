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
        Lock lock = state.getLatchTable().getLock();
        lock.lock();
        try {
            Values value = state.getSymTable().get(var);
            if (value == null) {
                throw new MyException("value not in symtable");
            }

            if (state.getLatchTable().getLatchTable().get(state.getLatchTable().getLocation()) != 0) {
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
            throw new MyException("var is not int type");
        }
        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("Await(%s)", var);
    }
}
