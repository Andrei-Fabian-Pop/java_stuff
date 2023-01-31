package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ADT.Pair;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;

import java.util.ArrayList;
import java.util.Locale;

public class NewBarrier implements IStatement {
    private final String var;
    private final Expression expression;

    public NewBarrier(String var, Expression expression) {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (state.getSymTable().get(var) == null) {
            throw new MyException("var not in sym table");
        }

        IntValue value = (IntValue) expression.eval(state.getSymTable(), state.getHeap());
        Integer location = state.getBarrierTable().getLocation();
        state.getSymTable().put(var, new IntValue(location));
        state.getBarrierTable().getBarrierTable().put(location, new Pair<>(value.getValue(), new ArrayList<>()));

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        if (!(typeTable.get(var) instanceof IntType)) {
            throw new MyException("var not int type");
        }

        if (!(expression.typeCheck(typeTable) instanceof IntType)) {
            throw new MyException("exp not int type");
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("CyclicBarrier(%s, %s)", var, expression.toString());
    }
}
