package Types;

public class IntegerType extends Type {
    public static Type instance;

    public IntegerType() {}

    public static Type getInstance() {
        if (instance == null) {
            instance = new IntegerType();
        }
        return instance;
    }

    public static boolean check(Type t) {
        return (t instanceof IntegerType);
    }

    public String toString() {
        return "int";
    }
}
