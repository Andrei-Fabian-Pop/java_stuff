package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ProgramState;
import Model.Types.Type;

public class ReturnStatement implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getSymTable().pop();

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        return typeTable;
    }

    @Override
    public String toString() {
        return "return";
    }
}
