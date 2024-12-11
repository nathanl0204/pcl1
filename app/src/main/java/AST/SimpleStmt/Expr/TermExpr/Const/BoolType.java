package AST.SimpleStmt.Expr.TermExpr.Const;

import java.io.BufferedWriter;
import java.io.IOException;

public class BoolType extends Const {
    private boolean value;

    public BoolType() {
        this.value = false;
    }

    public BoolType(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\""+value+"\"];\n");
    }

    public BoolType simplify(){
        return this;
    }
}  