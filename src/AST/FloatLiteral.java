package AST;

import Types.FloatType;
import Types.Type;

public class FloatLiteral extends Literal {
    private float value;

    public FloatLiteral(float value, int line, int offset) {
        this.value = value;
        this.line = line;
        this.offset = offset;
    }

    public float getValue() {
        return value;
    }

    public Type getType() {
        return FloatType.getInstance();
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
