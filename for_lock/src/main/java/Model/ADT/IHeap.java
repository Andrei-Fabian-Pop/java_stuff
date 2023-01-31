package Model.ADT;

import Exceptions.MyException;
import Model.Values.Value;

import java.util.Map;

public interface IHeap {
    String getFreeValue();
    Map<String, Value> getContent();
    void setContent(Map<String, Value> newMap);
    String add(Value value);
    void update(String position, Value value) throws MyException;
    Value get(String position) throws MyException;
}
