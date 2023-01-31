package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Values;

import java.util.List;

public class Call implements IStatement {
    private final String procName;
    private final List<Expression> parameters;

    public Call(String procName, List<Expression> arguments) {
        this.procName = procName;
        this.parameters = arguments;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (!state.getProcedureTable().getProcedureTable().containsKey(procName)) {
            throw new MyException("Procedure not defined.");
        }

        if (state.getProcedureTable().getProcedureTable().get(procName).first.size() != parameters.size()) {
            throw new MyException(String.format("Too many or not enough parameters. Expected %d", state.getProcedureTable().getProcedureTable().get(procName).first.size()));
        }

        state.getSymTable().push(state.getSymTable().peek().copy());

        List<String> stringList = state.getProcedureTable().getProcedureTable().get(procName).first;
        for (int i = 0; i < stringList.size(); ++i) {
            Values value = parameters.get(i).eval(state.getSymTable().peek(), state.getHeap());
            state.getSymTable().peek().put(stringList.get(i), value);
        }

        state.getExecStack().push(new ReturnStatement());
        state.getExecStack().push(state.getProcedureTable().getProcedureTable().get(procName).second);

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        return typeTable;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(String.format("call %s (", procName));
        for (int i = 0; i < this.parameters.size(); ++i) {
            res.append(this.parameters.get(i));
            if (i + 1 < this.parameters.size()) {
                res.append(", ");
            }
        }
        res.append(")");

        return res.toString();
    }
}
