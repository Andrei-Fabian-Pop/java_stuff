package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ADT.Pair;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLock implements IStatement {
    private final String var;

    public NewLock(String var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Lock lock = new ReentrantLock();
        int location = new Random().nextInt() % 10000;
        state.getSymTable().put(var, new IntValue(location));
        state.getLockTable().getLockTable().put(location, new Pair<>(lock, null));

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        if (!(typeTable.get(var) instanceof IntType)) {
            throw new MyException("var not int type");
        }
        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("Lock{%s}", var);
    }
}
