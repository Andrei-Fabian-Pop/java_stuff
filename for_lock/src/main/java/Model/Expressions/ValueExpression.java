package Model.Expressions;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExpression implements Expression {
    private final Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeap heap) throws MyException {
        return value;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        return value.getType();
    }

    @Override
    public String toString() {
        return String.format("ValueExpression{%s}", value.toString());
    }
}
