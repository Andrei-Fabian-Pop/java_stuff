package Model.Types;

import Model.Values.BoolValue;
import Model.Values.Value;

public class BoolType implements Type {

    @Override
    public boolean equals(Type other) {
        return other instanceof BoolType;
    }

    @Override
    public Value getDefault() {
        return new BoolValue(false);
    }

    @Override
    public String toString() {
        return "Bool";
    }
}
