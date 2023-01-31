package Model.ADT;

import Model.Statements.IStatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyProcedureTable implements IProcedureTable {
    private Map<String, Pair<List<String>, IStatement>> procedureTable;

    public MyProcedureTable(Map<String, Pair<List<String>, IStatement>> procedureTable) {
        this.procedureTable = procedureTable;
    }

    public MyProcedureTable() {
        this.procedureTable = new HashMap<>();
    }

    @Override
    public Map<String, Pair<List<String>, IStatement>> getProcedureTable() {
        return this.procedureTable;
    }

    @Override
    public void setProcedureTable(Map<String, Pair<List<String>, IStatement>> procedureTable) {
        this.procedureTable = procedureTable;
    }
}
