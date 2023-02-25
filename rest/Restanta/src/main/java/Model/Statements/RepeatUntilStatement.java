package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.Expressions.*;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;

public class RepeatUntilStatement implements IStatement {
    private final IStatement statement;
    private final Expression expression;

    public RepeatUntilStatement(IStatement stmt, Expression exp) {
        this.statement = stmt;
        this.expression = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        state.getExecStack().push(
                new WhileStatement(
                    new NotExpression(expression),
                    statement
        ));

        state.getExecStack().push(statement);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        if (!(expression.typeCheck(typeTable) instanceof BoolType)) {
            throw new MyException("Expression %s is not bool type".formatted(expression.toString()));
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("repeat\n\t%s\nuntil (%s);", statement.toString(), expression.toString());
    }
}
