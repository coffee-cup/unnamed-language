package IR.Instructions;

import java.util.ArrayList;

public class IRFunctionCall extends IRInstruction {
    private String name;
    private Temp temp;
    private ArrayList<Temp> args;

    public IRFunctionCall(String name, ArrayList<Temp> args) {
        this(name, null, args);
    }

    public IRFunctionCall(String name, Temp temp, ArrayList<Temp> args) {
        this.name = name;
        this.temp = temp;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public Temp getTemp() {
        return temp;
    }

    public ArrayList<Temp> getArgs() {
        return args;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
