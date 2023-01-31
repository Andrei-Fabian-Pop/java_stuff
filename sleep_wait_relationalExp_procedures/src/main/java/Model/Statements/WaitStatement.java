package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.Expressions.*;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;

public class WaitStatement implements IStatement {
    private final Expression expression;

    public WaitStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IntValue value = (IntValue) expression.eval(state.getSymTable().peek(), state.getHeap());
        if (value.getValue() != 0) {
            state.getExecStack().push(new WaitStatement(
                    new ArithExpression(
                            BinaryExpression.OPERATOR.SUBSTR,
                            expression,
                            new ValueExpression(new IntValue(1))
                    )
            ));
            state.getExecStack().push(new PrintStatement(expression));
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        if (!(expression.typeCheck(typeTable) instanceof IntType)) {
            throw new MyException("expression is not int type");
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Wait(%s)", expression.toString());
    }
}
