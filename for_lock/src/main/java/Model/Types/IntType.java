package Model.Types;

import Model.Values.IntValue;
import Model.Values.Value;

public class IntType implements Type {
    @Override
    public boolean equals(Type other) {
        return other instanceof IntType;
    }

    @Override
    public Value getDefault() {
        return new IntValue(0);
    }

    @Override
    public String toString() {
        return "Int";
    }
}
