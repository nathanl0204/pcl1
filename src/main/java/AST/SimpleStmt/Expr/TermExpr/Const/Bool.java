package AST.SimpleStmt.Expr.TermExpr.Const;

import java.io.BufferedWriter;
import java.io.IOException;

public class Bool extends Const {
    private boolean value;

    public Bool() {
        this.value = false;
    }

    public Bool(boolean value) {
        this.value = value;
    }

    public boolean getBool() {
        return value;
    }

    public void setBool(boolean value) {
        this.value = value;
    }

    public void vizualisation(BufferedWriter writer, String nodeName) throws IOException {
        writer.write("  " + nodeName + " [label=\""+value+"\"];\n");
    }

    public Bool simplify(){
        return this;
    }
}  