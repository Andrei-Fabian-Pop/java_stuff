package Model.ADT;

import Model.Statements.IStatement;

import java.util.List;
import java.util.Map;

public interface IProcedureTable {
    Map<String, Pair<List<String>, IStatement>> getProcedureTable();
    void setProcedureTable(Map<String, Pair<List<String>, IStatement>> procedureTable);
}
