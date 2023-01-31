package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Expressions.BinaryExpression;
import Model.Expressions.Expression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.Value;

public class ForStatement implements IStatement {
    private final String v;
    private final Expression exp1;
    private final Expression exp2;
    private final Expression exp3;
    private final IStatement statement;

    public ForStatement(String v, Expression exp1, Expression exp2, Expression exp3, IStatement statement) {
        this.v = v;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<IStatement> exeStack = state.getExecStack();

        exeStack.push(new WhileStatement(new RelationalExpression(
                BinaryExpression.OPERATOR.LESS, new VariableExpression(v), exp2),
                new CompStatement(statement, new AssStatement(v, exp3))
        ));
        exeStack.push(new AssStatement(v, exp1));
        exeStack.push(new DeclarationStatement(v, new IntType()));

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        if (
                !(exp1.typeCheck(typeTable) instanceof IntType) ||
                !(exp2.typeCheck(typeTable) instanceof IntType) ||
                !(exp3.typeCheck(typeTable) instanceof IntType)
            ) {
            throw new MyException("No good expression in for");
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("for (int %s = %s; %s < %s; %s = %s) {\n\t%s\n}",
                v, exp1.toString(),
                v, exp2.toString(),
                v, exp3.toString(),
                statement.toString());
    }
}
