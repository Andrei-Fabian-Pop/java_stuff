package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.Values;

public class CondAss implements IStatement {
    private final String v;
    private final Expression exp1;
    private final Expression exp2;
    private final Expression exp3;

    public CondAss(String v, Expression exp1, Expression exp2, Expression exp3) {
        this.v = v;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getExecStack().push(new IfStatement(
                exp1,
                new AssStatement(v, exp2),
                new AssStatement(v, exp3)
        ));

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        if (!(exp1.typeCheck(typeTable) instanceof BoolType)) {
            throw new MyException("First expression is not bool type");
        }

        if (!(typeTable.get(v).equals(exp2.typeCheck(typeTable))) || !(exp2.typeCheck(typeTable).equals(exp3.typeCheck(typeTable)))) {
            throw new MyException("Different type error");
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("%s = %s ? %s : %s", v, exp1.toString(), exp2.toString(), exp3.toString());
    }
}
