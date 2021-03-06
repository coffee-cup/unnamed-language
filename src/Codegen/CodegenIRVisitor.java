package Codegen;

import java.io.PrintStream;

import IR.IRFunction;
import IR.IRProgram;
import IR.Instructions.IRInstruction;
import Types.Type;

public class CodegenIRVisitor implements IR.Visitor<Void> {
    private PrintStream out;
    private String sourceName;
    private String className;
    private int indentLevel = 0;

    public CodegenIRVisitor(PrintStream out, String sourceName) {
        this.out = out;
        this.sourceName = sourceName;
    }

    public Void visit(IRFunction f) {
        String argString = "";
        for (Type t: f.getParamTypes()) {
            argString += t.toJVMString();
        }
        String returnString = "V";
        if (f.getReturnType() != null) {
            returnString = f.getReturnType().toJVMString();
        }
        String funcSignature = f.getName() + "(" + argString + ")" + returnString;

        out.print(".method public static"); space();
        if (f.getName().equals("main")) out.print("__");
        out.print(funcSignature); newLine();
        forwardIndent();

        StringBuilder builder = new StringBuilder();
        CodegenIRInstructionVisitor codegenIRInstructionVisitor =
            new CodegenIRInstructionVisitor(builder, className);

        for (IRInstruction i: f.getTempFactory().getAllTemps()) {
            i.accept(codegenIRInstructionVisitor);
        }

        for (IRInstruction i: f.getInstructions()) {
            builder.append("\n");
            i.accept(codegenIRInstructionVisitor);
        }

        int stackSize = codegenIRInstructionVisitor.getMaxStackSize();
        stackSize += f.getTempFactory().getParamCount();

        printIndent(); printLimitLocals(f.getTempFactory().getTempCount());
        printIndent(); printLimitStack(stackSize); newLine();
        out.print(builder.toString());
        backIndent();
        out.print(".end method"); newLine();

        return null;
    }

    public Void visit(IRProgram p) {
        out.print(".source"); space();
        out.print(sourceName); newLine();

        out.print(".class public"); space();
        out.print(p.getName()); newLine();
        this.className = p.getName();

        out.print(".super java/lang/Object");
        newLine(); newLine();

        for (IRFunction f: p.getFunctions()) {
            f.accept(this);
            newLine();
        }

        out.println(";----------------------------------------------;");
        out.println(";                                              ;");
        out.println("; Boilerplate                                  ;");
        out.println(";                                              ;");
        out.println(";----------------------------------------------;");
        newLine();

        out.println(".method public static main([Ljava/lang.String;)V"); forwardIndent();
        printIndent(); printLimitLocals(1);
        printIndent(); printLimitStack(4);
        printIndent(); out.println("invokestatic " + p.getName() + "/__main()V");
        printIndent(); out.println("return"); backIndent();
        out.println(".end method"); newLine();

        out.println(".method public <init>()V"); forwardIndent();
        printIndent(); out.println("aload_0");
        printIndent(); out.println("invokenonvirtual java/lang/Object/<init>()V");
        printIndent(); out.println("return"); backIndent();
        out.println(".end method"); newLine();

        return null;
    }

    private void printLimitLocals(int limits) {
        limit(); out.print("locals"); space();
        out.print(limits);
        newLine();
    }

    private void printLimitStack(int size) {
        limit(); out.print("stack"); space();
        out.print(size);
        newLine();
    }

    private void limit() {
        out.print(".limit"); space();
    }

    private void forwardIndent() {
        indentLevel += 1;
    }

    private void backIndent() {
        indentLevel -= 1;
        if (indentLevel < 0)
            indentLevel = 0;
    }

    private void printIndent() {
        for (int i = 0; i < indentLevel; i += 1) {
            out.print("    ");
        }
    }

    private void openBrace() {
        out.print("{");
    }

    private void closeBrace() {
        out.print("}");
    }

    private void newLine() {
        out.println("");
    }

    private void space() {
        out.print(" ");
    }

    private void commaSep() {
        out.print(", ");
    }

    private void semi() {
        out.print(";");
    }
}

