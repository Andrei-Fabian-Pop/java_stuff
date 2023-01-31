package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.Values;

public class Switch implements IStatement {
    private final Expression exp;
    private final Expression exp1;
    private final IStatement stmt1;
    private final Expression exp2;
    private final IStatement stmt2;
    private final IStatement stmt3;

    public Switch(Expression exp, Expression exp1, IStatement stmt1, Expression exp2, IStatement stmt2, IStatement stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Values value = exp.eval(state.getSymTable(), state.getHeap());
        Values value1 = exp1.eval(state.getSymTable(), state.getHeap());
        Values value2 = exp2.eval(state.getSymTable(), state.getHeap());

        if (value.equals(value1)) {
            state.getExecStack().push(stmt1);
        } else if (value.equals(value2)) {
            state.getExecStack().push(stmt2);
        } else {
            state.getExecStack().push(stmt3);
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        if (!exp.typeCheck(typeTable).equals(exp1.typeCheck(typeTable)) && !exp.typeCheck(typeTable).equals(exp2.typeCheck(typeTable))) {
            throw new MyException("switch not good");
        }
        stmt1.typeCheck(typeTable.copy());
        stmt2.typeCheck(typeTable.copy());
        stmt3.typeCheck(typeTable.copy());
        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("switch(%s) (case %s: %s) (case %s: %s) (default: %s)",
                exp.toString(), exp1.toString(), stmt1.toString(), exp2.toString(), stmt2.toString(), stmt3.toString());
    }
}
