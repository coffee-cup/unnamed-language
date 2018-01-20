package Types;

import Types.Type;

public class ArrayType extends Type {
    Type arrayOf;
    int size;

    public ArrayType(Type arrayOf, int size) {
        this.arrayOf = arrayOf;
        this.size = size;
    }

    public String toString() {
        return arrayOf.toString() + "[" + size + "]";
    }
}