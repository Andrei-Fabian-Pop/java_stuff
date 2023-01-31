package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.Expressions.*;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;

public class RepeatUntil implements IStatement {
    private final IStatement stmt;
    private final Expression exp;

    public RepeatUntil(IStatement stmt, Expression exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        state.getExecStack().push(new WhileStatement(
                new NotExpression(exp),
                stmt
        ));

        state.getExecStack().push(stmt);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        if (!(exp.typeCheck(typeTable) instanceof BoolType)) {
            throw new MyException("Exp is not bool type");
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("repeat\n\t%s\nuntil (%s);", stmt.toString(), exp.toString());
    }
}
