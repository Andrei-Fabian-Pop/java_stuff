package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ADT.Pair;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Values;

public class NewLatch implements IStatement {
    private final String var;
    private final Expression expression;

    public NewLatch(String var, Expression expression) {
        this.var = var;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IntValue value = (IntValue) expression.eval(state.getSymTable(), state.getHeap());
        if (!(value.getType() instanceof IntType)) {
            throw new MyException("Expression is not int type");
        }

        Integer location =  state.getLatchTable().getLocation();
        state.getSymTable().put(var, new IntValue(location));
        state.getLatchTable().getLatchTable().put(location, value.getValue());

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        if (!(typeTable.get(var) instanceof IntType) || !(expression.typeCheck(typeTable) instanceof IntType)) {
            throw new MyException("Latch: Not int type");
        }
        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("NewLatch(%s, %s)", var, expression.toString());
    }
}
