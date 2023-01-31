package Model.Statements;

import Exceptions.MyException;
import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.ADT.MyStack;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Values;

import java.util.Stack;

public class ForkStatement implements IStatement {
    private final IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<IStatement> newExeStack = new MyStack<>();
        newExeStack.push(statement);

        Stack<IDictionary<String, Values>> aux = new Stack<>();
        Stack<IDictionary<String, Values>> finalStack = new Stack<>();

        while (!state.getSymTable().isEmpty()) {
            aux.push(state.getSymTable().pop().copy());
        }

        while (!aux.isEmpty()) {
            state.getSymTable().push(aux.peek().copy());
            finalStack.push(aux.pop().copy());
        }

        return new ProgramState(newExeStack, finalStack, state.getOut(), state.getFileTable(), state.getHeap(), state.getProcedureTable());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws MyException {
        statement.typeCheck(typeTable.copy());
        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("Fork{\n%s\n}", statement);
    }
}
