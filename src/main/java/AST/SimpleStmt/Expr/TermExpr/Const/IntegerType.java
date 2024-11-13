package AST.SimpleStmt.Expr.TermExpr.Const;

import java.io.BufferedWriter;
import java.io.IOException;

public class IntegerType extends Const {
    private int value;

    public IntegerType() {
        this.value = 0;
    }

    public IntegerType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\""+value+"\"];\n");
    }

    public IntegerType simplify(){
        return this;
    }
}
