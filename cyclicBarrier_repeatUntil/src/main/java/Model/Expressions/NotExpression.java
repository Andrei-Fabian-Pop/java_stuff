package Model.Expressions;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ADT.IHeap;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Values;

public class NotExpression implements Expression {
    private final Expression expression;

    public NotExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Values eval(IDictionary<String, Values> symTable, IHeap heap) throws MyException {
        BoolValue value = (BoolValue) expression.eval(symTable, heap);

        return new BoolValue(!value.getValue());
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        if (!(expression.typeCheck(typeTable) instanceof BoolType)) {
            throw new MyException("NOT: expression is not bool type");
        }
        return new BoolType();
    }

    @Override
    public String toString() {
        return String.format("NOT (%s)", expression.toString());
    }
}
