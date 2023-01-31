package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ADT.Pair;
import Model.ProgramState;
import Model.Types.Type;

import java.util.List;

public class Procedure implements IStatement {
    private final String procName;
    private final List<String> parameters;
    private final IStatement procedureDefinition;

    public Procedure(String procName, List<String> parameters, IStatement procedureDefinition) {
        this.procName = procName;
        this.parameters = parameters;
        this.procedureDefinition = procedureDefinition;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (state.getProcedureTable().getProcedureTable().containsKey(procName)) {
            throw new MyException("Procedure already defined, no overload available ://///");
        }

        state.getProcedureTable().getProcedureTable().put(procName, new Pair<>(parameters, procedureDefinition));

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        return typeTable;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(String.format("procedure %s (", procName));
        for (int i = 0; i < this.parameters.size(); ++i) {
            res.append(this.parameters.get(i));
            if (i+1 < this.parameters.size()) {
                res.append(", ");
            }
        }
        res.append(String.format(") {\n%s\n}", procedureDefinition.toString()));

        return res.toString();
    }
}
