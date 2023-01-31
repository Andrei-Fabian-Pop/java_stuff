package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ProgramState;
import Model.Types.Type;

public class SleepStatement implements IStatement{
    // sleep is like wait but with no print
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        return null;
    }
}
